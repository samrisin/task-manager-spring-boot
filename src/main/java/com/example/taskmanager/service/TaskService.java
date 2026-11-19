package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO getTaskById(Long id);

    TaskResponseDTO createTask(TaskRequestDTO request);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO request);

    boolean deleteTask(Long id);

    List<TaskResponseDTO> getTasksByStatus(String status);

    List<TaskResponseDTO> searchTasksByTitle(String keyword);
}