package springbook.learningtest;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 *  2-24 JUnit 테스트 오브젝트 생성에 대한 학습 테스트
 */
public class JUnitTest_2_24 {
    static JUnitTest_2_24 testObject;

    @Test public void test1() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }
    @Test public void test2() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }
    @Test public void test3() {
        assertThat(this, is(not(sameInstance(testObject))));
        testObject = this;
    }

}
