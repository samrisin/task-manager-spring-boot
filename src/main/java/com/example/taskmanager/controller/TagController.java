
package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TagRequestDTO;
import com.example.taskmanager.dto.TagResponseDTO;
import com.example.taskmanager.service.TagService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponseDTO createTag(
            @Valid @RequestBody TagRequestDTO request) {

        return tagService.createTag(request);
    }

    @PostMapping("/tasks/{taskId}/tags/{tagId}")
public TagResponseDTO assignTag(
        @PathVariable Long taskId,
        @PathVariable Long tagId) {

    return tagService.assignTagToTask(taskId, tagId);
}
}
