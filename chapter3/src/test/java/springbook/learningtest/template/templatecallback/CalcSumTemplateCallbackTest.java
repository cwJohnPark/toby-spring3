package springbook.learningtest.template.templatecallback;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * 3-36 템플릿 콜백 패턴을 적용한 Calculator의 Test
 */
public class CalcSumTemplateCallbackTest {
    Calculator calculator;
    String filepath;

    @Before
    public void setUp() throws Exception {
        this.calculator = new Calculator();
        this.filepath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(this.filepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(this.filepath), is(24));
    }


}