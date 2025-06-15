package com.example.ligmus.data.DTO;

import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.validation.groups.OnCreate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShareLinkDTO {

    private Subject sharedSubject;
@NotNull(groups = OnCreate.class, message = "Przedmiot wymagany")
    private Integer subjectName;
@NotNull(groups = OnCreate.class, message = "Nauczyciel wymagany")
    private Integer grantedToTeacherId;
@Future(groups = OnCreate.class)
    private LocalDate expiresAt;
}
