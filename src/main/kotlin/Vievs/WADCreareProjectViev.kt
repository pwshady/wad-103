package Vievs

import Controllers.WADProjectsController
import Models.WADStatus
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.control.DatePicker
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import jdbc.WADProject
import jdbc.WADProjectsDao
import tornadofx.*
import validation.ValidationProject
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class WADCreateProjectViev() : Fragment() {
    val  wadProjectsController : WADProjectsController by inject()
    override val root: Parent = form {
        var dirFlag = true
        var name : TextField by singleAssign()
        var domenName : TextField by singleAssign()
        var from : TextField by singleAssign()
        var dateFrom : DatePicker by singleAssign()
        var to : TextField by singleAssign()
        var dateTo : DatePicker by singleAssign()
        var directory : TextField by singleAssign()
        var errorList : TextArea by singleAssign()
        var errorText = MutableList(6,{Pair("",0)})
        val dateFromValue = SimpleObjectProperty<LocalDate>()
        val dateToValue = SimpleObjectProperty<LocalDate>()
        val sdf = SimpleDateFormat("yyyyMMddHHmmss")
        val min = "19970101000000"
        var max = sdf.format(Date())
        try {
            val dao = WADProjectsDao()
        } catch (e:Exception){

        } finally {

        }
        fun statusUpdating (errorText : List<Pair<String,Int>>):Unit
        {
            errorList.textProperty().set(errorText.map { task -> task.first }.joinToString(separator =""))
            when (errorText.map { task -> task.second }.maxOrNull()){
                0 -> errorList.style{
                    backgroundColor += c("green")
                }
                1 -> errorList.style{
                    backgroundColor += c("yellow")
                }
                2 -> errorList.style{
                    backgroundColor += c("red")
                }
            }
        }
        fieldset("Create project") {
            field("Project name") {
                name = textfield()
                errorText.set(0, Pair(ValidationProject.nameValidation(name.text).first,ValidationProject.nameValidation(name.text).second))
                name.textProperty().onChange {
                    errorText.set(0, Pair(ValidationProject.nameValidation(name.text).first,ValidationProject.nameValidation(name.text).second))
                    if (dirFlag){
                        directory.text = "c:\\wad\\${name.text}"
                    }
                    val dao = WADProjectsDao()
                    if(name.text in dao.getWADProjectsName("all_projects").first){
                        errorText.set(0, Pair("A project with that name already exists. The data will be lost",1))
                    }
                    statusUpdating(errorText);
                }
            }
            field("Domen name"){
                domenName = textfield()
                errorText.set(1, Pair(ValidationProject.domenNameValidation(domenName.text).first,ValidationProject.domenNameValidation(domenName.text).second))
                domenName.textProperty().onChange {
                    errorText.set(1, Pair(ValidationProject.domenNameValidation(domenName.text).first,ValidationProject.domenNameValidation(domenName.text).second))
                    statusUpdating(errorText);
                }
            }
            field("Date from"){
                hbox {
                    from = textfield()
                    dateFrom = datepicker(dateFromValue){
                        //val pattern = "yyyyMMdd"
                        //val dateFormatter = DateTimeFormatter.ofPattern(pattern)
                        //converter = LocalDateStringConverter(dateFormatter,dateFormatter)
                        value = LocalDate.now()
                    }
                    button("min") {
                        action {
                            from.text = min
                        }
                    }
                    dateFrom.valueProperty().onChange {
                        from.text = "${dateFrom.value.toString().replace("-","")}000000"
                    }
                    errorText.set(2, Pair(ValidationProject.dateTimeValidation(from.text,min,"","from")
                        .first,ValidationProject.dateTimeValidation(from.text,min,"", "from").second))
                    from.textProperty().onChange {
                        errorText.set(2, Pair(ValidationProject.dateTimeValidation(from.text,min,"","from")
                            .first,ValidationProject.dateTimeValidation(from.text,min,"","from").second))
                        statusUpdating(errorText);
                    }
                }
            }
            field("Date to"){
                hbox{
                    to = textfield()
                    dateTo = datepicker(dateToValue){
                        value = LocalDate.now()
                    }
                    button("max") {
                        action {
                            max = sdf.format(Date())
                            to.text = max
                        }
                    }
                    dateTo.valueProperty().onChange {
                        to.text = "${dateTo.value.toString().replace("-","")}000000"
                        max = sdf.format(Date())
                    }
                    errorText.set(3, Pair(ValidationProject.dateTimeValidation(to.text,"",max,"to")
                        .first,ValidationProject.dateTimeValidation(to.text,"",max, "to").second))
                    to.textProperty().onChange {
                        max = sdf.format(Date())
                        errorText.set(3, Pair(ValidationProject.dateTimeValidation(to.text,"",max,"to")
                            .first,ValidationProject.dateTimeValidation(to.text,"",max, "to").second))
                        statusUpdating(errorText);
                    }
                }
            }
            field("Files directory"){
                hbox {
                    directory = textfield()
                    button("path"){
                        action {

                        }
                    }
                    errorText.set(4, Pair(ValidationProject.directotyValidation(directory.text)
                        .first,ValidationProject.directotyValidation(directory.text).second))
                    directory.focusedProperty().onChange {
                        dirFlag = false
                    }
                    directory.textProperty().onChange {
                        errorText.set(4, Pair(ValidationProject.directotyValidation(directory.text)
                            .first,ValidationProject.directotyValidation(directory.text).second))
                        val dir = File(directory.text)
                        if(dir.isDirectory){
                            errorText.set(4, Pair("A directory with that name already exists. The operation of the program may not be correct",1))
                        }
                        statusUpdating(errorText);
                    }
                }
            }
            errorList = textarea(){
            }
            errorList.disableProperty().set(true)
            statusUpdating(errorText);
        }

        println(name)
        hbox {
            button("Create") {
                setOnAction {
                    var wadProject = WADProject(0, name.text, domenName.text, to.text, from.text, directory.text)
                    val dao = WADProjectsDao()
                    dao.addProject(wadProject, "all_projects")

                    val dir = File(directory.text)
                    dir.mkdirs()
                    WADStatus.stat.createProjectStatusCode = 2
                    wadProjectsController.createProjectViev()
                    close()
                }
            }
            button("Cancel") {
                setOnAction {
                    WADStatus.stat.createProjectStatusCode = 5
                    wadProjectsController.createProjectViev()
                    close()
                }
            }
        }

    }
}

class CreateProjectController() : Controller(){
    var mod : Boolean
    init {
        mod = false
    }
}