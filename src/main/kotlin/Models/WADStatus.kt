package Models

import jdbc.WADProject
import tornadofx.observable

class WADStatus {
    object stat{
        var createProjectStatusCode : Int = 0
        var openProjectStatusCode : Int = 0
        var openProjectList = mutableListOf<WADProject>().observable()
        var openProjectListName = mutableListOf<String>().observable()
        var wadProjectList = mutableListOf<ProjectStatus>().observable()
        var wadProjectRunList = mutableListOf<RunStatus>().observable()
    }
    object ex{
        var dbException : Int = 0
    }
}