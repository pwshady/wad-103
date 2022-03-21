package Controllers

import Models.WADSettings
import Models.WADStatus
import Vievs.WADApp
import Vievs.WADCreateProjectViev
import Vievs.WADOpenProjectsViev
import jdbc.WADProjectsDao
import tornadofx.Controller
import tornadofx.launch
import tornadofx.observable
import java.util.*

fun main(){
    val properties = Properties()
    properties.load(Any::class.java.getResourceAsStream("/WAD.properties"))
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
        when (WADStatus.stat.openProjectStatusCode){


            0 -> {
                find<WADOpenProjectsViev>().openWindow(owner = null)
                WADStatus.stat.openProjectStatusCode = 1
                println(WADStatus.stat.openProjectListName)
            }
            1 -> println("oop")
        }
    }

    fun openProject(text : String) : Int{
        val dao = WADProjectsDao()
        val result = dao.getWADProject(text, "all_project")
        if (result.second == 0){
            return dao.addProject(result.first, "open_project")
            WADStatus.stat.openProjectList.add(result.first)
        } else {
            return result.second
        }
    }
}


