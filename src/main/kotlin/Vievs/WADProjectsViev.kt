package Vievs

import Models.WADStatus
import javafx.scene.Parent
import javafx.scene.control.TabPane
import jdbc.WADProjectsDao
import tornadofx.*


class WADProjectsViev() : View() {
    init {
        val dao = WADProjectsDao()
        WADStatus.stat.openProjectList = dao.getWADProjects("open_projects").first.toMutableList().observable()
    }
    override val root: Parent = borderpane {
        var topViev = WADProjectTopViev()
        top(topViev::class)

        button("t1"){
            action {

            }
        }
        center(WADProjectCenterViev::class)
        var rightViev = WADProjectRightViev()
        right(rightViev::class)

    }
}