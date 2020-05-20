package springbook.dao.jdbctemplate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import springbook.domain.User;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserDao_JdbcTemplateRedundantTest {
    UserDao_JdbcTemplate_Redundant dao;
    User user1;
    User user2;
    User user3;

    @Before
    public void setUp() throws Exception {
        dao = new UserDao_JdbcTemplate_Redundant();
        dao.setDataSource(new SingleConnectionDataSource("jdbc:mysql://192.168.99.100:3306/testdb", "spring", "book", true));
        user1 = new User("gyumee", "박성철", "springno1");
        user2 = new User("leegw700", "이길원", "springno2");
        user3 = new User("bumjin", "박범진", "springno3");
    }

    /**
     * 3-54 데이터가 없는 경우에 대한 검증 코드가 추가된 getAll() 테스트
     */
    @Test
    public void getAllNegativeTest() throws Exception{
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));
    }

    /**
     * 3-52 getAll()에 대한 테스트
     */
    @Test
    public void getAll() throws Exception {
        dao.deleteAll();

        dao.add(user1); // Id; gyumee
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    /**
     * 3-52 User 오브젝트의 내용을 비교하는 검증 코드,
     * 테스트에서 반복적으로 사용되므로 분리해놓았다.
     */
    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));

    }

    @Test
    public void getUserFailure() throws Exception {
        // given
        // when
        try {
            dao.get("unknown_id");
            fail();
        } catch (EmptyResultDataAccessException e) {
            // then
            // success
        }

    }

}