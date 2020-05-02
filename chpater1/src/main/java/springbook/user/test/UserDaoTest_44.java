package springbook.user.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.dao.UserDao_DataSource;
import springbook.user.factory.DaoFactory_DataSource;

import javax.sql.DataSource;

/**
 * 1-44 DataSource 타입의 빈을 받아서 DI
 */
public class UserDaoTest_44 {
    public static void main(String[] args) {
        // hide cglib illegal reflective access
        System.err.close();
        System.setErr(System.out);
        //--

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory_DataSource.class);
        UserDao_DataSource dao = context.getBean("userDao", UserDao_DataSource.class);
        DataSource dataSource = context.getBean("dataSource", DataSource.class);

        System.out.println(dao);
        System.out.println(dataSource);
    }
}
