package com.example.enotes_api_service.Repository;

import com.example.enotes_api_service.Entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {
}
