package springbook.learningtest.template.linecallback;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * 라인 템플릿 콜백을 사용한 계산기의 테스트
 */
public class Calculator_LineCallbackTest {
    Calculator_LineCallback calculator;
    String filePath;

    @Before
    public void setUp() {
        calculator = new Calculator_LineCallback();
        filePath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void calcSumTest() throws IOException {
        Integer result = calculator.calcSum(filePath);
        assertThat(result, is(10));
    }

    @Test
    public void calcMultiplyTest() throws IOException {
        Integer result = calculator.calcMultiply(filePath);
        assertThat(result, is(24));
    }
}