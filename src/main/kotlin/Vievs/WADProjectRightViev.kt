package Vievs

import javafx.scene.Parent
import javafx.scene.control.TextField
import tornadofx.*
import java.awt.Button

class WADProjectRightViev() : View() {
    override val root: Parent = hbox{
        var from : TextField by singleAssign()
        button("t1"){
            action {
                from.text = "gg"
            }
        }
        from = textfield {  }

        }

    }
