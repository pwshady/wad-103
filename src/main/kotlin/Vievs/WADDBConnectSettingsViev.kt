package Vievs

import javafx.scene.Parent
import javafx.scene.control.TextField
import tornadofx.*

class WADDBConnectSettingsViev() : View() {
    override val root: Parent = form {
        var dbname: TextField by singleAssign()

        fieldset("Settings db") {
            field("Name db"){
                dbname = textfield()
            }
            field("Login"){
                textfield()
            }
            field("Password"){
                textfield()
            }
        }

        hbox {
            button("Ok") {
                setOnAction {
                    println(dbname.text)

                }
            }
            button("Cancel") {
                setOnAction {
                    close()
                }
            }
        }
    }
}