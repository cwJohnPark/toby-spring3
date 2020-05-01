package springbook.user.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.connection.ConnectionMaker;
import springbook.user.connection.DConnectionMaker;
import springbook.user.dao.UserDao_RuntimeDependent;

/**
 * 1-18 스프링 빈 팩토리가 사용할 설정정보를 담은 DaoFactory 클래스
 */
@Configuration
public class DaoFactory_SpringCtx {
    // 메소드의 이름이 Bean의 이름이 된다.
    @Bean
    public UserDao_RuntimeDependent userDao() {
        return new UserDao_RuntimeDependent(connectionMaker());
    }
    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

}
