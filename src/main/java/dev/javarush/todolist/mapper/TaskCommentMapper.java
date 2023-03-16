package dev.javarush.todolist.mapper;

import dev.javarush.todolist.command.TaskCommentCommand;
import dev.javarush.todolist.dto.TaskCommentDTO;
import dev.javarush.todolist.model.TaskComment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskCommentMapper {

    TaskCommentMapper INSTANCE = Mappers.getMapper(TaskCommentMapper.class);

    TaskComment mapToEntity(TaskCommentCommand taskCommentCommand);

    TaskCommentDTO mapToDTO(TaskComment taskComment);

}
