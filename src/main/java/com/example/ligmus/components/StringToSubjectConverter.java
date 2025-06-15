package com.example.ligmus.components;

import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.repositories.SubjectRepository;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSubjectConverter implements Converter<String, Subject> {

    private final SubjectRepository subjectRepository;

    public StringToSubjectConverter(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject convert(String source) {
        try {
            int id = Integer.parseInt(source);
            return subjectRepository.getSubjectById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono przedmiotu o id: " + id));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Niepoprawny format id przedmiotu: " + source, e);
        }
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}