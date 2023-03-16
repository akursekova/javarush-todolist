package dev.javarush.todolist.servlets.user;

import dev.javarush.todolist.command.UserCommand;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.exceptions.UserNotFoundException;
import dev.javarush.todolist.services.UserService;
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

import static dev.javarush.todolist.consts.WebConstants.USER_ATTRIBUTE;
import static dev.javarush.todolist.consts.WebConstants.USER_SERVICE;

@WebServlet(name = "userLogInServlet", value = "/user-sign-in")
public class UserSignIpServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserSignIpServlet.class);

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute(USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user/sign_in.jsp").forward(req, resp);
    }



    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCommand userCommand = buildUserCommand(req);

        String userName = req.getParameter("username");

        UserDTO user = userService.getUserByUsername(userName);

        logger.info("================================");
        logger.info(req.getSession().getCreationTime());
        logger.info(req.getSession().getId());
        logger.info(req);
        logger.info("current user = " + user.toString());
        logger.info("================================");

        putUsernameToCurrentSession(req, userCommand);
        resp.sendRedirect(req.getContextPath() + "/table-task");
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
        //req.getSession().setAttribute(USER_ATTRIBUTE, userCommand.getUsername());
    }
}
