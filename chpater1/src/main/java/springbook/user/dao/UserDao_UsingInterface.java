package springbook.user.dao;

import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 1-10 ConnectionMaker 인터페이스를 사용하도록 개선한 UserDao
 * 단점:  DConnection 클래스의 생성자를 호출하여 오브젝트를 생성하는 코드가 여전히 남아 있음
 */
public class UserDao_UsingInterface {
    private ConnectionMaker connectionMaker; // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요가 없다.

    public UserDao_UsingInterface() {
        connectionMaker = new DConnectionMaker(); // 앗! 그런데 여기에는 클래스 이름이 나오네!
    }
    public void add(User user) throws ClassNotFoundException, SQLException {
        // 인터페이스에 정의된 메소드를 사용하므로 클래스가 바뀐다고 해서 메소드 이름이 변경될 걱정은 없다.
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) value (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

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
}
