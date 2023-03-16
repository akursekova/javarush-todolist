package dev.javarush.todolist.services;

import dev.javarush.todolist.command.TagCommand;
import dev.javarush.todolist.dto.TagDTO;
import dev.javarush.todolist.dto.TaskDTO;

import java.util.List;
import java.util.Set;

public interface TagService {
    void save(TagCommand tagCommand);

    TagDTO getTagById(Long id);

    void delete(String name);

    void deleteTagById(Long id);

    void update(String tagName, TagCommand tagCommand);

    TagDTO getByName(String name);

    List<TagDTO> getAll();

    Set<TagDTO> getTagsByIds(Set<Long> ids);
}
