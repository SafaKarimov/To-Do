package testTodo;


import org.example.todo.dto.request.TaskRequestDto;
import org.example.todo.dto.response.TaskResponseDto;
import org.example.todo.entity.Category;
import org.example.todo.entity.Task;
import org.example.todo.repository.CategoryRepository;
import org.example.todo.repository.TaskRepository;
import org.example.todo.service.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepo;
    @Mock
    CategoryRepository categoryRepo;
    @Mock
    ModelMapper mapper;


    private TaskResponseDto responseDto1;
    private TaskResponseDto responseDto2;

    private Task task1;
    private Task task2;

    private TaskRequestDto requestDto;
    private Category category;

    @BeforeEach
    void init() {
        category = new Category(1L, "yes1", null);

        responseDto1 = new TaskResponseDto(1L, "ilk", "ilk123", "ilkin", 1L);
        responseDto2 = new TaskResponseDto(2L, "birinci", "milli", "bizim", 2L);

        category.setTasks(Arrays.asList(task1, task2));

        task1 = new Task(1L, "ilk", "ilk123", "ilkin", category);
        task2 = new Task(2L, "birinci", "milli", "bizim", category);

        requestDto = new TaskRequestDto("sirf", "metn", "birce", 3L);
    }


    @Test
public void getAllTaskTest() {
    List<Task> tasks = Arrays.asList(task1, task2);
    List<TaskResponseDto> taskResponseDtos = Arrays.asList(responseDto1, responseDto2);

    var spy = Mockito.spy(taskService);
//        var responseDtos = List.of(responseDto1, responseDto2);
    when(taskRepo.findAll()).thenReturn(tasks);
    when(spy.listMapping(tasks,TaskResponseDto.class)).thenReturn(taskResponseDtos);

    List<TaskResponseDto> allTasks = spy.getAllTasks();

//        Assertions.assertNotNull(allTasks);
    Assertions.assertEquals(taskResponseDtos, allTasks);


}


    @Test
    public void createTask_success() throws Exception {
        when(mapper.map(requestDto, Task.class)).thenReturn(task1);
        when(categoryRepo.findById(requestDto.getCategoryId())).thenReturn(Optional.ofNullable(category));
        when(taskRepo.existsByTitle(requestDto.getTitle())).thenReturn(false);

        String task = taskService.createTask(requestDto);


        Assertions.assertNotNull(task);
        assertEquals("task created", task);

    }


    @Test
    public void CreateTask_categoryNotFound() throws Exception {
        when(mapper.map(requestDto, Task.class)).thenReturn(task1);
        when(categoryRepo.findById(requestDto.getCategoryId())).thenReturn(Optional.empty());

//        String categoryNF = taskService.createTask(requestDto);
//        Assertions.assertNotNull(categoryNF);
//        Assertions.assertEquals("category not found",categoryNF);
        assertThrows(RuntimeException.class, () -> taskService.createTask(requestDto));

    }

    @Test
    public void CreateTask_existingTitle() throws Exception {
        when(mapper.map(requestDto, Task.class)).thenReturn(task1);
        when(categoryRepo.findById(requestDto.getCategoryId())).thenReturn(Optional.of(category));
        when(taskRepo.existsByTitle(requestDto.getTitle())).thenReturn(true);

        assertEquals("This task title is already exists", taskService.createTask(requestDto));


    }


    @Test
    public void updateTask_success() throws Exception{
        Long taskId = 1L;
        when(taskRepo.findById(taskId)).thenReturn(Optional.of(task1));


        String updateTask = taskService.updateTask(taskId, requestDto);

        Assertions.assertNotNull(updateTask);
        Assertions.assertEquals("updated",updateTask);


    }
    @Test
    public void updateTask_TaskNotFound()throws  Exception{
        Long taskId = 2L;
        when(taskRepo.findById(taskId)).thenReturn(Optional.empty());
//        String notFound = taskService.updateTask(taskId, requestDto);


//        Assertions.assertNotNull(notFound);
//        Assertions.assertEquals(" task don't update ",notFound);
      assertThrows(RuntimeException.class, () -> taskService.updateTask(taskId, requestDto) );

    }

    @Test
    public void  deleteTask_success() throws Exception{
        Long taskId = 5L;

        when(taskRepo.findById(taskId)).thenReturn(Optional.of(task1));


        String deleted = taskService.deleteTask(taskId);


        Assertions.assertNotNull(deleted);
        Assertions.assertEquals(" task deleted",deleted);
    }


    @Test
    public void deleteTask_TaskNotFoundId() throws Exception{
        Long taskId = 6L;
        when(taskRepo.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()-> taskService.updateTask(taskId,requestDto));
    }


}
