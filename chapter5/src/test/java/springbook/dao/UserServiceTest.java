package springbook.dao;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.domain.Level;
import springbook.domain.User;
import springbook.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * 5-16 UserServiceTest 클래스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-5-15.xml")
public class UserServiceTest {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    // 테스트 픽스쳐
    List<User> users;

    /**
     * 5-19 리스트로 만든 테스트 픽스쳐
     */
    @Before
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, 49, 0)
                ,new User("joytouch", "강명성", "p2", Level.BASIC, 50, 0)
                ,new User("erwins", "신승한", "p3", Level.SILVER, 60, 29)
                ,new User("madnite1", "이상호", "p4", Level.SILVER, 60, 30)
                ,new User("green", "오민규", "p5", Level.GOLD, 100, 100)
        );
    }

    @Test
    public void upgradeLevelsTest() throws Exception {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    /**
     * 5-17 userService 빈의 주입을 확인하는 테스트
     */
    @Test
    public void bean() throws Exception {
        assertThat(this.userService, is(notNullValue()));
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

}
