package dev.javarush.todolist.mapper;

import dev.javarush.todolist.command.UserCommand;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToEntity(UserCommand userCommand);

    UserDTO mapToDTO(User user);

    User mapToEntity(UserDTO userDTO);

}
