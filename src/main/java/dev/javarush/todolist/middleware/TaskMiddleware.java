package dev.javarush.todolist.middleware;

import dev.javarush.todolist.command.Command;
import dev.javarush.todolist.command.TaskCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskMiddleware extends Middleware {

    private final Logger logger = LogManager.getLogger(TaskMiddleware.class);

    public static final String NAME_REGEX = "[a-zA-Z0-9]*";
    public static final String DESCRIPTION_REGEX = "[a-zA-Z0-9\\s]*";

    public static final String NAME_ERROR_MESSAGE = "Task name is not valid";


    @Override
    public boolean check(Command command) {
        TaskCommand taskCommand = (TaskCommand) command;
        if (taskCommand.getTitle().matches(NAME_REGEX)
                && taskCommand.getDescription().matches(DESCRIPTION_REGEX)) {
            return checkNext(command);
        }
        logger.error(NAME_ERROR_MESSAGE);
        return false;
    }

}
