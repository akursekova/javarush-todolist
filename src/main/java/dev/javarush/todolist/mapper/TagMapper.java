package dev.javarush.todolist.mapper;

import dev.javarush.todolist.command.TagCommand;
import dev.javarush.todolist.dto.TagDTO;
import dev.javarush.todolist.model.Tag;
import org.mapstruct.Mapper;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TagMapper.class);

    TagDTO mapToDTO(Tag tag);

    Tag mapToEntity(TagCommand tagCommand);
}
