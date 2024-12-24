package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TypeMismatchException;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    // запрос на создание таблицы
    private static final String CREAT_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS mydb.user (
              `id` BIGINT NOT NULL AUTO_INCREMENT,
              `name` VARCHAR(45) NOT NULL,
              `lastName` VARCHAR(45) NOT NULL,
              `age` TINYINT NOT NULL,
              PRIMARY KEY (`id`),
              UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
            ENGINE = InnoDB
            DEFAULT CHARACTER SET = utf8;
            """;

    // запрос на удаление всей таблицы
    private static final String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS mydb.user;";

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {


    }

    // создание таблицы
    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()) {

            Transaction tx = session.beginTransaction();
            Query<User> query = session.createSQLQuery(CREAT_TABLE_SQL);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }
    }

    // удаление таблицы
    @Override
    public void dropUsersTable() {
        Transaction tx;

        try (Session session = sessionFactory.openSession()) {

            tx = session.beginTransaction();
            Query<User> query = session.createSQLQuery(DELETE_TABLE_SQL);
            query.executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }

    }

    // запись нового User
    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction tx;

        try (Session session = sessionFactory.openSession()) {

            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();

        } catch (TypeMismatchException e) {
            System.out.printf("Ошибка соответствия типов.\n%s\n", e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }
    }

    // удаление User по id
    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {

            Transaction tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            tx.commit();

        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }
    }

    // получение всех строк
    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> users = null;

        try (Session session = sessionFactory.openSession()) {

            tx = session.beginTransaction();

            Query<User> query = session.createQuery("from User", User.class);
            users = query.getResultList();
            tx.commit();

        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }

        return users;
    }

    // очистка таблицы
    @Override
    public void cleanUsersTable() {
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {

            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            tx.commit();

        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }
    }

    // получение объектa  User по id
    @Override
    public User getUserById(long id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            user = session.get(User.class, id);
            tx.commit();
        } catch (Exception e) {
            System.out.printf("Ошибка: %s\n",e.getMessage());
            throw e;
        }
        return user;
    }
}
