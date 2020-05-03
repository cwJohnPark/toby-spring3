package springbook.user.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * 2-5 JUnit 프레임워크를 적용한 UserDaoTest
 */
public class UserDaoTest_2_5 {

    @Test
    public void addAndGet() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext-2-1.xml");

        // 테스트할 대상인 UserDao의 오브젝트를 가져와 메소드를 호출한다.
        UserDao_DataSource dao = context.getBean("userDao", UserDao_DataSource.class);

        // 테스터에 사용할 입력 값(User 오브젝트)을 직접 코드에서 만들어 넣어준다.
        User user = new User();
        user.setId("user2-4");
        user.setName("백기선");
        user.setPassword("married");

        //dao.add(user);

        User user2 = dao.get("user2-4");

        assertThat(user.getName(), is(user2.getName()));
        assertThat(user.getPassword(), is(user2.getPassword()));
        assertThat(user.getName(), is(user2.getName()));
    }



}