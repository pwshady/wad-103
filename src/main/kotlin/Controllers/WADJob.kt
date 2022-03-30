package Controllers

import Models.WADStatus
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import jdbc.WADProject
import jdbc.WADProjectsDao
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import tornadofx.onChange



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
            val service = retrofit.create(WADGetFileListService::class.java)
            val dao = WADProjectsDao()
            while (flag) {
                var wadProject = dao.getWADProject(name, "open_projects").first
                val result = service.getFileList("${wadProject.domenName}*",3, wadProject.resumeKey, wadProject.from, wadProject.to, wadProject.fileType).await()
                var fileList = result.split("\n")
                wadProject.resumeKey = fileList[fileList.size-2]
                dao.updateWADProjectResumeKey(wadProject,"all_projects")
                dao.updateWADProjectResumeKey(wadProject,"open_projects")
                allFiles += fileList.size - 3
                if (fileList[fileList.size - 3] == ""){
                    //println("prod")
                    //println(allFiles)
                    fileList = fileList.subList(0,fileList.size - 3)
                }else{
                    //println("end")
                    //println("all files: " + (allFiles + 2).toString() )
                    fileList = fileList.subList(0,fileList.size - 1)
                    wadProject.resumeKey = ""
                    flag = false
                }
                println(fileList[0])
                println(fileList[1])
                dao.updateWADProjectResumeKey(wadProject,"all_projects")
                dao.updateWADProjectResumeKey(wadProject,"open_projects")
            }
            WADStatus.stat.wadProjectList.last{it.projectName == name}.run = false
        }
    }
}


