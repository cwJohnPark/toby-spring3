package springbook.dao.templatemethod;

import springbook.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 3-7 makeStatement()를 구현한 UserDao 서브클래스
 */
public class UserDaoAdd extends UserDao_TemplateMethod {


    @Override
    protected PreparedStatement makeStatement(Connection c, User user) throws SQLException {
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) value (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        return ps;
    }

    @Override
    protected PreparedStatement makeStatement(Connection c) throws SQLException {
        return null;
    }
}
