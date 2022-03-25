package Controllers

import Models.WADStatus
import jdbc.WADProject
import kotlinx.coroutines.*
import tornadofx.onChange



class WADJob(wadProject: WADProject) {
    val name = wadProject.name
    var flag = false
    var i = 0
    fun main() {
        WADStatus.stat.wadProjectRunList.onChange {
            WADStatus.stat.wadProjectRunList.forEach{
                if (it.projectName == name && it.statusMessage){
                    if(it.codeMessage == 1 && !WADStatus.stat.wadProjectList.last{it.projectName == name}.run){
                        job()
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

    fun job(){
        CoroutineScope(Dispatchers.IO).launch {
            WADStatus.stat.wadProjectList.last{it.projectName == name}.run = true
            while (flag) {
                println("$name   $i    $flag")
                i++
                delay(2000L)
                println(WADStatus.stat.wadProjectList.last{it.projectName == name}.run)

            }
            WADStatus.stat.wadProjectList.last{it.projectName == name}.run = false
        }
    }
}


