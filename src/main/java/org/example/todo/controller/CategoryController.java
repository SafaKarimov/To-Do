package org.example.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.todo.dto.request.CategoryRequestDto;
import org.example.todo.dto.response.CategoryResponseDto;
import org.example.todo.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("all")
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();

    }

    @PostMapping("/category-create")
    public String createCategory(@RequestBody CategoryRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }


    @PutMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDto requestDto) {
      return  categoryService.updateCategory(id,requestDto);
    }
    @DeleteMapping("/delete/{id}")
    public  String deleteCategory(@PathVariable Long id ){
        return categoryService.deleteCategory(id);
    }


}
