package Vievs

import javafx.scene.Parent
import javafx.scene.control.TextField
import jdbc.WADProjectsDao
import tornadofx.*
import java.awt.Button

class WADProjectRightViev() : View() {
    override val root: Parent = hbox{
        var from : TextField by singleAssign()
        button("t1"){
            action {
                val dao = WADProjectsDao()
                println( )
            }
        }
        from = textfield {  }

        }

    }
