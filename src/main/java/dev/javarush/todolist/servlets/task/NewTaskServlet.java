package dev.javarush.todolist.servlets.task;

import dev.javarush.todolist.command.TaskCommand;
import dev.javarush.todolist.enums.TaskPriority;
import dev.javarush.todolist.enums.TaskStatus;
import dev.javarush.todolist.exceptions.UserNotFoundException;
import dev.javarush.todolist.services.TagService;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static dev.javarush.todolist.consts.WebConstants.*;

@WebServlet(name = "newTaskServlet", value = "/new-task")
public class NewTaskServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(NewTaskServlet.class);

    private TaskService taskService;
    private UserService userService;
    private TagService tagService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
        userService = (UserService) context.getAttribute(USER_SERVICE);
        tagService = (TagService) context.getAttribute(TAG_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addAttributes(req);

        //TEST common JSP
//        req.getRequestDispatcher("/task/new_task.jsp").forward(req, resp);


        req.getRequestDispatcher("/task/task_form.jsp").forward(req, resp);
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = (String) req.getSession().getAttribute("user");
        //String username = "malina"; //forced username
        if (ObjectUtils.anyNull(username)) {
            resp.sendRedirect("/javarush_todolist_war_exploded/login");
        }
        TaskCommand taskCommand = buildTaskCommand(req, username);
        taskService.save(taskCommand);
        String currentUser = (String) req.getSession().getAttribute(USER_ATTRIBUTE);

        logger.info("================================");
        logger.info(req.getSession().getCreationTime());
        logger.info(req.getSession().getId());
        logger.info(req);
        logger.info("currentUser = " + currentUser);
        logger.info("================================");

        resp.sendRedirect("/javarush_todolist_war_exploded/table-task");
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
                .tags(tagService.getTagsByIds(convertTagsIds(req.getParameterValues("taskTags")))) // todo падает когда раскомменчено. убрала тэги, пока я с ними не разберусь
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

    private void addAttributes(HttpServletRequest req) {
        req.setAttribute(STATUSES, taskService.getAllStatuses());
        req.setAttribute(PRIORITIES, taskService.getAllPriorities());
        req.setAttribute(TAGS, tagService.getAll());
    }
}
