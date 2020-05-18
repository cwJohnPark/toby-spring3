package springbook.learningtest.template.generics;

/**
 * 3-41 타입 파라미터를 적용한 LineCallback
 * @param <T>
 */
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
