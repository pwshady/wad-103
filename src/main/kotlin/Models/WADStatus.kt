package Models

import jdbc.WADProject
import tornadofx.observable

class WADStatus {
    object stat{
        var createProjectStatusCode : Int = 0
        var openProjectStatusCode : Int = 0
        var openProjectList = mutableListOf<WADProject>().observable()
        var openProjectListName = mutableListOf<String>().observable()
    }
    object ex{
        var dbException : Int = 0
    }
}