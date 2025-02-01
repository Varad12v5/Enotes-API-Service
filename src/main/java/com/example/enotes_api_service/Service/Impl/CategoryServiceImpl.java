package com.example.enotes_api_service.Service.Impl;

import com.example.enotes_api_service.Dto.CategoryDto;
import com.example.enotes_api_service.Dto.CategoryResponse;
import com.example.enotes_api_service.Entity.Category;
import com.example.enotes_api_service.Exception.ResourceNotFoundException;
import com.example.enotes_api_service.Repository.CategoryRepository;
import com.example.enotes_api_service.Service.CategoryService;
import com.example.enotes_api_service.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;
    private Validation validation;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper, Validation validation) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

        //Validation Checking
         validation.categoryValidation(categoryDto);

        Category category = mapper.map(categoryDto, Category.class);

        if (ObjectUtils.isEmpty(category.getId()))
        {
            category.setIsDeleted(false);
            category.setCreatedBy(1);
            category.setCreatedOn(new Date());
        }
        else
        {
            updateCategory(category);
        }


        Category save = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(save))
        {
            return false;
        }
        return true;


    }

    private void updateCategory(Category category) {
        Optional<Category> byId = categoryRepository.findById(category.getId());
        if (byId.isPresent())
        {
            Category existingCategory = byId.get();
            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setIsDeleted(existingCategory.getIsDeleted());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
        }
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> all = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> list = all.stream().map(category -> mapper.map(category, CategoryDto.class)).toList();
        return list;
    }

    @Override
    public List<CategoryResponse> getActiveCategories() {

        List<Category> all = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> list = all.stream().map(category -> mapper.map(category, CategoryResponse.class)).toList();
        return list;

    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception {
        Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("No category found with id="+id));
        if (!ObjectUtils.isEmpty(category))
        {
            return mapper.map(category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent())
        {
            Category category = byId.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
