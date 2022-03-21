package Vievs

import Models.WADStatus
import javafx.scene.Parent
import jdbc.WADProjectsDao
import tornadofx.*

class WADOpenProjectsViev : Fragment() {
    override val root: Parent = hbox {
        listview(WADStatus.stat.openProjectListName) {  }
        button("add"){
            action { WADStatus.stat.openProjectListName.add("fuck")}
        }
    }
}