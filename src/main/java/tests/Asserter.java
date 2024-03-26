package tests;

import tests.exception.TestInfoException;

public class Asserter {
    public static void assertEquals(int expected, int actual) {

        if (expected != actual) {
            throw new TestInfoException(expected, actual, "is not correct");
        } else {
            throw new TestInfoException(expected, actual, "is correct");
        }

    }
}
