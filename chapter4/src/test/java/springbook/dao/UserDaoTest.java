package springbook.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.domain.User;
import springbook.exception.DuplicateUserIdException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext-4-21.xml")
public class UserDaoTest {

    // 스프링 컨텍스트가 같은 타입의 빈을 찾아준다.
    // UserDaoJdbc는 UserDao 인터페이스와 같은 타입이므로 DI가 성공한다.
    @Autowired
    private UserDao dao;

    User user1;

    @Before
    public void setup() throws Exception {
        user1 = new User("gyumee", "박성철", "springno1");
    }


    @Test(expected= DuplicateUserIdException.class)
    public void duplicateKey() throws Exception {
        // given
        // when
        dao.deleteAll();
        // then
        dao.add(user1);
        dao.add(user1); // 강제로 같은 사용자를 두 번 등록한다. 여기서 예외가 발생해야 한다.
        fail();
    }

    @Test
    public void testDeleteAllUsers() throws Exception {
        // given
        // when
        dao.deleteAll();
        // then
        assertThat(dao.getCount(), is(0));
    }
}