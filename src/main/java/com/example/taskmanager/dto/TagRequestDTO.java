
package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagRequestDTO(
    @NotBlank(message = "Tag name is required")
    @Size(max = 50, message = "Tag name cannot exceed 50 characters")
    String name
) {}
