package Vievs

import javafx.collections.ObservableList
import javafx.geometry.Side
import javafx.scene.Parent
import tornadofx.*
import java.awt.Image

class WADProjectViev() : Fragment() {
    override val root: Parent = vbox {
        hbox {
            button("jj")
            imageview("https://i.imgur.com/DuFZ6PQb.jpg")
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


