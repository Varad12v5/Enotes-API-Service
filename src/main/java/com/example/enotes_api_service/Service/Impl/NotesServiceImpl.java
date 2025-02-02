package com.example.enotes_api_service.Service.Impl;

import com.example.enotes_api_service.Dto.NotesDto;
import com.example.enotes_api_service.Entity.Notes;
import com.example.enotes_api_service.Exception.ResourceNotFoundException;
import com.example.enotes_api_service.Repository.CategoryRepository;
import com.example.enotes_api_service.Repository.NotesRepository;
import com.example.enotes_api_service.Service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    private NotesRepository notesRepository;

    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    public NotesServiceImpl(NotesRepository notesRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.notesRepository = notesRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean saveNotes(NotesDto notesDto) throws Exception {
        //Category validation
        checkCategoryExist(notesDto.getCategory());

        Notes map = mapper.map(notesDto, Notes.class);
        Notes savedNote = notesRepository.save(map);

        if (!ObjectUtils.isEmpty(savedNote))
        {
             return true;
        }
        return false;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws Exception {
         categoryRepository.findById(category.getId()).orElseThrow(()-> new  ResourceNotFoundException("No category found with id="+category.getId()));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream()
                .map(note->mapper.map(note,NotesDto.class)).toList();
    }
}
