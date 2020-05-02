package springbook.user.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.UserDao_DataSource;
import springbook.user.dao.UserDao_SetterDI;

import javax.sql.DataSource;


@Configuration
public class DaoFactory_DataSource {

    /**
     * 1-44 DataSource 타입의 빈을 DI 받는 userDao() 빈 정의 메소드
     */
    @Bean
    public UserDao_DataSource userDao() {
        UserDao_DataSource userDao = new UserDao_DataSource();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    /**
     * 1-43 DataSource 타입의 dataSource 빈 정의 메소드
     */
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/springbook");
        dataSource.setUsername("spring");
        dataSource.setPassword("book");

        return dataSource;
    }


}
