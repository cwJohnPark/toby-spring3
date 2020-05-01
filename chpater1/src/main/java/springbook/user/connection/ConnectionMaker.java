package springbook.user.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1-8 ConnectionMaker 인터페이스
 */
public interface ConnectionMaker {
    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
