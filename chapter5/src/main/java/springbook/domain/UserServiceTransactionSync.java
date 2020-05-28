package springbook.domain;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import springbook.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * 5-41 트랜잭션 동기화 방식을 적용한 UserService
 */
public class UserServiceTransactionSync {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private DataSource dataSource;
    private UserDao userDao;

    // Connection을 생성할 때 사용할 DataSource를 DI 받도록 한다.
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() throws Exception {
        // 트랜잭션 동기화 관리자를 이용해 동기화 작업을 초기화 한다.
        TransactionSynchronizationManager.initSynchronization();
        // DB 커넥션을 생성하고 트랜잭션을 시작한다.
        // 이후의 DAO 작업은 모두 여기서 시작한 트랜잭션 안에서 진행된다.
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
            c.commit(); // 정상적으로 작업을 마치면 트랜잭션 커밋
        } catch (Exception e) {
            c.rollback(); // 예외가 발생하면 롤백한다.
            throw e;
        } finally {
            // 스프링 유틸리티 메소드를 이용해 DB 커넥션을 안전하게 닫는다.
            DataSourceUtils.releaseConnection(c, dataSource);
            // 동기화 작업 종료 및 정리
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    /**
     * 5-28 간결해진 upgradeLevel()
     */
    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    /**
     * 5-23 업그레이드 가능 확인 메소드
     */
    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch(currentLevel) {
            // 레벨 별로 구분하여 조건을 판단한다.
            case BASIC : return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER : return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD : return false;
            // 현재 로직에서 다룰 수 없는 레벨이 주어지면 예외를 발생시킨다.
            // 새로운 레벨이 추가되고 로직을 수정하지 않으면 에러가 나서 확인할 수 있다.
            default : throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

}
