package dev.javarush.todolist.command;

import dev.javarush.todolist.dto.TagDTO;
import dev.javarush.todolist.dto.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagCommand implements Command {
    private String name;
    private String color;
    private Set<TaskDTO> tasks;
}
