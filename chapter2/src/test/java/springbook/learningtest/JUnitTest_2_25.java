package springbook.learningtest;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 *  2-25 개선한 JUnit 테스트 오브젝트 생성에 대한 학습 테스트
 */
public class JUnitTest_2_25 {
    static Set<JUnitTest_2_25> testObjects = new HashSet<JUnitTest_2_25>();

    @Test public void test1() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }
    @Test public void test2() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }
    @Test public void test3() {
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }
}
