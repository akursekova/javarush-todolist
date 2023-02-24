package dev.javarush.todolist.servlets.tag;

import dev.javarush.todolist.dto.TagDTO;
import dev.javarush.todolist.services.TagService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static dev.javarush.todolist.consts.WebConstants.TAG_SERVICE;

@WebServlet(name = "tableTagServlet", value = "/table-tag")
public class TableTagServlet extends HttpServlet {

    private TagService tagService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        tagService = (TagService) context.getAttribute(TAG_SERVICE);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TagDTO> tags = tagService.getAll();
        req.setAttribute("tags", tags);
        req.getRequestDispatcher("/tag/table_tag.jsp").forward(req, resp);
    }
}
