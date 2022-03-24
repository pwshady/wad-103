package Models

import jdbc.WADProject
import tornadofx.observable

class WADStatus {
    object stat{
        var createProjectStatusCode : Int = 0
        var openProjectStatusCode : Int = 0
        var openProjectList = mutableListOf<WADProject>().observable()
        var openProjectListName = mutableListOf<String>().observable()
        var wadProjectList = mutableListOf<Pair<WADProject, Int>>()
    }
    object ex{
        var dbException : Int = 0
    }
}