package dev.javarush.todolist.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCommand implements Command {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}
