package tests;

import tests.annotation.*;

public class TestRunnerDemo {

    // private никому не видно
    // default (package-private) внутри пакета
    // protected внутри пакета + наследники
    // public всем

    public static void main(String[] args) {
        TestRunner.run(TestRunnerDemo.class);
    }

    @Test(order = 3)
    void test1() {
        System.out.println("test1");
        Asserter.assertEquals(1, 1);
    }

    @Test
    public void test2() {
        System.out.println("test2");
        Asserter.assertEquals(1, 0);
    }

    @Test
    void test3() {
        System.out.println("test3");
    }

    @BeforeAll
    void something1() {
        System.out.println("something. BeforeAll1");
    }

    @BeforeAll
    void something5() {
        System.out.println("something. BeforeAll2");
    }

    @BeforeEach
    void something2() {
        System.out.println("something. BeforeEach");
    }

    @AfterEach
    void something3() {
        System.out.println("something. AfterEach");
    }

    @AfterAll
    void something4() {
        System.out.println("something. AfterAll1");
    }

    @AfterAll
    void something6() {
        System.out.println("something. AfterAll2");
    }
}
