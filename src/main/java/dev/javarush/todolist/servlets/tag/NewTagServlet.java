package dev.javarush.todolist.servlets.tag;

import dev.javarush.todolist.command.TagCommand;
import dev.javarush.todolist.services.TagService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static dev.javarush.todolist.consts.WebConstants.TAG_SERVICE;

@WebServlet(name = "newTagServlet", value = "/new-tag")
public class NewTagServlet extends HttpServlet {

    private TagService tagService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        tagService = (TagService) context.getAttribute(TAG_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/tag/new_tag.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        TagCommand tagCommand = buildTagCommand(req);
        tagService.save(tagCommand);
//        resp.sendRedirect("/hibernate_project_war_exploded/table-tag");
        //todo ВРЕМЕННОЕ РЕШЕНИЕ:
        // после объединение сервлетов поменять на единый,
        // а сейчас временно перевод на новую таску.
        // Для Эдит работать не будет
        resp.sendRedirect("/javarush_todolist_war/new-task");
    }

    private static TagCommand buildTagCommand(HttpServletRequest req) {
        return TagCommand.builder()
                .name(req.getParameter("tagName"))
                .color(req.getParameter("favcolor"))
                .build();
    }
}
