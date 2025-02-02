package com.example.enotes_api_service.Controller;

import com.example.enotes_api_service.Dto.CategoryDto;
import com.example.enotes_api_service.Dto.CategoryResponse;
import com.example.enotes_api_service.Entity.Category;
import com.example.enotes_api_service.Exception.ResourceNotFoundException;
import com.example.enotes_api_service.Service.CategoryService;
import com.example.enotes_api_service.util.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto)
    {
        Boolean b = categoryService.saveCategory(categoryDto);
        if (b)
        {
            return CommonUtils.createBuildResponse("saved successfully",HttpStatus.CREATED);
//            return new ResponseEntity<>("Saved", HttpStatus.CREATED);
        }
        else
        {
            return CommonUtils.createErrorResponse("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
//            return new ResponseEntity<>("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategories()
    {
        List<CategoryDto> categories = categoryService.getCategories();
        if (CollectionUtils.isEmpty(categories))
        {
            return new ResponseEntity<>("No categories found", HttpStatus.NOT_FOUND);
        }
        else
        {
            return CommonUtils.createBuildResponse(categories,HttpStatus.OK);
//            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategories()
    {
        List<CategoryResponse> categories = categoryService.getActiveCategories();
        if (CollectionUtils.isEmpty(categories))
        {
            return new ResponseEntity<>("No categories found", HttpStatus.NOT_FOUND);
        }
        else
        {
            return CommonUtils.createBuildResponse(categories,HttpStatus.OK);
//            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception
    {
        CategoryDto categoryDto=categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto))
        {
            return CommonUtils.createErrorResponseMessage("No category found with id="+id, HttpStatus.NOT_FOUND);
//            return new ResponseEntity<>("No category found with id="+id, HttpStatus.NOT_FOUND);
        }
        return CommonUtils.createBuildResponse(categoryDto,HttpStatus.OK);
//        return new ResponseEntity<>(categoryDto, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id)
    {
        Boolean deleted=categoryService.deleteCategory(id);
        if(deleted)
        {
            return CommonUtils.createBuildResponse("Category with id="+id+" deleted",HttpStatus.OK);
//            return new ResponseEntity<>("Category with id="+id+" deleted", HttpStatus.OK);
        }
        return CommonUtils.createErrorResponseMessage("Category with id="+id+" not deleted (Not Found)", HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("Category with id="+id+" not deleted (Not Found)", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
