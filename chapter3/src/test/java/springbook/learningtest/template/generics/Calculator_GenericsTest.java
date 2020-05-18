package springbook.learningtest.template.generics;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Calculator_GenericsTest {
    Calculator_Generics calculator = new Calculator_Generics();
    String numFilepath = getClass().getResource("/numbers.txt").getPath();

    /**
     * 3-44 concatenate() 메소드에 대한 테스트
     */
    @Test
    public void concatenateStrings() throws IOException {
        assertThat(calculator.concatenate(this.numFilepath), is("1234"));
    }
}