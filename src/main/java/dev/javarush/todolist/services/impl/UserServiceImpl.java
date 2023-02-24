package dev.javarush.todolist.services.impl;

import dev.javarush.todolist.command.UserCommand;
import dev.javarush.todolist.components.PasswordHashing;
import dev.javarush.todolist.dto.UserDTO;
import dev.javarush.todolist.exceptions.CreatingEntityException;
import dev.javarush.todolist.exceptions.PasswordHashingException;
import dev.javarush.todolist.mapper.UserMapper;
import dev.javarush.todolist.middleware.Middleware;
import dev.javarush.todolist.middleware.UserDataMiddleware;
import dev.javarush.todolist.middleware.UserPasswordMiddleware;
import dev.javarush.todolist.model.User;
import dev.javarush.todolist.repositories.UserRepository;
import dev.javarush.todolist.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final String USER_WAS_NOT_CREATED = "User was not created. ";
    private static final String VALIDATION_FAILED = "Validation failed";
    private static final String USER_WAS_CREATED_SUCCESSFULLY = "User was created successfully";
    private final UserRepository userRepository;
    private final PasswordHashing passwordHashing;
    private final UserMapper userMapper;
    private final Middleware middleware;

    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, PasswordHashing passwordHashing) {
        this.userRepository = userRepository;
        this.passwordHashing = passwordHashing;
        this.userMapper = UserMapper.INSTANCE;
        this.middleware = Middleware.link(
                new UserPasswordMiddleware(),
                new UserDataMiddleware());
    }

    @Override
    public void createUser(UserCommand userCommand) {
        try {
            User user = userMapper.mapToEntity(userCommand);
            if (middleware.check(userCommand)) {
                saveUser(userCommand, user);
                logger.info(USER_WAS_CREATED_SUCCESSFULLY);
            } else {
                throw new CreatingEntityException(USER_WAS_NOT_CREATED + VALIDATION_FAILED, User.class);
            }
        } catch (Exception e) {
            throw new CreatingEntityException(USER_WAS_NOT_CREATED, User.class);
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        logger.info("User was found successfully");
        return userMapper.mapToDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        logger.info("User was found successfully");
        return userMapper.mapToDTO(user);
    }

    private void saveUser(UserCommand userCommand, User user) throws PasswordHashingException {
        user.setPassword(passwordHashing.hashPassword(userCommand.getPassword()));
        userRepository.save(user);
    }
}
