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
    fun getWADProject(id : Int) : WADProject = TODO()
    fun saveWADProject(wadProject: WADProject ) : Unit = TODO()
    fun updateWADProject(wadProject: WADProject ) : Unit = TODO()
    fun getWADProjectsName(table_name : String) : List<String>
    {
        var resultList = mutableListOf<String>()
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
        return resultList
    }
    fun addProject(wadProject: WADProject, tableName : String) : Unit
    {
        Class.forName(driver)
        val connection = DriverManager.getConnection(url,user, pass)
        val stmt : PreparedStatement = connection.prepareStatement("insert into ${tableName} (id, name, domen_name, _from, _to, path, status, resume_key, timestamp, file_type, autopars, file_limit) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
    }

    fun getWADProjects(tableName: String) : List<WADProject>
    {
        var resultList = mutableListOf<WADProject>()
        Class.forName(driver)
        val connection = DriverManager.getConnection(url,user, pass)
        val stmt : PreparedStatement = connection.prepareStatement("select * from ${tableName}")
        val rs = stmt.executeQuery()
        var wad : WADProject? = null
        while (rs.next())
        {
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
        return resultList
    }
}