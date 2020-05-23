package springbook.exception;

/**
 * 4-13 아이디 중복 시 사용하는 예외
 */
public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
