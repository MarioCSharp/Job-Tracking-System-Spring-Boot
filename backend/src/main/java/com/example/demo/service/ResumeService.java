package com.example.demo.service;

import com.example.demo.dto.AttachResumeRequest;
import com.example.demo.dto.ResumeResponse;
import com.example.demo.exception.ResumeNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Resume;
import com.example.demo.model.User;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository){
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void attachResume(AttachResumeRequest request, Long userId) throws IOException {

        resumeRepository.findByUserId(userId)
                .ifPresent(resumeRepository::delete);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFile(request.getFile().getBytes());
        resume.setFileName(request.getFile().getOriginalFilename());
        resume.setContentType(request.getFile().getContentType());

        resumeRepository.save(resume);
    }

    @Transactional
    public boolean deleteResume(Long userId) {
        if (!resumeRepository.existsByUserId(userId)) {
            return false;
        }

        resumeRepository.deleteByUserId(userId);
        return true;
    }

    @Transactional
    public ResumeResponse getResume(Long userId){
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResumeNotFoundException("Resume not found"));

        return new ResumeResponse(
                resume.getId(),
                resume.getFileName(),
                resume.getContentType(),
                resume.getFile()
        );
    }
}
