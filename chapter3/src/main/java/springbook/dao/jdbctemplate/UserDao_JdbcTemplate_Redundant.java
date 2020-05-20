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
 * 3-45 JdbcTemplate의 초기화를 위한 코드
 */
public class UserDao_JdbcTemplate_Redundant {
    private JdbcTemplate jdbcTemplate;
    //private DataSource dataSource; // DataSource를 직접 사용할 일이 없다.

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        //this.dataSource = dataSource;
    }

    /**
     * 3-53 getAll() 메소드
     */
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
            new RowMapper<User>() {
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            });
    }

    /**
     * 3-51 queryForObject() 와 RowMapper를 적용한 get() 메소드
     */
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
            new Object[] {id},
            new RowMapper<User>() {
                // ResultSet으로 부터 하나의 로우 결과를 오브젝트(User)에 매핑해주는 RowMapper 콜백
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            });
    }

    /**
     * 3-50 queryForInt()를 사용하도록 수정한 getCount()
     */
    public int getCount() {
        return this.jdbcTemplate.queryForInt("select count(*) from users");
    }

    /**
     * 3-49 JdbcTemplate을 이용해 만든 getCount()
     */
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

    /**
     * Jdbc template을 활용 한 User 추가
     */
    public void add(final User user) {
        this.jdbcTemplate.update(
                "insert into users(id, name, password) values(?, ?, ?)",
                    user.getId(), user.getName(), user.getPassword());
    }

    /**
     * 3-47 내장 콜백을 사용하는 update()로 변경한 deleteAll() 메소드
     */
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    /**
     * 3-46 JdbcTemplate을 적용한 deleteAll() 메소드
     * @see springbook.dao.templatecallback.UserDao_TemplateCallback
     */
    public void deleteAll_creator() {
        this.jdbcTemplate.update(
            new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    return con.prepareStatement("delete from users");
                }
            }
        );
    }


}
