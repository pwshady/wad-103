package Vievs

import Controllers.WADProjectsController
import Models.WADStatus
import javafx.scene.Parent
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import tornadofx.*

class WADProjectCenterViev() : View() {
    val projectsController: WADProjectsController by inject()
    override val root: Parent = hbox {
        var tp: TabPane by singleAssign()
        //var tt : TextField by singleAssign()
        tp = tabpane() {
            tab("d2") {

            }
        }
            //     WADStatus.stat.openProjectList.forEach(){
            //         tab("s1"){
            //         }
            //     }
            // tt = textfield {  }
            //var ty = tp.tabs.size
        tp.tabs.onChange {         var ty = tp.tabs
            println(ty) }
        var ty = tp.tabs
                println(ty)

        button("hhh").action{
            tp.tab("ui7") {  }
            //println(tp.tabs)
        }

            //println(WADStatus.stat.openProjectList[0])
            //var i = 0
            //var openProject = WADStatus.stat.openProjectList.observable()
            //    openProject.forEach{
            //        println(i)
            //        println(openProject[i].domenName)
            //        i++
            //    }

        }
    }


