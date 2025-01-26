package com.example.enotes_api_service.Repository;

import com.example.enotes_api_service.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
