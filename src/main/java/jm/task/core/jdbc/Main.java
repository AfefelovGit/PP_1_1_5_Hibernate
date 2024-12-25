package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        User user;

        // создание сервиса для работы с базой данных
        UserServiceImpl userService = new UserServiceImpl();

        // создание таблицы
        userService.createUsersTable();

        // Добавим нового пользователя
        userService.saveUser("Aleksandr", "Aleksandrov", (byte) 35);
        user = userService.getUserById(1L);
        if (user != null) {
            System.out.printf("User: id = %d, name = '%s', lastName = '%s', age = '%d'.\n",
                    user.getId(), user.getName(), user.getLastName(), user.getAge());
        }

        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        user = userService.getUserById(2L);
        if (user != null) {
            System.out.printf("User: id = %d, name = '%s', lastName = '%s', age = '%d'.\n",
                    user.getId(), user.getName(), user.getLastName(), user.getAge());
        }

        userService.saveUser("Sergej", "Sergeev", (byte) 45);
        user = userService.getUserById(3L);
        if (user != null) {
            System.out.printf("User: id = %d, name = '%s', lastName = '%s', age = '%d'.\n",
                    user.getId(), user.getName(), user.getLastName(), user.getAge());
        }

        userService.saveUser("Victor", "Victorov", (byte) 55);
        user = userService.getUserById(4L);
        if (user != null) {
            System.out.printf("User: id = %d, name = '%s', lastName = '%s', age = '%d'.\n",
                    user.getId(), user.getName(), user.getLastName(), user.getAge());
        }
        System.out.println();

        // удалим строку по id
        userService.removeUserById(3L);

        // получим весь список строк
        List<User> usersMas = userService.getAllUsers();
        for (User userEl : usersMas) {
            System.out.println(userEl);
        }

        // удаление всех строк таблицы
        userService.cleanUsersTable();

        // Удаление таблицы
        userService.dropUsersTable();

        // закроем SessionFactory
        Util.closeSessionFactory();
    }
}
