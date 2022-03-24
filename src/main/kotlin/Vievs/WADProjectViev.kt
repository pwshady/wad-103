package Vievs

import Models.ProjectStatus
import Models.WADStatus
import javafx.application.Platform
import javafx.collections.ObservableList
import javafx.geometry.Side
import javafx.scene.Parent
import javafx.scene.control.TextField
import jdbc.WADProject
import tornadofx.*
import java.awt.Image
import kotlin.concurrent.thread

class WADProjectViev(wadProject: WADProject) : Fragment() {
    override val root: Parent = vbox {
        var textFieldDomenName : TextField by singleAssign()
        hbox {
            WADStatus.stat.wadProjectList.add(Pair(wadProject,ProjectStatus()))
            label("Domen name: "){
                this.style{
                    //this.backgroundColor += c("red")
                }
            }
            textFieldDomenName = textfield {
                this.disableProperty().set(true)
            }
            textFieldDomenName.text = wadProject.domenName
            checkbox("Only url") {  }
            button("Start"){

            }
            button("Stop"){

            }
            progressbar {
                thread {
                    var i = 0
                    while (true){
                        Platform.runLater{ progress = i.toDouble() /100}
                        Thread.sleep(100)
                        i ++
                        if (i == 100){
                            i = 0
                        }
                    }
                }
            }
            label("Status:")
            label("Stop")

        }
        drawer (side = Side.BOTTOM, multiselect = false) {
            item("files", expanded = true){

            }

            item("parsing"){

            }
            item("test") {

                    vbox {
                        class Branch(val id : Int, val code : String, val city : String, val state : String)
                        class Region(val id : Int, val name : String, val country : String, val branches : ObservableList<Branch>)

                        val  regions = listOf(Region(1,"Pac Nor", "USA", listOf(Branch(1,"d", "seatl","WA")).observable())).observable()
                        tableview(regions) {

                        }
                    }

            }
        }
    }
}


