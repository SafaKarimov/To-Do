package org.example.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.todo.entity.Category;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequestDto {
    private String title;
    private String description;
    private String status;
    private Long categoryId;
}