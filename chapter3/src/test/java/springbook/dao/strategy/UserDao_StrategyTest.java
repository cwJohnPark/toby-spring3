package springbook.dao.strategy;

import springbook.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDao_StrategyTest {

    UserDao_Strategy dao;
    User user1;
    User user2;
    User user3;


    @Before
    public void setUp() throws Exception {
        dao = new UserDao_Strategy();

        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://192.168.99.100:3306/testdb", "spring", "book", true);

        dao.setDataSource(dataSource);

        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }

    @Test
    public void testDeleteAll() throws Exception {
        // when
        dao.deleteAll();
        // then
        int actual = dao.getCount();
        assertThat(actual, is(0));
    }

    @Test
    public void testAddAll() throws Exception {
        // given
        dao.deleteAll();
        // when
        dao.add(user1);
        dao.add(user2);
        dao.add(user3);
        // then
        int actual = dao.getCount();
        assertThat(actual, is(3));

    }
}