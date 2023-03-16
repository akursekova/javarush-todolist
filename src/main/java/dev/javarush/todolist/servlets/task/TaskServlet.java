package dev.javarush.todolist.servlets.task;

import dev.javarush.todolist.command.TaskCommand;
import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.enums.TaskPriority;
import dev.javarush.todolist.enums.TaskStatus;
import dev.javarush.todolist.enums.WebMethodsType;
import dev.javarush.todolist.exceptions.UserNotFoundException;
import dev.javarush.todolist.services.TagService;
import dev.javarush.todolist.services.TaskCommentService;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;
import dev.javarush.todolist.servlets.tag.TagServlet;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.javarush.todolist.consts.WebConstants.*;
import static dev.javarush.todolist.consts.WebConstants.USER_SERVICE;

@WebServlet(name = "taskServlet", value = "/task")
public class TaskServlet extends HttpServlet {

    private final Logger logger = LogManager.getLogger(TaskServlet.class);
    private TaskService taskService;
    private TaskCommentService taskCommentService;

    private TagService tagService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        taskCommentService = (TaskCommentService) context.getAttribute(TASK_COMMENT_SERVICE);
        tagService = (TagService) context.getAttribute(TAG_SERVICE);
        userService = (UserService) context.getAttribute(USER_SERVICE);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String referer = req.getHeader(REFERER);
        TaskDTO task = null;
        Long id = null;

        addAttributes(req);

        if (referer.contains(NEW_TAG_SERVLET_VALUE)) {
            String idValue = String.valueOf(session.getAttribute("id"));
            id = Long.parseLong(idValue);
            task = taskService.getTaskById(id);
        } else {
            id = Long.valueOf(req.getParameter("id"));
            task = taskService.getTaskById(id);
        }

        req.setAttribute("task", task);
        session.setAttribute("task", task);

        if (req.getParameter("action") != null) {
            WebMethodsType method = WebMethodsType.valueOf(req.getParameter("action").toUpperCase());

            if (method.equals(WebMethodsType.DELETE)) {
                deleteAction(req, resp, id, method);
                return;
            }
            if (method.equals(WebMethodsType.EDIT)) {
                req.getRequestDispatcher("/task/task_form.jsp").forward(req, resp);
                return;
            }
        }
        if (session.getAttribute("action") != null) {
            req.getRequestDispatcher("/task/task_form.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/task/info_task.jsp").forward(req, resp);
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Long currentTaskId = Long.valueOf(req.getParameter("id"));
        TaskDTO task = taskService.getTaskById(currentTaskId);

        String username = (String) req.getSession().getAttribute(USER_ATTRIBUTE);

        logger.info("current task = " + task.toString());

        TaskCommand updatedTaskCommand = buildTaskCommand(req, username);
        taskService.updateTask(updatedTaskCommand, Long.valueOf(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/table-task");
    }

    private void deleteAction(HttpServletRequest req, HttpServletResponse resp, Long id, WebMethodsType method) throws ServletException, IOException {
        if (method == WebMethodsType.DELETE) {
            taskService.deleteTaskById(id);
            resp.sendRedirect(req.getContextPath() + "/table-task");
        }
    }

    private void addAttributes(HttpServletRequest req) {
        req.setAttribute(STATUSES, taskService.getAllStatuses());
        req.setAttribute(PRIORITIES, taskService.getAllPriorities());
        req.setAttribute(TAGS, tagService.getAll());
    }

    private TaskCommand buildTaskCommand(HttpServletRequest req, String username) throws UserNotFoundException {
        return TaskCommand.builder()
                .title(req.getParameter("taskName"))
                .description(req.getParameter("taskDescription"))
                .status(TaskStatus.getByStatus(req.getParameter("taskStatus")))
                .priority(TaskPriority.getByPriority(req.getParameter("taskPriority")))
                .userId(userService.getUserByUsername(username).getId())
                .hours(Integer.parseInt(req.getParameter("taskHours")))
                .text(req.getParameter("taskText"))
                .tags(tagService.getTagsByIds(convertTagsIds(req.getParameterValues("taskTags"))))
                .build();
    }

    private Set<Long> convertTagsIds(String[] tags) {
        if (tags != null) {
            return Arrays.stream(tags)
                    .map(Long::parseLong)
                    .collect(Collectors.toSet());
        }
        Set<Long> result = new HashSet<>();
        result.add(null);
        return result;
    }

}
