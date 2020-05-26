package springbook.domain;

/**
 * 5-26 업그레이드 순서를 담고 있도록 수정한 Level
 */
public enum Level {
    BASIC(1, 2), SILVER(2, 3), GOLD(3, 3);

    private final int value;
    // 다음 단계의 레벨을 스스로 갖도록 Level 타입에 next 변수를 추가한다.
    private Integer next;

    // DB에 저장할 값을 넣어줄 생성자를 만들어준다.
    Level(int value, int next) {
        this.value = value;
        this.next = next;
    }

    public int intValue() {
        return value;
    }

    public Level nextLevel() {
        return Level.valueOf(next);
    }

    public static Level valueOf(int value) {
        switch (value) {
            case -1 : return null;
            case 1 : return BASIC;
            case 2 : return SILVER;
            case 3 : return GOLD;
            default: throw new AssertionError("Unknown value: " + value);
        }
    }
}
