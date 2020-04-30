package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

/**
 * 1-5 상속을 통한 확장 방법이 제공되는 UserDao
 * 단점: 상속을 통한 상하위 클래스의 관계는 결합도가 높다. 슈퍼클래스 변경 시 서브 클래스도 함께 변경해야 한다.
 */
public abstract class UserDao_Inheritance {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) value (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }


    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }

    // 1-5 구현 코드는 제거되고 추상 메소드를 바뀌었다. 메소드의 구현은 서브클래스가 담당한다.
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

    // 서브클래스에서 팩토리 메서드 패턴을 적용
    public class NUserDao extends UserDao_Inheritance {
        @Override
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            // N사 DB Connection 생성 코드
            return null;
        }
    }
    public class DUserDao extends UserDao_Inheritance {
        @Override
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            // D사 DB Connection 생성 코드
            return null;
        }
    }
}
