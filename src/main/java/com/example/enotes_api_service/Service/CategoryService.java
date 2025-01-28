package com.example.enotes_api_service.Service;

import com.example.enotes_api_service.Dto.CategoryDto;
import com.example.enotes_api_service.Dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getCategories();

    List<CategoryResponse> getActiveCategories();

    CategoryDto getCategoryById(Integer id) throws Exception;

    Boolean deleteCategory(Integer id);
}
