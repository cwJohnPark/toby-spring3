package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1-8 ConnectionMaker 인터페이스
 */
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
