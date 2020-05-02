package springbook.user.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.UserDao_RuntimeDependent;
import springbook.user.dao.UserDao_SetterDI;

/**
 * 1-34 수정자 메소드 DI를 사용하는 팩토리 메소드
 */
@Configuration
public class DaoFactory_SetterDI {

    @Bean
    public UserDao_SetterDI userDao_34() {
        UserDao_SetterDI userDao = new UserDao_SetterDI();
        userDao.setConnectionMaker(connectionMaker_34());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker_34() {
        return new DConnectionMaker();
    }

}
