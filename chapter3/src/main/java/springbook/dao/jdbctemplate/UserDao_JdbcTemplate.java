package springbook.dao.jdbctemplate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import springbook.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class UserDao_JdbcTemplate {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 3-56 재사용 가능하도록 독립시킨 RowMapper
     */
    private RowMapper<User> userMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setId(rs.getString("name"));
                user.setId(rs.getString("password"));
                return user;
            }
        };

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[] {id}, this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
                this.userMapper);
    }

    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    public int getCount_doubleCallback() {
        return this.jdbcTemplate.query(new PreparedStatementCreator() { // 첫 번째 콜백, Statement 생성
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement("select count(*) from users");
            }
        }, new ResultSetExtractor<Integer>() { // 두 번째 콜백, ResultSet으로부터 값을 추출
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getInt(1);
            }
        });
    }

    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password) values(?, ?, ?)",
                    user.getId(), user.getName(), user.getPassword());
    }

    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

}
