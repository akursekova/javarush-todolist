package dev.javarush.todolist.servlets.task;

import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.enums.WebMethodsType;
import dev.javarush.todolist.services.TaskCommentService;
import dev.javarush.todolist.services.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static dev.javarush.todolist.consts.WebConstants.TASK_COMMENT_SERVICE;
import static dev.javarush.todolist.consts.WebConstants.TASK_SERVICE;

@WebServlet(name = "taskInformationServlet", value = "/task-test")
public class TaskInformation extends HttpServlet {

    private final Logger logger = LogManager.getLogger(TaskInformation.class);

    private TaskService taskService;
    private TaskCommentService taskCommentService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        taskCommentService = (TaskCommentService) context.getAttribute(TASK_COMMENT_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("doGet started. id = " + req.getParameter("id"));

        Long id = Long.parseLong(req.getParameter("id"));
        TaskDTO task = taskService.getTaskById(id);
        if(req.getParameter("action") != null) {
            WebMethodsType method = WebMethodsType.valueOf(req.getParameter("action"));
            deleteAction(req, resp, id, method);
        } else {
            task.setComments(taskCommentService.findCommentsByTaskId(id));
            req.setAttribute("task", task);
        }
        req.getRequestDispatcher("/task/info_task.jsp").forward(req, resp);
    }

    private void deleteAction(HttpServletRequest req, HttpServletResponse resp, Long id, WebMethodsType method) throws ServletException, IOException {
        if (method == WebMethodsType.DELETE) {
            taskService.deleteTaskById(id);
            req.getRequestDispatcher("/task/table_task.jsp").forward(req, resp);
        }
    }



}
