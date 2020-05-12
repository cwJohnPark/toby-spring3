package springbook.dao.strategy;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import springbook.domain.User;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * 3.4.1 JdbcContext 클래스 분리 테스트
 * - dataSource의 의존성이 두 객체를 향하므로 좋지 않음
 */
public class UserDao_JdbcContextTest {

    UserDao_JdbcContext dao;
    DataSource dataSource;
    JdbcContext jdbcContext;

    User user1;
    User user2;
    User user3;

    @Before
    public void Setup() {
        dataSource = new SingleConnectionDataSource("jdbc:mysql://192.168.99.100:3306/testdb", "spring", "book", true);

        jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);
        dao = new UserDao_JdbcContext();
        dao.setDataSource(dataSource);
        dao.setJdbcContext(jdbcContext);

        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");

    }

    @Test
    public void add() throws Exception{
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);
        int actual = dao.getCount();
        int expected = 2;

        assertThat(actual, is(expected));

    }

    @Test
    public void deleteAll() throws Exception {
        dao.deleteAll();
        int actual = dao.getCount();
        assertThat(actual, is(0));
    }
}