package dev.javarush.todolist.services;

import dev.javarush.todolist.command.TaskCommand;
import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.enums.TaskPriority;
import dev.javarush.todolist.enums.TaskStatus;

import java.util.List;

public interface TaskService {
    void save(TaskCommand taskCommand);

    TaskDTO getTaskById(Long id);

    List<TaskStatus> getAllStatuses();

    List<TaskPriority> getAllPriorities();

    List<TaskDTO> findTasksByUserId(Long id);

    void deleteTaskById(Long id);

    void updateTask(TaskCommand taskCommand, Long id);

    void unbindTagFromTasks(Long id);
}
