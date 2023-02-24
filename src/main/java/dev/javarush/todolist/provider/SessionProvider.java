package dev.javarush.todolist.provider;

import org.hibernate.SessionFactory;

public interface SessionProvider {

    SessionFactory getSessionFactory();
}
