package org.example.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.todo.dto.request.TaskRequestDto;
import org.example.todo.dto.response.TaskResponseDto;
import org.example.todo.entity.Category;
import org.example.todo.entity.Task;
import org.example.todo.repository.CategoryRepository;
import org.example.todo.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;
    private final CategoryRepository categoryRepo;
    private final ModelMapper mapper;

    public List<TaskResponseDto> getAllTasks() {
        List<Task> all = taskRepo.findAll();
        return listMapping(all, TaskResponseDto.class);

    }

    public String createTask(TaskRequestDto taskDto) {
        Task map = mapper.map(taskDto, Task.class);
        Category category = categoryRepo.findById(taskDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found by ID - "
                        + taskDto.getCategoryId()));

        boolean exists = taskRepo.existsByTitle(taskDto.getTitle());

        if (exists) {
            return "This task title is already exists";
        }

        map.setCategory(category);
        taskRepo.save(map);
        return "task created";
    }


    public String updateTask(Long id, TaskRequestDto taskDto) {
        Task byId = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(" task don't update "));
        mapper.map(taskDto, byId);
        taskRepo.save(byId);
        return "updated";
    }


    public String deleteTask(Long id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found by ID - " + id));

        taskRepo.delete(task);
        return " task deleted";
    }

    public <S, D> List<D> listMapping(List<S> source, Class<D> destination) {
        return source.stream().map(s -> mapper.map(s, destination)).toList();
    }
}


