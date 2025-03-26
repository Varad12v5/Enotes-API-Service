package com.example.enotes_api_service.Service.Impl;

import com.example.enotes_api_service.Dto.NotesDto;
import com.example.enotes_api_service.Entity.FileDetails;
import com.example.enotes_api_service.Entity.Notes;
import com.example.enotes_api_service.Exception.ResourceNotFoundException;
import com.example.enotes_api_service.Repository.CategoryRepository;
import com.example.enotes_api_service.Repository.FileRepository;
import com.example.enotes_api_service.Repository.NotesRepository;
import com.example.enotes_api_service.Service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NotesServiceImpl implements NotesService {

    private NotesRepository notesRepository;

    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    private FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    public NotesServiceImpl(NotesRepository notesRepository, ModelMapper mapper, CategoryRepository categoryRepository, FileRepository fileRepository) {
        this.mapper = mapper;
        this.notesRepository = notesRepository;
        this.categoryRepository = categoryRepository;
        this.fileRepository=fileRepository;
    }

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob= new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        //Category validation
        checkCategoryExist(notesDto.getCategory());

        Notes map = mapper.map(notesDto, Notes.class);

        FileDetails fileDetails=saveFileDetails(file);

        if (!ObjectUtils.isEmpty(fileDetails))
        {
            map.setFileDetails(fileDetails);
        }
        else
        {
            map.setFileDetails(null);
        }

        Notes savedNote = notesRepository.save(map);

        if (!ObjectUtils.isEmpty(savedNote))
        {
             return true;
        }
        return false;
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file) &&!file.isEmpty())
        {
            String originalFileName=file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);
            List<String> allowedExtensions=Arrays.asList(".pdf",".jpg",".png",".jpeg",".xlsx");
            if (!allowedExtensions.contains(extension))
            {
                throw new IllegalArgumentException("Invalid File extension!!! , Upload file with extension .pdf, .jpg, .png, .jpeg, .xlsx");
            }

            String rndString = UUID.randomUUID().toString();
            String uploadFileName = rndString + "." + extension;

            File saveFile=new File(uploadPath);
            if (!saveFile.exists())
            {
                saveFile.mkdir();
            }
            String storePath=uploadPath.concat(uploadFileName);

            //upload path
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (upload!=0)
            {
                FileDetails fileDetails=new FileDetails();

                fileDetails.setOriginalFileName(originalFileName);
                fileDetails.setDisplayFileName(getDisplayName(originalFileName));
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(storePath);
                FileDetails save = fileRepository.save(fileDetails);
                return save;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);

        if (fileName.length()>8)
        {
            fileName=fileName.substring(0,7);
        }
        fileName += "." + extension;
        return fileName;
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
