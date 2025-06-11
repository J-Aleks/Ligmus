package com.example.ligmus.data.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeDTO {
    private Integer gradeId;
    private Integer studentId;
    private Integer teacherId;
    @NotNull(message = "Ocena musi mieć wartość")
    @Min(value = 1,message = "Wartość oceny musi wynosić co najmniej 1")
    @Max(value = 5, message = "Wartość oceny może wynosić maksymalnie 5")
    private Integer grade;
    @NotNull(message = "Proszę wybrać przedmiot")
    private Integer subject;
    @NotNull(message = "Ocena musi mieć wagę")
    @Min(value = 1,message = "Waga oceny musi wynosić co najmniej 1")
    @Max(value = 5, message = "Waga oceny może wynosić maksymalnie 5")
    private Integer weight;
    private String description;
}
