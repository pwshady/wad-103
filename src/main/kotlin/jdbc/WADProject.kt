package jdbc

data class WADProject (
    var id : Int,
    var name : String,
    var domenName : String,
    var from : String,
    var to : String,
    var path : String,
    var status : Int = 0,
    var resumeKey : String = "",
    var timestamp : Int = 0,
    var fileType : String = "",
    var autopars : Int = 0,
    var fileLimit : Int = 0,
)