package testTodo;


import org.example.todo.dto.request.CategoryRequestDto;
import org.example.todo.dto.response.CategoryResponseDto;
import org.example.todo.dto.response.TaskResponseDto;
import org.example.todo.entity.Category;
import org.example.todo.entity.Task;
import org.example.todo.repository.CategoryRepository;
import org.example.todo.service.CategoryService;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    CategoryService categoryService;


    @Mock
    ModelMapper mapper;
    @Mock
    CategoryRepository categoryRepo;


    private CategoryResponseDto responseDto1;
    private CategoryResponseDto responseDto2;

    private Category category1;
    private Category category2;

    private CategoryRequestDto requestDto;

    private Task task1;
    private Task task2;

    private TaskResponseDto taskresponseDto1;
    private TaskResponseDto taskresponseDto2;


    @BeforeEach
    void init() {
        category1 = new Category(1L, "Inal", null);
        category2 = new Category(2L, "Enum", null);


        responseDto1 = new CategoryResponseDto(1L, "Inal", null);
        responseDto1 = new CategoryResponseDto(2L, "Enum", null);

        requestDto = new CategoryRequestDto("Inal");

        task1 = new Task(3L, "ilk", "ilk123", "ilkin", category1);
        task2 = new Task(4L, "birinci", "milli", "bizim", category1);

        category1.setTasks(Arrays.asList(task1, task2));
        category2.setTasks(Arrays.asList(task1, task2));

        taskresponseDto1 = new TaskResponseDto(1L, "ilk", "ilk123", "ilkin", 1L);
        taskresponseDto2 = new TaskResponseDto(2L, "birinci", "milli", "bizim", 2L);


        responseDto1.setTasks(List.of(taskresponseDto1, taskresponseDto2));


    }

    @Test
    public void getAllCategories_Testing() {
        List<Category> list = Arrays.asList(category1, category2);
        List<CategoryResponseDto> categoryResponseDtoList = Arrays.asList(responseDto2, responseDto1);
        when(categoryRepo.findAll()).thenReturn(list);
        var spy = Mockito.spy(categoryService);
        when(spy.listMapping(list, CategoryResponseDto.class)).thenReturn(categoryResponseDtoList);
        List<CategoryResponseDto> allCategories = spy.getAllCategories();


        Assertions.assertEquals(allCategories, categoryResponseDtoList);

    }

    @Test
    public void createCategory_Testing() {
        when(mapper.map(requestDto, Category.class)).thenReturn(category1);
        when(categoryRepo.save(category1)).thenReturn(category2);
        String category = categoryService.createCategory(requestDto);

        Assertions.assertNotNull(category);
        Assertions.assertEquals("category created", category);

    }

    @Test
    public void updateCategory_success() throws Exception {
        Long categId = 5L;
        when(categoryRepo.findById(categId)).thenReturn(Optional.of(category1));
        when(categoryRepo.save(category1)).thenReturn(category2);

        String updateCategory = categoryService.updateCategory(categId, requestDto);

        Assertions.assertNotNull(updateCategory);
        Assertions.assertEquals(" category updated", updateCategory);


    }


    @Test
    public void updateCategory_NotFound() throws Exception {
        Long id = 23456L;
        when(categoryRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.updateCategory(id, requestDto));

    }

    @Test
    public void deleteCategory_forId() {
        Long id = 2345L;
        when(categoryRepo.findById(id)).thenReturn(Optional.ofNullable(category1));

        String deletedCategory = categoryService.deleteCategory(id);
        Assertions.assertNotNull(deletedCategory);

        Assertions.assertEquals("category deleted", deletedCategory);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        Long id = 1L;

        when(categoryRepo.findById(id)).thenReturn(Optional.empty());

        String result = categoryService.deleteCategory(id);

        Assertions.assertEquals(" this  category not found ", result);
    }
}
