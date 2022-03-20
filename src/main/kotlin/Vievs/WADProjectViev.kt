package Vievs

import javafx.scene.Parent
import tornadofx.View
import tornadofx.borderpane

class WADProjectViev() : View() {
    override val root: Parent = borderpane {
        var topViev = WADProjectTopViev()
        top(topViev::class)
    }
}