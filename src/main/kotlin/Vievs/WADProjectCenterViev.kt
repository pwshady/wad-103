package Vievs

import Controllers.WADProjectsController
import Models.WADStatus
import javafx.scene.Parent
import javafx.scene.control.TabPane
import jdbc.WADProject
import tornadofx.*

class WADProjectCenterViev() : View() {
    val  wadProjectsController : WADProjectsController by inject()
    override val root: Parent = hbox {
        var tp: TabPane by singleAssign()
        tp = tabpane() {}
        fun setTab(){
            for (i in 0 until WADStatus.stat.openProjectList.size){
                var flag = false
                for (j in 0 until tp.tabs.size){
                    if (WADStatus.stat.openProjectList[i].name == tp.tabs[j].text){
                        flag = true
                    }
                }
                if(!flag){
                    tp.tab(WADStatus.stat.openProjectList[i].name) {
                        this += WADProjectViev(WADStatus.stat.openProjectList[i])
                        this.setOnClosed {
                            wadProjectsController.closeProject(this.text)
                            println(WADStatus.stat.openProjectList)
                        }
                    }
                }
            }
        }
        setTab()
        WADStatus.stat.openProjectList.onChange {
            setTab()
            wadProjectsController.reCreateOpenProjectListName()
        }

    }
}


