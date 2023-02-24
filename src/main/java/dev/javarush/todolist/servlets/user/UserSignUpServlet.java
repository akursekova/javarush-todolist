package dev.javarush.todolist.servlets.user;

import dev.javarush.todolist.command.UserCommand;
import dev.javarush.todolist.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static dev.javarush.todolist.consts.WebConstants.USER_ATTRIBUTE;
import static dev.javarush.todolist.consts.WebConstants.USER_SERVICE;

@WebServlet(name = "userCreatingServlet", value = "/user-sign-up")
public class UserSignUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute(USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCommand userCommand = buildUserCommand(req);
        userService.createUser(userCommand);
        putUsernameToCurrentSession(req, userCommand);
        resp.sendRedirect("/hibernate_project_war_exploded/table-task");
    }

    private UserCommand buildUserCommand(HttpServletRequest req) {
        return UserCommand.builder()
                .email(req.getParameter("email"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .password(req.getParameter("password"))
                .username(req.getParameter("username"))
                .build();
    }

    private void putUsernameToCurrentSession(HttpServletRequest req, UserCommand userCommand) {
        HttpSession session = req.getSession();
        session.setAttribute(USER_ATTRIBUTE, userCommand.getUsername());
    }
}
