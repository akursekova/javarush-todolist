package dev.javarush.todolist.servlets.task;

import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static dev.javarush.todolist.consts.WebConstants.*;

@WebServlet(name = "tableTaskServlet", value = "/table-task")
public class TableTaskServlet extends HttpServlet {

    private TaskService taskService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        userService = (UserService) context.getAttribute(USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUser = getUserNameFromSession(req);
        UserDTO user = userService.getUserByUsername("malina");//as I understood, this is like a hotfix. I force username
        List<TaskDTO> tasks = taskService.findTasksByUserId(user.getId());
        req.setAttribute("username", currentUser);
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/task/table_task.jsp").forward(req, resp);
    }

    private String getUserNameFromSession(HttpServletRequest req) {
        return (String) req.getSession().getAttribute(USER_ATTRIBUTE);
    }
}
