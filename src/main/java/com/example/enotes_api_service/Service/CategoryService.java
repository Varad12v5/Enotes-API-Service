package com.example.enotes_api_service.Service;

import com.example.enotes_api_service.Entity.Category;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(Category category);

    public List<Category> getCategories();
}
