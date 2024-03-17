package Seminar1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Streams {
    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            persons.add(new Person(
                    "Person #" + i,
                    ThreadLocalRandom.current().nextInt(20, 61),
                    ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0,
                    departments.get(ThreadLocalRandom.current().nextInt(departments.size()))
            ));
        }

        System.out.println(persons);

        Homework homework = new Homework();
        System.out.println("-------------");
        homework.printNamesOrdered(persons);
        System.out.println("-------------");
        System.out.println(homework.printDepartmentOldestPerson(persons));
        System.out.println("-------------");
        System.out.println(homework.findFirstPersons(persons));
        System.out.println("-------------");
        System.out.println(homework.findTopDepartment(persons));
    }

    public static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public Person(String name, int age, double salary, Department department) {
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.department = department;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getSalary() {
            return salary;
        }

        public Department getDepartment() {
            return department;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Double.compare(person.salary, salary) == 0 && Objects.equals(name, person.name)
                    && Objects.equals(department, person.department);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, salary, department);
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }
    }

    public static class Department {
        private String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
 }
