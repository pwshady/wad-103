package Models

data class ProjectStatus (
    var projectName : String,
    var run : Boolean = false,
    var statusCode : Int = 0,
    var statusText : String = ""
)