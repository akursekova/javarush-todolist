package dev.javarush.todolist.services;

import dev.javarush.todolist.command.UserCommand;
import dev.javarush.todolist.dto.UserDTO;

public interface UserService {

    void createUser(UserCommand userCommand);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);
}
