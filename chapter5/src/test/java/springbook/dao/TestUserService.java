package springbook.dao;

import springbook.domain.User;
import springbook.service.UserService;

import java.util.List;

/**
 * 5-34 UserService의 테스트용 대역 클래스
 */
public class TestUserService extends UserService {
    private String id;

    // 예외를 발생시킬 User 오브젝트의 id를 지정할 수 있게 만든다.
    // 생성자를 통해 객체의 상태(state)로 저장한다.
    public TestUserService(String id) {
        this.id = id;
    }


    /**
     * 5.2 트랜잭션 서비스 추상화
     * private을 테스트할 수 없으므로 protected로 변경
     */
    protected void upgradeLevel(User user) {
        // 지정된 id의 User 오브젝트가 발견되면 예외를 던져서 작업을 강제로 중단시킨다.
        if (user.getId().equals(this.id))
            throw new TestUserServiceException();
        super.upgradeLevel(user);
    }

    /**
     * 5-35 테스트용 예외
     */
    static class TestUserServiceException extends RuntimeException {
    }
}
