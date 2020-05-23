package springbook.dao;

import springbook.domain.User;
import springbook.domain.User_ConstantLevel;

import java.util.List;

/**
 * 4-19 기술에 독립적인 DAO 인터페이스
 */
public interface UserDao {
    // RuntimeException 이므로 throws를 작성할 필요가 없다.
    void add(User user);
    User get(String id);
    List<User> getAll();
    int getCount();
    void deleteAll();
}
