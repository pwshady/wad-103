package Controllers

import Models.ProjectStatus
import Models.RunStatus
import Models.WADSettings
import Models.WADStatus
import Vievs.WADApp
import Vievs.WADCreateProjectViev
import Vievs.WADOpenProjectsViev
import jdbc.WADProject
import jdbc.WADProjectsDao
import tornadofx.Controller
import tornadofx.launch
import tornadofx.observable
import java.text.BreakIterator
import java.util.*

fun main(){
    val properties = Properties()
    properties.load(Any::class.java.getResourceAsStream("/WAD.properties"))
    //WADStatus.stat.wadProjectList = mutableListOf<Pair<WADProject,WADStatus>>().observable()
    launch<WADApp>()
}

class WADProjectsController() : Controller(){
    fun createProjectViev()
    {
        when (WADStatus.stat.createProjectStatusCode){
            0 -> {
                find<WADCreateProjectViev>().openWindow(owner = null)
                WADStatus.stat.createProjectStatusCode = 1
            }
            1 -> println("ocp")
            2 -> println("create")
            3 -> println("error db")
            4 -> println("error dir")
            5 -> {
                println("cancel")
                WADStatus.stat.createProjectStatusCode = 0
            }
        }
    }

    fun openProjectViev()
    {
        val dao = WADProjectsDao()
        WADStatus.stat.openProjectListName = dao.getWADProjectsName("all_projects").first.toMutableList().observable()
        if (reCreateOpenProjectListName() == 0){
            when (WADStatus.stat.openProjectStatusCode){
                0 -> {
                    find<WADOpenProjectsViev>().openWindow(owner = null)
                    WADStatus.stat.openProjectStatusCode = 1
                }
                1 -> println("oop")
            }
        }
    }

    fun reCreateOpenProjectListName() : Int{
        var errorCode = 0
        try {
            val dao = WADProjectsDao()
            var allProjects = dao.getWADProjectsName("all_projects").first
            for (name in WADStatus.stat.openProjectListName){
                if(name !in allProjects){
                    WADStatus.stat.openProjectListName.removeAll{it == name}
                }
            }
            for (name in WADStatus.stat.openProjectList.map { it.name }){
                WADStatus.stat.openProjectListName.removeAll{it == name}
            }
        } catch (e : Exception){
            errorCode = 1
        }
        return errorCode
    }

    fun creteProject(wadProject : WADProject) : Int
    {
        val dao = WADProjectsDao()
        dao.addProject(wadProject, "all_projects")
        dao.createTable(wadProject.name, "files")
        return 0
    }

    fun openProject(text : String) : Int{
        val dao = WADProjectsDao()
        var result = dao.getWADProject(text, "all_projects")
        if (result.second == 0){
            dao.addProject(result.first, "open_projects")
            WADStatus.stat.openProjectList.add(result.first)
            reCreateOpenProjectListName()
            return 0
        } else {
            return result.second
        }
    }

    fun closeProject(name: String) : Int{
        val dao = WADProjectsDao()
        val result = dao.deleteWADProject(name, "open_projects")
        if (result == 0){
            WADStatus.stat.openProjectList.removeAll{ it.name == name}
            WADStatus.stat.wadProjectList.removeAll{ it.projectName == name}
            reCreateOpenProjectListName()
        }
        return result
    }

    fun deleteProject(name : String) : Int{
        val dao = WADProjectsDao()
        val result = dao.deleteWADProject(name, "all_projects")
        reCreateOpenProjectListName()
        return result
    }

}


