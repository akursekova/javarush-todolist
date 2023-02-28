package dev.javarush.todolist.services.impl;

import dev.javarush.todolist.command.TaskCommand;
import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.enums.TaskPriority;
import dev.javarush.todolist.enums.TaskStatus;
import dev.javarush.todolist.exceptions.CreatingEntityException;
import dev.javarush.todolist.mapper.TaskMapper;
import dev.javarush.todolist.middleware.Middleware;
import dev.javarush.todolist.middleware.TaskMiddleware;
import dev.javarush.todolist.model.Tag;
import dev.javarush.todolist.model.Task;
import dev.javarush.todolist.repositories.TaskRepository;
import dev.javarush.todolist.services.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private static final String TASK_SAVED = "Task saved";
    private static final String ERROR_WHILE_SAVING_TASK = "Error while saving task";

    private final TaskRepository taskRepository;
    private final Middleware middleware;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = TaskMapper.INSTANCE;
        this.middleware = Middleware.link(
                new TaskMiddleware());

    }

    @Override
    public void save(TaskCommand taskCommand) throws CreatingEntityException {
        try {
            //if (middleware.check(taskCommand)) {
                Task task = taskMapper.mapToEntity(taskCommand);
                //addTagsToTask(taskCommand, task);
                taskRepository.save(task);
                logger.info(TASK_SAVED);
            //}
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_TASK, e);
            throw new CreatingEntityException(ERROR_WHILE_SAVING_TASK, Task.class);
        }
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskMapper.mapToDTO(taskRepository.findById(id));
    }

    @Override
    public List<TaskStatus> getAllStatuses() {
        return TaskStatus.getAll();
    }

    @Override
    public List<TaskPriority> getAllPriorities() {
        return TaskPriority.getAll();
    }

    @Override
    public List<TaskDTO> findTasksByUserId(Long id) {
        return taskRepository.findAllByUserId(id)
                .stream()
                .map(taskMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteTaskById(id);
    }

    @Override
    public void updateTask(TaskCommand taskCommand, Long id) {
        try {
            if (middleware.check(taskCommand)) {
                Task task = taskMapper.mapToEntity(taskCommand);//todo id перетирается, поэтому я его сечу отдельно
                task.setCreatedAt(taskRepository.findById(id).getCreatedAt());
                task.setId(id);
                logger.info("MY TASK = " + task.getId());

                taskRepository.update(task);
                //logger.info(TASK_UPDATED);
            }
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_TASK, e);
            throw new CreatingEntityException(ERROR_WHILE_SAVING_TASK, Task.class);
        }
    }

    @Override
    public void unrelateTagFromTasks(Long id) {
        List<Task> tasks = taskRepository.findTasksByTagId(id);
        for (int i = 0; i < tasks.size(); i++) {
            logger.info("findTasksByTagId method: taskId = " + tasks.get(i).getId());
        }

    }

    private void addTagsToTask(TaskCommand taskCommand, Task task) {
        if (taskCommand.getTags() != null) {
            Set<Tag> tags = taskCommand.getTags()
                    .stream()
                    .map(tag -> Tag.builder()
                            .name(tag.getName())
                            .color(tag.getColor())
                            .build())
                    .collect(Collectors.toSet());
            task.setTags(tags);
        }
    }
}
