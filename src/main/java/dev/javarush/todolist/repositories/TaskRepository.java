package dev.javarush.todolist.repositories;

import dev.javarush.todolist.model.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class TaskRepository {

    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(task);
            //session.merge(task);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(task);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTaskById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Task t where t.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void unbindTagFromTask(Long taskId, Long tagId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM task_tag WHERE task_id = :taskId and tag_id = :tagId")
                    .setParameter("taskId", taskId)
                    .setParameter("tagId", tagId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(task);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Task findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Task.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public Task findByTitle(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Task where title = :name", Task.class)
                    .setParameter("name", name)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Task> findAllByUserId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Task where userId = :id", Task.class)
                    .setParameter("id", id)
                    .list();
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Integer> findTasksByTagId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<Integer> query = session.createNativeQuery("select task_id from task_tag where tag_id = :id", Integer.class);
            query.setParameter("id", id);
            return query.getResultList();
        } catch (Exception e) {
            return List.of();
        }
    }
}
