package org.example.todo.controller;

import lombok.RequiredArgsConstructor;
import org.example.todo.dto.request.TaskRequestDto;
import org.example.todo.dto.response.TaskResponseDto;
import org.example.todo.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;


    @GetMapping("all")
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();

    }
    @PostMapping("task-create")
    public String createTask(@RequestBody TaskRequestDto requestDto){
        return  taskService.createTask(requestDto);
    }

    @PutMapping("/update/{id}")
    public  String updateTask(@PathVariable Long id ,@RequestBody TaskRequestDto requestDto){
        return taskService.updateTask(id,requestDto);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id ){
        return  taskService.deleteTask(id);
    }




}
