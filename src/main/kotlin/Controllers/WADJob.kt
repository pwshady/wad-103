package Controllers

import Models.WADStatus
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import jdbc.WADProject
import jdbc.WADProjectsDao
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import tornadofx.onChange
import tornadofx.seconds
import java.util.*


class WADJob(wadProject: WADProject) {
    val name = wadProject.name
    var flag = false
    val wadProject = wadProject
    fun main() {
        WADStatus.stat.wadProjectRunList.onChange {
            WADStatus.stat.wadProjectRunList.forEach{
                if (it.projectName == name && it.statusMessage){
                    if(it.codeMessage == 1 && !WADStatus.stat.wadProjectList.last{it.projectName == name}.run){
                        job(wadProject)
                        flag = true
                    }
                    if (it.codeMessage == 0){
                        flag = false
                    }
                    //println(WADStatus.stat.wadProjectRunList)
                    it.statusMessage = false
                }
            }


        }

    }

    fun job(wadProject: WADProject){
        CoroutineScope(Dispatchers.IO).launch {
            WADStatus.stat.wadProjectList.last{it.projectName == name}.run = true
            val retrofit : Retrofit = Retrofit.Builder().baseUrl("https://web.archive.org")
                .addConverterFactory(ScalarsConverterFactory.create()).addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            var allFiles = 0
            var allLength : Long = 0
            val service = retrofit.create(WADGetFileListService::class.java)
            var dao = WADProjectsDao()
            println(Date())
            var wadProjectGet = Pair(WADProject(0,"","","","",""),1)
            while (flag) {
                do {
                    wadProjectGet = dao.getWADProject(name, "open_projects")
                    if (wadProjectGet.second == 1){
                        println("error db")
                        flag = false
                        delay(1000L)
                        dao = WADProjectsDao()
                        break
                    }
                } while (wadProjectGet.second != 0)
                var wadProject = wadProjectGet.first
                val result = service.getFileList("${wadProject.domenName}*",10000, wadProject.resumeKey, wadProject.from, wadProject.to, wadProject.fileType).await()
                var fileList = result.split("\n")
                wadProject.resumeKey = fileList[fileList.size-2]
                if (fileList[fileList.size - 3] == ""){
                    fileList = fileList.subList(0,fileList.size - 3)
                    allFiles += fileList.size
                    println("prod")
                    println(allFiles)
                }else{
                    fileList = fileList.subList(0,fileList.size - 1)
                    wadProject.resumeKey = ""
                    flag = false
                    allFiles += fileList.size
                    println("end")
                    println("all files: " + (allFiles + 2).toString() )
                }
                val resultSaveFile = dao.addFilesList(wadProject.name, fileList)
                allLength += resultSaveFile.second
                println(resultSaveFile.first)
                if(resultSaveFile.first == 1){
                    flag = false
                }
                dao.updateWADProjectResumeKey(wadProject,"all_projects")
                dao.updateWADProjectResumeKey(wadProject,"open_projects")
                println("all length: ${allLength}")
                println(wadProject.resumeKey)
            }

            println(Date())
            WADStatus.stat.wadProjectList.last{it.projectName == name}.run = false
        }
    }
}


