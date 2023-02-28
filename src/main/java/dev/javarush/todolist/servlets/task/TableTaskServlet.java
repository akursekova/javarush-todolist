package dev.javarush.todolist.servlets.task;

import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;
import dev.javarush.todolist.services.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static dev.javarush.todolist.consts.WebConstants.*;

@WebServlet(name = "tableTaskServlet", value = "/table-task")
public class TableTaskServlet extends HttpServlet {

    private final Logger logger = LogManager.getLogger(TableTaskServlet.class);

    private TaskService taskService;
    private UserService userService;

    private HttpSession session;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        userService = (UserService) context.getAttribute(USER_SERVICE);
        session = null;
    }


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TEST
        session = req.getSession(true);
        String currentUser = (String) session.getAttribute(USER_ATTRIBUTE);
        //String currentUser = "user01";
        req.isRequestedSessionIdValid();
        //TEST



        //String currentUser = getUserNameFromSession(req);
        logger.info("================================");
        logger.info(req.getSession().getCreationTime());
        logger.info(req.getSession().getId());
        logger.info(req);
        logger.info("currentUser = " + currentUser);
        logger.info("================================");

        UserDTO user = userService.getUserByUsername(currentUser);//as I understood, this is like a hotfix. I force username
        logger.info("userDTO = " + user);

        List<TaskDTO> tasks = taskService.findTasksByUserId(user.getId());


        req.setAttribute("username", currentUser);
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/task/table_task.jsp").forward(req, resp);
    }

    private String getUserNameFromSession(HttpServletRequest req) {
        logger.info("================================");
        logger.info("getUserNameFromSession method");
        logger.info(req.getSession());
        logger.info(req);
        logger.info("currentUser = " + (String) req.getSession().getAttribute(USER_ATTRIBUTE));
        logger.info("================================");
        return (String) req.getSession().getAttribute(USER_ATTRIBUTE);
    }
}
