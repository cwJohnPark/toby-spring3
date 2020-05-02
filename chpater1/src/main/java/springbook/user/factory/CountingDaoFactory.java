package springbook.user.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.CountingConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.UserDao_RuntimeDependent;

/**
 * 1-31 CountingConnectionMaker 의존관계가 추가된 DI 설정용 클래스
 */
@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao_RuntimeDependent userDao() {
        return new UserDao_RuntimeDependent(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
