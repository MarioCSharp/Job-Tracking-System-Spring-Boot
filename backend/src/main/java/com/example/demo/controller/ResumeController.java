package com.example.demo.controller;

import com.example.demo.dto.AttachResumeRequest;
import com.example.demo.dto.ResumeResponse;
import com.example.demo.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @PostMapping(
            value = "/attach",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> attach(
            @Valid @ModelAttribute AttachResumeRequest request,
            Authentication authentication) throws IOException {

        Long userId = Long.valueOf(authentication.getName());

        resumeService.attachResume(request, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(Authentication authentication){

        Long userId = Long.valueOf(authentication.getName());

        boolean response = resumeService.deleteResume(userId);

        if (!response){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<ResumeResponse> get(Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());

        ResumeResponse response = resumeService.getResume(userId);

        return ResponseEntity.ok(response);
    }
}
