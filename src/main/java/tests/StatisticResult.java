package tests;

public class StatisticResult {
    private String nameMethod;
    private String statusMethod;
    private int expected;
    private int actual;

    public StatisticResult(String nameMethod, String statusMethod, int expected, int actual) {
        this.nameMethod = nameMethod;
        this.statusMethod = statusMethod;
        this.expected = expected;
        this.actual = actual;
    }

    public String getNameMethod() {
        return nameMethod;
    }

    public void setNameMethod(String nameMethod) {
        this.nameMethod = nameMethod;
    }

    public String getStatusMethod() {
        return statusMethod;
    }

    public void setStatusMethod(String statusMethod) {
        this.statusMethod = statusMethod;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return String.format("Название метода: %s\n"
        + "Статус метода: %s\n"
        + "Ожидали получить: %d; Получили: %d"
        , nameMethod, statusMethod, expected, actual);
    }
}
