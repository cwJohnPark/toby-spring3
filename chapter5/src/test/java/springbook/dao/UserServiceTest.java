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
import springbook.domain.UserLevelUpgradePolicy;
import springbook.domain.UserLevelUpgradePolicyImpl;
import springbook.service.UserService;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static springbook.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.service.UserService.MIN_RECOMMEND_FOR_GOLD;

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
        UserLevelUpgradePolicy policy = new UserLevelUpgradePolicyImpl();

        userService = new UserService();
        userService.setUserDao(userDao);
        userService.setPolicy(policy);

        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0)
                ,new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0)
                ,new User("erwins", "신승한", "p3", Level.SILVER, MIN_LOGCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD-1)
                ,new User("madnite1", "이상호", "p4", Level.SILVER, MIN_LOGCOUNT_FOR_SILVER+10, MIN_RECOMMEND_FOR_GOLD)
                ,new User("green", "오민규", "p5", Level.GOLD, Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }


    /**
     * 5-36 예외 발생 시 작업 취소 여부 테스트
     */
    @Test
    public void upgradeAllOrNothing() throws Exception {
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        userDao.deleteAll();

        for(User user : users)
            userDao.add(user);

        try {
            testUserService.upgradeLevels();
        } catch (TestUserService.TestUserServiceException e) {
            // 성공
        }

        // 테스트는 실패한다.
        // 두 번째 사용자 레벨은 SILVER로 바뀌고나서 예외 발생 후 그대로 유지가 되었기 때문
        checkLevelUpgraded(users.get(1), false);

    }



    /**
     * 5-33 업그레이드 정책 분리 후 유저 업그레이드 테스트
     */
    @Test
    public void upgradeLevels_separtePolicy() throws Exception {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels_separatePolicy();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    /**
     * 5-30 개선한 upgradeLevels() 테스트
     */
    @Test
    public void upgradeLevels_revision() throws Exception {
        userDao.deleteAll();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();

        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            // 업데이트가 발생했다.
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        } else {
            // 업데이트가 발생하지 않았다.
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
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
