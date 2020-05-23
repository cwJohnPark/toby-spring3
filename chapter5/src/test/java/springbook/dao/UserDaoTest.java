package springbook.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.domain.Level;
import springbook.domain.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * 5-5 수정된 테스트 픽스처
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {

    @Autowired UserDao dao;

    User user1, user2, user3;

    @Before
    public void setUp() {

        this.user1 = new User("gyumee", "박성철", "springno1", Level.BASIC, 1, 0);
        this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10);
        this.user3 = new User("bumjin", "박범진", "springno3", Level.GOLD, 100, 40);

        dao.deleteAll();
        dao.add(user1);
        dao.add(user2);
        dao.add(user3);
    }

    @Test
    public void addAndGet() throws Exception {
        User userget1 = dao.get(user1.getId());
        checkSameUser(userget1, user1);

        User userget2 = dao.get(user2.getId());
        checkSameUser(userget2, user2);
    }

    /**
     * 5-7 새로운 필드를 포함하는 User 필드 값 검증 메소드
     */
    private void checkSameUser(User u1, User u2) {
        assertThat(u1.getId(), is(u2.getId()));
        assertThat(u1.getPassword(), is(u2.getPassword()));
        assertThat(u1.getName(), is(u2.getName()));
        assertThat(u1.getLevel(), is(u2.getLevel()));
        assertThat(u1.getLogin(), is(u2.getLogin()));
    }
}