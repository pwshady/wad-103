package Vievs

import Controllers.WADProjectsController
import Models.WADStatus
import javafx.scene.Parent
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.control.TextField
import jdbc.WADProjectsDao
import tornadofx.*

class WADOpenProjectsViev : Fragment() {
    val  wadProjectsController : WADProjectsController by inject()
    override val root: Parent = vbox {
        var listViev : ListView<String> by singleAssign()
        listViev = listview(WADStatus.stat.openProjectListName){
            selectionModel.selectionMode = SelectionMode.SINGLE
        }
        listViev.onDoubleClick {
            if (listViev.selectedItem != null){
                if (wadProjectsController.openProject(listViev.selectedItem!!) == 0){
                    WADStatus.stat.openProjectListName
                }
            }
        }

        button("hh"){
            action {
                WADStatus.stat.openProjectListName.add("foo")
            }
        }
    }
    override fun onUndock() {
        WADStatus.stat.openProjectStatusCode = 0
    }
}