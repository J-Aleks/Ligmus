package com.example.ligmus.data.shared;

import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.subjects.Subject;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AccessSharedToken {

    private int id;
    private String token;

    private SubjectEntity sharedSubject;

    private int grantedByTeacherId;

    private int grantedToTeacherId;

    private LocalDate expiresAt;
}


