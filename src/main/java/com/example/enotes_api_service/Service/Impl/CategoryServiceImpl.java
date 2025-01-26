package com.example.enotes_api_service.Service.Impl;

import com.example.enotes_api_service.Entity.Category;
import com.example.enotes_api_service.Repository.CategoryRepository;
import com.example.enotes_api_service.Service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category save = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(save))
        {
            return false;
        }
        return true;
    }

    @Override
    public List<Category> getCategories() {
        List<Category> all = categoryRepository.findAll();
        return all;
    }
}
