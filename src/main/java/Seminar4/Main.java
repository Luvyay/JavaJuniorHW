package Seminar4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Configuration configuration = new Configuration().configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            persistMethod(sessionFactory);
            showBd();
            methodFind(sessionFactory);
            methodMerge(sessionFactory);
            showBd();
            methodRemove(sessionFactory);
            showBd();
            methodCreateQuery(sessionFactory);
        }
    }

    private static void persistMethod(SessionFactory sessionFactory) {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("An");
        student1.setSecondName("Po");
        student1.setAge(25);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Ar");
        student2.setSecondName("Ve");
        student2.setAge(19);

        Student student3 = new Student();
        student3.setId(3L);
        student3.setFirstName("Test");
        student3.setSecondName("LastTest");
        student3.setAge(100);

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.persist(student1);
            session.persist(student2);
            session.persist(student3);

            tx.commit();
        }
    }

    private static void methodFind(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Student student1 = session.find(Student.class, 1L);
            System.out.println("-------------------------");
            System.out.println(student1);
        }
    }

    private static void methodMerge(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Student student3 = session.find(Student.class, 3L);

            Transaction tx = session.beginTransaction();

            student3.setFirstName("Sta");
            student3.setSecondName("Tish");
            student3.setAge(5);

            session.merge(student3);

            tx.commit();
        }
    }

    private static void methodRemove(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Student student3 = session.find(Student.class, 3L);

            Transaction tx = session.beginTransaction();

            session.remove(student3);

            tx.commit();
        }
    }

    private static void methodCreateQuery(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("select firstName from students where age > 20"
                    , Student.class);
            List<Student> resultList = query.getResultList();

            System.out.println("***********************************");
            System.out.println(resultList);
        }
    }

    private static void showBd() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
             Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("select id, firstName, secondName, age from students");

            System.out.println("-----------------------------------");
            while (rs.next()) {
                System.out.println("id = " + rs.getInt("id")
                        + " firstName = " + rs.getString("firstName")
                        + " secondName = " + rs.getString("secondName")
                        + " age = " + rs.getInt("age"));
            }
        }
    }
}
