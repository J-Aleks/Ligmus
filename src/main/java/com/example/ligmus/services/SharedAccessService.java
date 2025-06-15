package com.example.ligmus.services;


import com.example.ligmus.data.DTO.ShareLinkDTO;
import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.repositories.AccessSharedRepository;
import com.example.ligmus.data.shared.AccessSharedToken;
import com.example.ligmus.repositories.SubjectRepository;
import com.example.ligmus.services.CustomUserDetailsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SharedAccessService {

    private final CustomUserDetailsService customUserDetailsService;
    private final SubjectRepository subjectRepository;

    public SharedAccessService(CustomUserDetailsService customUserDetailsService, SubjectRepository subjectRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.subjectRepository = subjectRepository;
    }

    @Autowired
    private AccessSharedRepository accessSharedRepository;

    public String generateAccessLink(ShareLinkDTO linkDTO, int teacherId) {

        Optional<SubjectEntity> optSubjectEntity = this.subjectRepository.findById(linkDTO.getSubjectName());
        SubjectEntity subjectEntity;
        if (optSubjectEntity.isPresent()){
            subjectEntity = optSubjectEntity.get();
            AccessSharedToken token = new AccessSharedToken();
            token.setToken(UUID.randomUUID().toString());
            token.setSharedSubject(subjectEntity);
            token.setGrantedByTeacherId(teacherId);
            token.setGrantedToTeacherId(linkDTO.getGrantedToTeacherId());
            if(linkDTO.getExpiresAt() == null) {
                token.setExpiresAt(LocalDate.now().plusDays(2));
            }
            else
                token.setExpiresAt(linkDTO.getExpiresAt());

            accessSharedRepository.save(token);

            return "https://twojadomena.pl/shared-access/" + token.getToken();
        }
        return null;
    }


    public List<AccessSharedToken> getAccessSharedTokensToTeacher(int teacherId) {
        return this.accessSharedRepository.getAccessSharedtokensByTeacherId(teacherId);
    }

    public List<AccessSharedToken> getAvalaibleAccessSharedTokensToCurrentUser() {
        int currentUserId = customUserDetailsService.getCurrentUser().getId();
        return this.accessSharedRepository.getAvalaibleAccessSharedTokensToCurrentUser(currentUserId);
    }

    public List<AccessSharedToken> getAvalaibleAccessSharedTokensByCurrentUser() {
        int currentUserId = customUserDetailsService.getCurrentUser().getId();
        return this.accessSharedRepository.getAvalaibleAccessSharedTokensByCurrentUser(currentUserId);
    }

    public boolean checkAccessSharedToken(AccessSharedToken token, int id) {
        return this.accessSharedRepository.chekAccessSharedToken(token, id);
    }

    public AccessSharedToken getAccessToken(String token) {
        return this.accessSharedRepository.getAccessSharedToken(token);
    }

}
