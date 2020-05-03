package springbook.user.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * 2-9 deleteAll()과 getCount()가 추가된 addAndGet() 테스트
 */
public class UserDaoTest_2_9 {

    @Test
    public void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext-2-1.xml");

        // 테스트할 대상인 UserDao의 오브젝트를 가져와 메소드를 호출한다.
        UserDao_DataSource dao = context.getBean("userDao", UserDao_DataSource.class);
        dao.deleteAll();

        //count 테스트
        assertThat(dao.getCount(), is(0));

        User user = new User();
        user.setId("gyumee");
        user.setName("박성철");
        user.setPassword("springno1");

        dao.add(user);

        //count 테스트
        assertThat(dao.getCount(), is(1));

        User user2 = dao.get(user.getId());

        assertThat(user.getName(), is(user2.getName()));
        assertThat(user.getPassword(), is(user2.getPassword()));
    }

}