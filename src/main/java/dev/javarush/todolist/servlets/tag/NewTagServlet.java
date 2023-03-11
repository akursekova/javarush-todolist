package dev.javarush.todolist.servlets.tag;

import dev.javarush.todolist.command.TagCommand;
import dev.javarush.todolist.services.TagService;
import dev.javarush.todolist.services.TaskService;

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

import static dev.javarush.todolist.consts.WebConstants.TAG_SERVICE;
import static dev.javarush.todolist.consts.WebConstants.TASK_SERVICE;

@WebServlet(name = "newTagServlet", value = "/new-tag")
public class NewTagServlet extends HttpServlet {

    private TagService tagService;

    private TaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        tagService = (TagService) context.getAttribute(TAG_SERVICE);
        taskService = (TaskService) context.getAttribute(TASK_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.getRequestDispatcher("/tag/tag_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        HttpSession session = req.getSession();
        TagCommand tagCommand = buildTagCommand(req);
        tagService.save(tagCommand);


        req.setAttribute("id", session.getAttribute("id"));

        if (session.getAttribute("action").equals("new")) {
            resp.sendRedirect(req.getContextPath() + "/new-task");
        } else {
            req.setAttribute("action", session.getAttribute("action"));
            resp.sendRedirect(req.getContextPath() + "/task");
        }
    }

    private TagCommand buildTagCommand(HttpServletRequest req) {
        return TagCommand.builder()
                .name(req.getParameter("tagName"))
                .color(req.getParameter("favcolor"))
                .build();
    }

    private Set<Long> convertTasksIds(String[] tasks) {
        if (tasks != null) {
            return Arrays.stream(tasks)
                    .map(Long::parseLong)
                    .collect(Collectors.toSet());
        }
        Set<Long> result = new HashSet<>();
        result.add(null);
        return result;
    }
}
