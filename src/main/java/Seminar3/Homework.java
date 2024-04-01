package Seminar3;

import java.sql.*;

/**
 * 0. Разобрать код с семниара
 * 1. Повторить код с семниара без подглядываний на таблице Student с полями:
 * 1.1 id - int
 * 1.2 firstName - string
 * 1.3 secondName - string
 * 1.4 age - int
 * 2.* Попробовать подключиться к другой БД
 * 3.** Придумать, как подружить запросы и reflection:
 * 3.1 Создать аннотации Table, Id, Column
 * 3.2 Создать класс, у которого есть методы:
 * 3.2.1 save(Object obj) сохраняет объект в БД
 * 3.2.2 update(Object obj) обновляет объект в БД
 * 3.2.3 Попробовать объединить save и update (сначала select, потом update или insert)
 */
public class Homework {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:university")) {
            createTable(connection);
            fillTableDate(connection);
            showDateFromDB(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showDateFromDB(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select id, firstName, secondName, age from student");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String secondName = resultSet.getString("secondName");
                int age = resultSet.getInt("age");

                System.out.println("Student: id = " + id + ", firstName = " + firstName
                + ", secondName = " + secondName + ", age = " + age);
            }
        }
    }

    private static void fillTableDate(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    insert into student(id, firstName, secondName, age) values
                    (1, 'Andrey', 'Andreev', 25),
                    (2, 'Andrey1', 'Andreev1', 21),
                    (3, 'Andrey2', 'Andreev2', 15),
                    (4, 'Andrey3', 'Andreev3', 40)
                    """);
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    create table student(
                        id int,
                        firstName varchar(256),
                        secondName varchar(256),
                        age int
                    )
                    """);
        }
    }
}
