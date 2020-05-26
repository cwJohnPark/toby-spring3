package springbook.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.dao.impl.UserDaoJdbc;
import springbook.domain.Level;
import springbook.domain.User;
import springbook.service.UserService;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * 5-16 UserServiceTest 클래스
 */
public class UserServiceTest {

    UserDaoJdbc userDao;
    UserService userService;

    // 테스트 픽스쳐
    List<User> users;

    /**
     * 5-19 리스트로 만든 테스트 픽스쳐
     */
    @Before
    public void setUp() {
        userDao = new UserDaoJdbc();
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://192.168.99.100:3306/testdb", "spring", "book", true);
        userDao.setDataSource(dataSource);
        userService = new UserService();
        userService.setUserDao(userDao);

        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, 49, 0)
                ,new User("joytouch", "강명성", "p2", Level.BASIC, 50, 0)
                ,new User("erwins", "신승한", "p3", Level.SILVER, 60, 29)
                ,new User("madnite1", "이상호", "p4", Level.SILVER, 60, 30)
                ,new User("green", "오민규", "p5", Level.GOLD, 100, 100)
        );
    }

    /**
     * 5-21 add() 메소드의 테스트
     */
    @Test
    public void add(){
        userDao.deleteAll();

        // GOLD 레벨이 이미 지정된 User임
        // 레벨을 초기화하지 않아야 한다.
        User userWithLevel = users.get(4); // GOLD 레벨
        // 레벨이 비어 있는 User
        // 로직에 따라 등록 중에 BASIC 레벨도 설정돼야 한다.
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(userWithoutLevel.getLevel()));

    }

    @Test
    public void upgradeLevelsTest() throws Exception {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels_lowCohesion();

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
