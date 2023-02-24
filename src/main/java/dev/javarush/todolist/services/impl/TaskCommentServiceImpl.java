package dev.javarush.todolist.services.impl;

import dev.javarush.todolist.command.TaskCommentCommand;
import dev.javarush.todolist.dto.TaskCommentDTO;
import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.mapper.TaskCommentMapper;
import dev.javarush.todolist.mapper.TaskMapper;
import dev.javarush.todolist.mapper.UserMapper;
import dev.javarush.todolist.model.TaskComment;
import dev.javarush.todolist.repositories.TaskCommentRepository;
import dev.javarush.todolist.services.TaskCommentService;
import dev.javarush.todolist.services.TaskService;
import dev.javarush.todolist.services.UserService;

import java.util.List;

public class TaskCommentServiceImpl implements TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final TaskCommentMapper taskCommentMapper;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    public TaskCommentServiceImpl(TaskCommentRepository taskCommentRepository,
                                  TaskService taskService,
                                  UserService userService) {
        this.taskCommentRepository = taskCommentRepository;
        this.taskService = taskService;
        this.userService = userService;
        taskCommentMapper = TaskCommentMapper.INSTANCE;
        userMapper = UserMapper.INSTANCE;
        taskMapper = TaskMapper.INSTANCE;
    }

    @Override
    public void save(TaskCommentCommand taskCommentCommand) {
        TaskDTO taskToAddComment = taskService.getTaskById(taskCommentCommand.getTaskId());
        UserDTO userWhoAddsComment = userService.getUserById(taskCommentCommand.getUserId());
        TaskComment taskComment = taskCommentMapper.mapToEntity(taskCommentCommand);
        taskComment.setTask(taskMapper.mapToEntity(taskToAddComment));
        taskComment.setUser(userMapper.mapToEntity(userWhoAddsComment));
        taskCommentRepository.save(taskComment);
    }

    @Override
    public List<TaskCommentDTO> findCommentsByTaskId(Long id) {
        return taskCommentRepository.findByTaskId(id);
    }
}
