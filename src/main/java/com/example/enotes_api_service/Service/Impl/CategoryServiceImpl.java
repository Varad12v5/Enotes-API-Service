package com.example.enotes_api_service.Service.Impl;

import com.example.enotes_api_service.Dto.CategoryDto;
import com.example.enotes_api_service.Dto.CategoryResponse;
import com.example.enotes_api_service.Entity.Category;
import com.example.enotes_api_service.Repository.CategoryRepository;
import com.example.enotes_api_service.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto, Category.class);

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
    public List<CategoryDto> getCategories() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDto> list = all.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
        return list;
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {

        List<Category> all = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> list = all.stream().map(category -> mapper.map(category, CategoryResponse.class)).toList();
        return list;

    }
}
