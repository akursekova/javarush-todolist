package dev.javarush.todolist.repositories;

import dev.javarush.todolist.command.TagCommand;
import dev.javarush.todolist.model.Tag;
import dev.javarush.todolist.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.*;

public class TagRepository {

    private final SessionFactory sessionFactory;

    public TagRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Tag tag) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(tag);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public Optional<Tag> findTagByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Tag where name = :name", Tag.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Tag> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Tag", Tag.class)
                    .list();
        } catch (Exception e) {
            return List.of();
        }
    }

    public void deleteByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Tag where name = :name")
                    .setParameter("name", name)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTagById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Tag t where t.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Long tagId, TagCommand tagCommand) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Tag tag = session.get(Tag.class, tagId);
            tag.setName(tagCommand.getName());
            tag.setColor(tagCommand.getColor());
            session.merge(tag);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Tag findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Tag.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Set<Tag> findTagsByIds(Collection<Long> ids) {
        try (Session session = sessionFactory.openSession()) {
            return new HashSet<>(session.createQuery("from Tag where id in :ids", Tag.class)
                    .setParameter("ids", ids)
                    .list());
        } catch (Exception e) {
            return Set.of();
        }
    }
}
