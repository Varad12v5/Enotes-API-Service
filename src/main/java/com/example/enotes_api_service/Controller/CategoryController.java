package com.example.enotes_api_service.Controller;

import com.example.enotes_api_service.Entity.Category;
import com.example.enotes_api_service.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category)
    {
        Boolean b = categoryService.saveCategory(category);
        if (b)
        {
            return new ResponseEntity<>("Saved", HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategories()
    {
        List<Category> categories = categoryService.getCategories();
        if (CollectionUtils.isEmpty(categories))
        {
            return new ResponseEntity<>("No categories found", HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }
}
