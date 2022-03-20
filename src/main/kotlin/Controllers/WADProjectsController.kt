package Controllers

import Models.WADSettings
import Vievs.WADApp
import tornadofx.Controller
import tornadofx.launch
import java.util.*

fun main(){
    val properties = Properties()
    properties.load(Any::class.java.getResourceAsStream("/WAD.properties"))
    WADSettings.set.language = properties["language"].toString()
    launch<WADApp>()
}

class WADProjectsController() : Controller(){
    fun createProjects(){
        println("1")
    }
}


