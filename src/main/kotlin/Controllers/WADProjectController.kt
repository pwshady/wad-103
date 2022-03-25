package Controllers

import Models.RunStatus
import Models.WADStatus
import tornadofx.Controller

class WADProjectController : Controller(){
    fun sendMessageProject(projectName: String, statusMessage : Boolean, codeMessage : Int) : Int{
        WADStatus.stat.wadProjectRunList.add(RunStatus(projectName,statusMessage,codeMessage))
        return 0
    }

    fun deleteMessageProject(text: String) : Int{
        WADStatus.stat.wadProjectRunList.removeAll{ it.projectName == text}
        return 0
    }
}