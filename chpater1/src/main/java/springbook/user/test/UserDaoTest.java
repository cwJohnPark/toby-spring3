package springbook.user.test;

import springbook.user.dao.ConnectionMaker;
import springbook.user.dao.DConnectionMaker;
import springbook.user.dao.UserDao_RuntimeDependent;

/**
 * 1-12 관계설정 책임이 추가된 UserDao 클라이언트인 main() 메소드
 */
public class UserDaoTest {
    public static void main(String[] args) {
        // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고, 오브젝트를 만든다.
        ConnectionMaker connectionMaker = new DConnectionMaker();

        // 사용할 ConnectionMaker 타입의 오브젝트를 제공하여 의존관계 설정이 가능함
        UserDao_RuntimeDependent dao = new UserDao_RuntimeDependent(connectionMaker);
        //...
    }
}
