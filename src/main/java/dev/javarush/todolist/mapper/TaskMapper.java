package dev.javarush.todolist.mapper;


import dev.javarush.todolist.command.TaskCommand;
import dev.javarush.todolist.dto.TaskDTO;
import dev.javarush.todolist.model.Task;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TaskMapper.class);

    Task mapToEntity(TaskCommand taskCommand);

    TaskDTO mapToDTO(Task task);

    Task mapToEntity(TaskDTO taskDTO);
}
