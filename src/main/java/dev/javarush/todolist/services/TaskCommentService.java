package dev.javarush.todolist.services;

import dev.javarush.todolist.command.TaskCommentCommand;
import dev.javarush.todolist.dto.TaskCommentDTO;

import java.util.List;

public interface TaskCommentService {

    void save(TaskCommentCommand taskCommentCommand);

    List<TaskCommentDTO> findCommentsByTaskId(Long id);
}
