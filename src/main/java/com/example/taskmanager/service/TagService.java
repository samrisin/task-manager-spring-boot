package com.example.taskmanager.service;

import com.example.taskmanager.dto.TagRequestDTO;
import com.example.taskmanager.dto.TagResponseDTO;

public interface TagService {
    TagResponseDTO createTag(TagRequestDTO request);
    TagResponseDTO assignTagToTask(Long taskId, Long tagId);
}