package jdbc

import java.sql.DriverManager
import java.sql.PreparedStatement
import java.util.*

class WADProjectsDao {
    private val driver : String
    private val url : String
    private val user : String
    private val pass : String
    init{
        val properties = Properties()
        properties.load(Any::class.java.getResourceAsStream("/database.properties"))
        driver = properties["driver"].toString()
        url = properties["url"].toString()
        user = properties["user"].toString()
        pass = properties["pass"].toString()
    }

    fun getWADProjectsName(table_name : String) : Pair<List<String>, Int>
    {
        var resultList = mutableListOf<String>()
        var errorCode = 0
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url,user, pass)
            val stmt : PreparedStatement = connection.prepareStatement("select name from ${table_name}")
            val rs = stmt.executeQuery()
            var wad : String? = null
            while (rs.next())
            {
                wad = rs.getString(1)
                resultList.add(wad)
            }
            connection.close()
        } catch (e : Exception) {
            errorCode = 1
        }
        return Pair(resultList, errorCode)
    }

    fun addProject(wadProject: WADProject, tableName : String) : Int
    {
        var errorCode = 0
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt: PreparedStatement =
                connection.prepareStatement("insert into ${tableName} (id, name, domen_name, _from, _to, path, status, resume_key, timestamp, file_type, autopars, file_limit) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, wadProject.id!!)
            stmt.setString(2, wadProject.name)
            stmt.setString(3, wadProject.domenName)
            stmt.setString(4, wadProject.to)
            stmt.setString(5, wadProject.from)
            stmt.setString(6, wadProject.path)
            stmt.setInt(7, wadProject.status)
            stmt.setString(8, wadProject.resumeKey)
            stmt.setInt(9, wadProject.timestamp)
            stmt.setString(10, wadProject.fileType)
            stmt.setInt(11, wadProject.autopars)
            stmt.setInt(12, wadProject.fileLimit)
            stmt.executeUpdate()
            connection.close()
        } catch (e : Exception) {
            errorCode = 1
        }
        return errorCode
    }

    fun updateWADProjectResumeKey(wadProject: WADProject, tableName : String) : Int
    {
        var errorCode = 0
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt: PreparedStatement = connection.prepareStatement("update ${tableName} set resume_key = ? where name = ?")
            stmt.setString(1, wadProject.resumeKey)
            stmt.setString(2, wadProject.name)
            stmt.executeUpdate()
            connection.close()
        } catch (e : Exception) {
            errorCode = 1
        }
        return errorCode
    }

    fun getWADProjects(tableName: String) : Pair<List<WADProject>,Int>
    {
        var resultList = mutableListOf<WADProject>()
        var errorCode = 0
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt: PreparedStatement = connection.prepareStatement("select * from ${tableName}")
            val rs = stmt.executeQuery()
            var wad: WADProject? = null
            while (rs.next()) {
                wad = WADProject(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getString(8),
                    rs.getInt(9),
                    rs.getString(10),
                    rs.getInt(11),
                    rs.getInt(12)
                )
                resultList.add(wad)
            }
            connection.close()
        } catch (e : Exception){
            errorCode = 1
        }
        return Pair(resultList, errorCode)
    }

    fun getWADProject(name : String, tableName : String) : Pair<WADProject, Int>
    {
        var wad = WADProject(0, "", "", "", "", "")
        var errorCode = 0
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt: PreparedStatement = connection.prepareStatement("select * from ${tableName} where name=? limit 1")
            stmt.setString(1, name)
            val rs = stmt.executeQuery()
            while (rs.next()){
                wad = WADProject(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7),
                    rs.getString(8),
                    rs.getInt(9),
                    rs.getString(10),
                    rs.getInt(11),
                    rs.getInt(12)
                )
            }
            connection.close()
        } catch (e : Exception){
            errorCode = 1
        } finally {

        }
        return Pair(wad, errorCode)
    }

    fun deleteWADProject(name: String, tableName: String) : Int
    {
        var errorCode = 0
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt: PreparedStatement = connection.prepareStatement("delete from ${tableName} where name=?")
            stmt.setString(1, name)
            stmt.executeUpdate()
            connection.close()
        } catch (e : Exception){
            errorCode = 1
        }
        return errorCode
    }

    fun createTable(projectName : String, tableName : String) : Int
    {
        var errorCode = 0
        val name = "${projectName}_${tableName}"
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt : PreparedStatement = connection.prepareStatement(
                "CREATE TABLE wad.${name} (\n" +
                    "\tid INT auto_increment NOT NULL,\n" +
                    "\turl varchar(256) NOT NULL,\n" +
                    "\t`timestamp` varchar(14) NOT NULL,\n" +
                    "\tmimetype varchar(100) NOT NULL,\n" +
                    "\tcode INT NOT NULL,\n" +
                    "\t`length` INT NOT NULL,\n" +
                    "\tCONSTRAINT test1_files_pk PRIMARY KEY (id)\n" +
                    ");"
            )
            stmt.executeUpdate()
            connection.close()
        } catch (e : Exception){
            errorCode = 1
        }
        return errorCode
    }

    fun deleteTable(projectName : String, tableName : String) : Int
    {
        var errorCode = 0
        val name = "${projectName}_${tableName}"
        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, user, pass)
            val stmt : PreparedStatement = connection.prepareStatement("DROP TABLE wad.${name};")
            stmt.executeUpdate()
            connection.close()
        } catch (e : Exception){
            errorCode = 1
        }
        return errorCode
    }

    fun addFilesList(projectName: String, fileList : List<String>) : Pair<Int,Long>
    {
        var errorCode = 0
        var allLength : Long = 0
        val tableName = "${projectName}_files"
        try {
            //Class.forName(driver)
            //val connection = DriverManager.getConnection(url, user, pass)
            //var stmt: PreparedStatement = connection.prepareStatement("insert into ${tableName} (url, timestamp, mimetype, code, length) values (?, ?, ?, ?, ?);")
            for (element in fileList){
            //    val fileData = element.split(" ")
            //    allLength += fileData[4].toLong()
            //    stmt.setString(1, fileData[1])
            //    stmt.setString(2, fileData[0])
            //    stmt.setString(3, fileData[2])
            //    stmt.setInt(4, fileData[3].toIntOrNull() ?: 0)
            //    stmt.setInt(5, fileData[4].toInt())
            //    stmt.addBatch()
            }
            //stmt.executeBatch()
            //connection.close()
        } catch (e : Exception) {
            errorCode = 1
        }
        return Pair(errorCode, allLength)
    }
}