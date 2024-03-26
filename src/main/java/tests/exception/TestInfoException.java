package tests.exception;

public class TestInfoException extends RuntimeException {
    private int expected;
    private int actual;
    private String status;

    public TestInfoException(int expected, int actual, String status) {
        this.expected = expected;
        this.actual = actual;
        this.status = status;
    }

    public int getExpected() {
        return expected;
    }

    public int getActual() {
        return actual;
    }

    public String getStatus() {
        return status;
    }
}
