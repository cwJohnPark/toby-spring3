package springbook.domain;

/**
 * 5-1 정수형 상수 값으로 정의한 사용자 레벨
 */
public class User_ConstantLevel {
    private static final int BASIC = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;

    int level;

    // 범위에 벗어나는 값을 입력하면 심각한 버그가 만들어진다.
    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    String id;
    String name;
    String password;

    public User_ConstantLevel(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     *  자바빈 규약을 따르는 클래스에 생성자를 명시적으로 추가했을 때는,
     *  파라미터가 없는 디폴트 생성자도 함께 정의해주어야 함
     */
    public User_ConstantLevel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
