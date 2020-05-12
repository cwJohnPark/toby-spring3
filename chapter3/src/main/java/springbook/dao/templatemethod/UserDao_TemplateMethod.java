package springbook.dao.templatemethod;

import springbook.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public abstract class UserDao_TemplateMethod {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            // 변하는 부분을 메소드로 추출하고 변하지 않는 부분에서 호출하도록 만듦
            ps = makeStatement(c);

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

    abstract protected PreparedStatement makeStatement(Connection c) throws SQLException;

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        // 템플릿 메소드 적용
        PreparedStatement ps = makeStatement(c, user);

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    protected abstract PreparedStatement makeStatement(Connection c, User user) throws SQLException;

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
