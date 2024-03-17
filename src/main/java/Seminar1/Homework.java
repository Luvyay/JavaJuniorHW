package Seminar1;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Homework {
    /**
     * Реалзиовать методы, описанные ниже:
     */

    /**
     * Вывести на консоль отсортированные (по алфавиту) имена персонов
     */
    public void printNamesOrdered(List<Streams.Person> persons) {
        persons.stream()
                .map(Streams.Person::getName)
                .sorted()
                .forEach(System.out::println);
    }

    /**
     * В каждом департаменте найти самого взрослого сотрудника.
     * Вывести на консоль мапипнг department -> personName
     * Map<Department, Person>
     */
    public Map<Streams.Department, Streams.Person> printDepartmentOldestPerson(List<Streams.Person> persons) {
        Map<Streams.Department, Streams.Person> collect = persons.stream()
                .map(it -> it.getDepartment())
                .distinct()
                .collect(Collectors.toMap(it -> it,
                        department -> persons.stream()
                                .filter(person -> person.getDepartment().equals(department))
                                .max(Comparator.comparing(Streams.Person::getAge))
                                .orElseThrow()));

        return collect;
    }

    /**
     * Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
     */
    public List<Streams.Person> findFirstPersons(List<Streams.Person> persons) {
        return persons.stream()
                .filter(it -> it.getAge() < 30)
                .filter(it -> it.getSalary() > 50_000)
                .limit(10)
                .toList();
    }

    /**
     * Найти депаратмент, чья суммарная зарплата всех сотрудников максимальна
     */
    public Optional<Streams.Department> findTopDepartment(List<Streams.Person> persons) {
        Optional<Streams.Department> result = persons.stream()
                .map(Streams.Person::getDepartment)
                .distinct()
                .collect(Collectors.toMap(department -> department,
                        department -> persons.stream()
                                .filter(person -> person.getDepartment().equals(department))
                                .mapToDouble(Streams.Person::getSalary)
                                .reduce(0, Double::sum)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);

        return result;
    }
}
