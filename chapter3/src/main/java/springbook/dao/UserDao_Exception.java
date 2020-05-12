package springbook.dao;

import springbook.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 3-1 JDBC API를 이용한 DAO
 * 3-2 예외 발생 시에도 리소스를 반환하도록 수정한 deleteAll()
 * 3-3 JDBC 예외처리를 적용한 getCount() 메소드
 */
public class UserDao_Exception {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) value (?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;

        if(rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if(user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    /**
     * 3-2 예외 발생 시에도 리소스를 반환하도록 수정한 deleteAll()
     */
    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            // 예외가 발생할 가능성이 있는 코드를 모두 try 블록으로 묶어준다.
            c = dataSource.getConnection();
            ps = c.prepareStatement(" delete from users");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch( SQLException e) {
                    // ps.close() 메소드에서도 SQLExecption이 발생할 수 잇으므로 이를 잡아야 한다.
                    // 그렇지 않으면 Connection을 close() 하지 못하고 메소드를 빠져나갈 수 있다.
                }
            }
            if (c != null) {
                try {
                    c.close(); // Connection 반환
                } catch(SQLException e) {
                }
            }
        }

        ps.close();
        c.close();
    }

    /**
     * 3-3 JDBC 예외처리를 적용한 getCount() 메소드
     */
    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();

            ps = c.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch(SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
