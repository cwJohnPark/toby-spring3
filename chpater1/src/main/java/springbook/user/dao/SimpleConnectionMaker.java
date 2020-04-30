package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 1-7 독립시킨 DB연결 기능인 SimpleConnectionMaker
public class SimpleConnectionMaker {
    // makeNewConnection()이 openConnection()으로 바뀌어야 한다면?
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "book");
        return c;
    }
}
