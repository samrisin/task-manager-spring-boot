package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDTO;
import com.example.taskmanager.dto.TaskResponseDTO;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Entity -> Response DTO
    private TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.getStatus()
        );
    }

    // Request DTO -> Entity
    private Task toEntity(TaskRequestDTO request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());
        return task;
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return toResponseDTO(task);
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO request) {
        Task task = toEntity(request);

        Task savedTask = taskRepository.save(task);

        return toResponseDTO(savedTask);
    }

  
    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO request) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.getTitle());
        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);

        return toResponseDTO(updatedTask);
    }

    @Override
    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }

        taskRepository.deleteById(id);
        return true;
    }

    @Override
    public List<TaskResponseDTO> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<TaskResponseDTO> searchTasksByTitle(String keyword) {
        return taskRepository.findByTitleContaining(keyword)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}