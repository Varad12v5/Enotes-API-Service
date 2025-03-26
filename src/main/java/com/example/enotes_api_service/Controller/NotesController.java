package com.example.enotes_api_service.Controller;

import com.example.enotes_api_service.Dto.NotesDto;
import com.example.enotes_api_service.Service.NotesService;
import com.example.enotes_api_service.util.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController()
@RequestMapping("api/v1/notes")
public class NotesController {

    private NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception
    {
        Boolean b = notesService.saveNotes(notes,file);
        if (b)
        {
            return CommonUtils.createBuildResponseMessage("Notes Saved Successfully", HttpStatus.CREATED);
        }
        return CommonUtils.createErrorResponseMessage("Notes Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes()
    {
        List<NotesDto> allNotes = notesService.getAllNotes();
        if (CollectionUtils.isEmpty(allNotes))
        {
            return new ResponseEntity<>("No notes found", HttpStatus.NOT_FOUND);
        }
        return CommonUtils.createBuildResponse(allNotes, HttpStatus.OK);

    }
}
