package springbook.user.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * 2-13 get() 메소드의 예외상황에 대한 테스트
 */
public class UserDaoTest_2_13 {
    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao_DataSource dao = context.getBean("userDao", UserDao_DataSource.class);
        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        /**
         * 이 메소드 실행 중에 예외가 발생해야 한다.
         * 예외가 발생하지 않으면 테스트가 실패한다.
         */
        dao.get("unknown_id");
    }

}
