package springbook.dao.strategy;

import springbook.dao.strategy.impl.AddStatement;
import springbook.dao.strategy.impl.DeleteAllStatement;
import springbook.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 전략 패턴을 사용한 UserDao
 * 3-11 메소드로 분리한 try/catch/finally 컨텍스트 코드
 * 3-12 클라이언트 책임을 담당할 deleteAll() 메소드
 * 3-15 user 정보를 AddStatement에 전달해주는 add() 메소드
 */
public class UserDao_Strategy {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 3-11 메소드로 분리한 try/catch/finally 컨텍스트 코드
     * - 리턴 값이 존재하지 않는 statement만 가능
     */
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (c != null) { try { c.close(); } catch (SQLException e) {} }
        }
    }

    /**
     * 3-12 클라이언트 책임을 담당할 deleteAll() 메소드
     */
    public void deleteAll() throws SQLException {
        // 선정한 전략 클래스의 오브젝트 생성
        StatementStrategy st = new DeleteAllStatement();
        // 컨텍스트 호출, 전략 오브젝트 전달
        jdbcContextWithStatementStrategy(st);
    }

    /**
     * 3-15 user 정보를 AddStatement에 전달해주는 add() 메소드
     */
    public void add(User user) throws SQLException {
       StatementStrategy st = new AddStatement(user);
       jdbcContextWithStatementStrategy(st);
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
