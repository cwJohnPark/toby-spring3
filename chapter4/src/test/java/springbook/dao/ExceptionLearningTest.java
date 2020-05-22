package springbook.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import springbook.domain.User;
import springbook.exception.DuplicateUserIdException;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

/**
 * 4-23 DataSource 빈을 주입받도록 만든 UserDaoTest
 */
public class ExceptionLearningTest {
    @Autowired UserDao dao;
    @Autowired DataSource dataSource;

    User user1;

    @Before
    public void setup() throws Exception {
        user1 = new User("gyumee", "박성철", "springno1");
    }

    @Test
    public void sqlExceptionTranslate() throws Exception {
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        } catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            //assertThat(set.translate(null, null, sqlEx), isA(DuplicateKeyException.class));
        }
    }
}
