package dev.javarush.todolist.listener;

import dev.javarush.todolist.components.PasswordHashing;
import dev.javarush.todolist.provider.Provider;
import dev.javarush.todolist.provider.SessionProvider;
import dev.javarush.todolist.repositories.TagRepository;
import dev.javarush.todolist.repositories.TaskCommentRepository;
import dev.javarush.todolist.repositories.TaskRepository;
import dev.javarush.todolist.repositories.UserRepository;
import dev.javarush.todolist.services.TagService;
import dev.javarush.todolist.services.TaskCommentService;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;
import dev.javarush.todolist.services.impl.TagServiceImpl;
import dev.javarush.todolist.services.impl.TaskCommentServiceImpl;
import dev.javarush.todolist.services.impl.TaskServiceImpl;
import dev.javarush.todolist.services.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static dev.javarush.todolist.consts.WebConstants.*;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final String CONTEXT_INITIALIZED = "Context initialized";
    private final Logger logger = LogManager.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        PasswordHashing passwordHashing = new PasswordHashing();
        SessionProvider sessionProvider = new Provider();



        UserRepository userRepository = new UserRepository(sessionProvider.getSessionFactory());
        TaskRepository taskRepository = new TaskRepository(sessionProvider.getSessionFactory());
        TagRepository tagRepository = new TagRepository(sessionProvider.getSessionFactory());
        TaskCommentRepository taskCommentRepository = new TaskCommentRepository(sessionProvider.getSessionFactory());

        UserService userService = new UserServiceImpl(userRepository, passwordHashing);
        TaskService taskService = new TaskServiceImpl(taskRepository);
        TagService tagService = new TagServiceImpl(tagRepository);
        TaskCommentService taskCommentService = new TaskCommentServiceImpl(taskCommentRepository, taskService, userService);

        context.setAttribute(USER_SERVICE, userService);
        context.setAttribute(TASK_SERVICE, taskService);
        context.setAttribute(TAG_SERVICE, tagService);
        context.setAttribute(TASK_COMMENT_SERVICE, taskCommentService);
        logger.info(CONTEXT_INITIALIZED);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
