package org.example.todo.service;

import lombok.AllArgsConstructor;
import org.example.todo.dto.request.CategoryRequestDto;
import org.example.todo.dto.response.CategoryResponseDto;
import org.example.todo.entity.Category;
import org.example.todo.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepo;


    public List<CategoryResponseDto> getAllCategories() {
        List<Category> all = categoryRepo.findAll();
        return listMapping(all, CategoryResponseDto.class);

    }


    public String createCategory(CategoryRequestDto requestDto) {
        Category map = mapper.map(requestDto, Category.class);
        Category save = categoryRepo.save(map);
        return "category created";
    }


    public String updateCategory(Long id, CategoryRequestDto requestDto) {
        Category found = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("category don;t update "));
        mapper.map(requestDto, found);
        Category save = categoryRepo.save(found);
        return " category updated";

    }


    public String deleteCategory(Long id) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category == null) {
            return " this  category not found ";
        }
        categoryRepo.delete(category);
        return "category deleted";

    }


    public <S, D> List<D> listMapping(List<S> source, Class<D> destination) {
        return source.stream().map(s -> mapper.map(s, destination)).toList();
    }

}
