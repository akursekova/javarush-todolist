package dev.javarush.todolist.middleware;

import dev.javarush.todolist.command.Command;
import dev.javarush.todolist.command.UserCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDataMiddleware extends Middleware {

    private final Logger logger = LogManager.getLogger(UserDataMiddleware.class);
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String NAME_REGEX = "[a-zA-Z]*";
    public static final String USERNAME_REGEX = "^[a-z0-9_-]{3,15}$";


    @Override
    public boolean check(Command command) {
       UserCommand userCommand = (UserCommand) command;
        if (userCommand.getEmail().matches(EMAIL_REGEX)
                && userCommand.getFirstName().matches(NAME_REGEX)
                && userCommand.getLastName().matches(NAME_REGEX)
                && userCommand.getUsername().matches(USERNAME_REGEX)) {
            return checkNext(command);
        }
        logger.error("User data is not valid");
        return false;
    }
}
