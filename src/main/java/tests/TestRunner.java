package tests;

import tests.annotation.*;
import tests.exception.TestInfoException;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private static List<Method> allMethodsWithAnnotationAfterAll;
    private static List<Method> allMethodsWithAnnotationAfterEach;
    private static List<Method> allMethodsWithAnnotationBeforeAll;
    private static List<Method> allMethodsWithAnnotationBeforeEach;
    private static List<Method> allMethodsWithAnnotationTest;

    public static void run(Class<?> testClass) {

        final Object testObj = initTestObj(testClass);

        initLists();

        fillLists(testClass);

        allMethodsWithAnnotationTest = sortedListWithAnnotationTest();

        runAllAnnotationFromList(testObj, allMethodsWithAnnotationBeforeAll);

        for (Method testMethod : allMethodsWithAnnotationTest) {
            if (testMethod.accessFlags().contains(AccessFlag.PRIVATE)) {
                continue;
            }

            StatisticResult resultTest;

            runAllAnnotationFromList(testObj, allMethodsWithAnnotationBeforeEach);

            try {

                testMethod.invoke(testObj);

            } catch (IllegalAccessException | InvocationTargetException e) {
                try {

                    throw e.getCause();

                } catch(TestInfoException testInfo) {

                    resultTest = new StatisticResult(testMethod.getName(), testInfo.getStatus()
                    , testInfo.getExpected(), testInfo.getActual());

                }  catch (Throwable ex) {

                    throw new RuntimeException(ex);

                }

                System.out.println(resultTest);
            }

            runAllAnnotationFromList(testObj, allMethodsWithAnnotationAfterEach);
        }

        runAllAnnotationFromList(testObj, allMethodsWithAnnotationAfterAll);
    }

    private static Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }

    private static void initLists() {
        allMethodsWithAnnotationAfterAll = new ArrayList<>();
        allMethodsWithAnnotationAfterEach = new ArrayList<>();
        allMethodsWithAnnotationBeforeAll = new ArrayList<>();
        allMethodsWithAnnotationBeforeEach = new ArrayList<>();
        allMethodsWithAnnotationTest = new ArrayList<>();
    }

    private static void fillLists(Class<?> testClass) {
        for (Method testMethod : testClass.getDeclaredMethods()) {
            if (testMethod.getAnnotation(AfterAll.class) != null) {
                allMethodsWithAnnotationAfterAll.add(testMethod);
            }
            if (testMethod.getAnnotation(AfterEach.class) != null) {
                allMethodsWithAnnotationAfterEach.add(testMethod);
            }
            if (testMethod.getAnnotation(BeforeAll.class) != null) {
                allMethodsWithAnnotationBeforeAll.add(testMethod);
            }
            if (testMethod.getAnnotation(BeforeEach.class) != null) {
                allMethodsWithAnnotationBeforeEach.add(testMethod);
            }
            if (testMethod.getAnnotation(Test.class) != null) {
                allMethodsWithAnnotationTest.add(testMethod);
            }
        }
    }

    private static void runAllAnnotationFromList(Object obj, List<Method> listWithAnnotation) {
        for (Method testMethod : listWithAnnotation) {
            try {
                testMethod.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static List<Method> sortedListWithAnnotationTest() {
        return allMethodsWithAnnotationTest.stream()
                .sorted((x, y) -> {
                    if (x.getAnnotation(Test.class).order() < y.getAnnotation(Test.class).order()) {
                        return -1;
                    } else if (x.getAnnotation(Test.class).order() > y.getAnnotation(Test.class).order()) {
                        return 1;
                    }

                    return 0;
                })
                .toList();
    }
}
