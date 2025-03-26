package com.example.enotes_api_service.Repository;

import com.example.enotes_api_service.Entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
