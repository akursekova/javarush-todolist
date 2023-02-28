package dev.javarush.todolist.services;

import dev.javarush.todolist.command.UserCommand;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.exceptions.UserNotFoundException;

public interface UserService {

    void createUser(UserCommand userCommand);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username) throws UserNotFoundException;
}
