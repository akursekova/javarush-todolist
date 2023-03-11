package dev.javarush.todolist.servlets.tag;

import dev.javarush.todolist.command.TagCommand;
import dev.javarush.todolist.dto.TagDTO;
import dev.javarush.todolist.enums.WebMethodsType;
import dev.javarush.todolist.services.TagService;
import dev.javarush.todolist.services.TaskCommentService;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;
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

import static dev.javarush.todolist.consts.WebConstants.*;

@WebServlet(name = "tagServlet", value = "/tag")
public class TagServlet extends HttpServlet {

    private final Logger logger = LogManager.getLogger(TagServlet.class);

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
        logger.info("doGet started. id = " + req.getParameter("id"));


        Long id = Long.parseLong(req.getParameter("id"));
        //TaskDTO task = taskService.getTaskById(id);
        TagDTO tag = tagService.getTagById(id);


        req.setAttribute("tag", tag);

        logger.info("Action = " + req.getParameter("action"));

        WebMethodsType method = WebMethodsType.valueOf(req.getParameter("action").toUpperCase());

        if (method.equals(WebMethodsType.DELETE)) {
            deleteAction(req, resp, id, method);
        }
        if (method.equals(WebMethodsType.EDIT)) {
            req.getRequestDispatcher("/tag/tag_form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long currentTagId = Long.valueOf(req.getParameter("id"));
        TagDTO tag = tagService.getTagById(currentTagId);

        logger.info("Current tag = " + tag.toString());




        TagCommand updatedTagCommand = buildTagCommand(req);
        tagService.update(tag.getName(), updatedTagCommand);
        //todo есть ли смысл передавать айди в updateTask,
        // если я могу засетить айди в таскКомманд
        // и потом замапить эту сущность на таску
        resp.sendRedirect(req.getContextPath() + "/table-tag");
    }

    private void deleteAction(HttpServletRequest req, HttpServletResponse resp, Long id, WebMethodsType method) throws ServletException, IOException {
            taskService.unbindTagFromTasks(id);
            tagService.deleteTagById(id);
            resp.sendRedirect(req.getContextPath() + "/table-tag");
    }

    private TagCommand buildTagCommand(HttpServletRequest req) {
        return TagCommand.builder()
                .name(req.getParameter("tagName"))
                .color(req.getParameter("favcolor"))
                .build();
    }

}
