package dao.templatemethod;

import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 3-7 makeStatement()를 구현한 UserDao 서브클래스
 */
public class UserDaoDeleteAll extends UserDao_TemplateMethod {

    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("delete from users");
        return ps;
    }

    @Override
    protected PreparedStatement makeStatement(Connection c, User user) throws SQLException {
        return null;
    }
}
