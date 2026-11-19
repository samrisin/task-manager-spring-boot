package com.example.taskmanager.service;
import com.example.taskmanager.dto.TagRequestDTO;
import com.example.taskmanager.dto.TagResponseDTO;
import com.example.taskmanager.exception.DuplicateTagException;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Tag;
import com.example.taskmanager.repository.TagRepository;
import com.example.taskmanager.repository.TaskRepository;

import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    public TagServiceImpl(TagRepository tagRepository,TaskRepository taskRepository) {
        this.tagRepository = tagRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public TagResponseDTO createTag(TagRequestDTO request) {

        String name = request.name().trim().toUpperCase();

        if (tagRepository.existsByName(name)) {
            throw new DuplicateTagException(name);
        }

        Tag tag = new Tag();
        tag.setName(name);

        Tag savedTag = tagRepository.save(tag);

        return new TagResponseDTO(
            savedTag.getId(),
            savedTag.getName()
        );
    }

    
    @Override
@Transactional
public TagResponseDTO assignTagToTask(Long taskId, Long tagId) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));

    Tag tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new RuntimeException("Tag not found: " + tagId));

    task.addTag(tag);

    return new TagResponseDTO(tag.getId(), tag.getName());
}
}
