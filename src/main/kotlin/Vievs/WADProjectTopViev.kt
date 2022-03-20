package Vievs

import Controllers.WADProjectsController
import Models.WADSettings
import javafx.scene.Parent
import tornadofx.*
import java.nio.charset.Charset
import java.util.*

class WADProjectTopViev() : View() {
    val  projectsController : WADProjectsController by inject()
    override val root: Parent = menubar {
        menu("File") {
            item("New project").action { projectsController.createProjects() }
            item("Open project")
            menu ("Stop"){  }
            menu ("Stop and close"){  }
            item("New project")
        }

        menu("Viev"){
        }

        menu ("Help"){
            item("About")
        }

    }
}