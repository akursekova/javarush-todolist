package dev.javarush.todolist.dto;

import lombok.Data;

@Data
public class TagDTO {
    private Long id;
    private String name;
    private String color;
}
