package springbook.learningtest.template;

import org.junit.Test;
import springbook.learningtest.template.nopattern.Calculator_HandlingException;
import springbook.learningtest.template.nopattern.Calculator_NotHandlingException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * 3-30 파일의 숫자 합을 계산하는 코드의 테스트
 */
public class CalcSumTest {
    @Test
    public void sumOfNumbers_NoExceptionHandling() throws IOException {
        Calculator_NotHandlingException calculator = new Calculator_NotHandlingException();
        int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
        assertThat(sum, is(10));
    }
    @Test
    public void sumOfNumbers_ExceptionHandling() throws IOException {
        Calculator_HandlingException calculator = new Calculator_HandlingException();
        int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
        assertThat(sum, is(10));
    }
}
