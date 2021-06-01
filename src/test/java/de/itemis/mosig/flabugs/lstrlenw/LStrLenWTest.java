package de.itemis.mosig.flabugs.lstrlenw;

import org.junit.jupiter.api.RepeatedTest;

public class LStrLenWTest {

    @RepeatedTest(100)
    public void testSomething() {
        System.out.println(LStrLenW.myMethod("testString"));
    }
}
