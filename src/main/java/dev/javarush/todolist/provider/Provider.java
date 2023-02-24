package dev.javarush.todolist.provider;

import dev.javarush.todolist.model.Tag;
import dev.javarush.todolist.model.Task;
import dev.javarush.todolist.model.TaskComment;
import dev.javarush.todolist.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Provider implements SessionProvider {

    @Override
    public SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        properties.setProperty("hibernate.connection.username", "postgres");
        properties.setProperty("hibernate.connection.password", "qwerty");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");


        return new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(TaskComment.class)
                .buildSessionFactory();
    }
}
