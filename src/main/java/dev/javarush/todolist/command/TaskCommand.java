package dev.javarush.todolist.command;

import dev.javarush.todolist.dto.TagDTO;
import dev.javarush.todolist.enums.TaskPriority;
import dev.javarush.todolist.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCommand implements Command {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Integer hours;
    private String text;
    private Set<TagDTO> tags;
    private Long userId;
}
