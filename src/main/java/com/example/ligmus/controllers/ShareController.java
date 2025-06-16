package com.example.ligmus.controllers;


import com.example.ligmus.data.DTO.ShareLinkDTO;
import com.example.ligmus.data.DTO.StudentsDTO;
import com.example.ligmus.data.Entities.SubjectEntity;
import com.example.ligmus.data.subjects.Subject;
import com.example.ligmus.data.users.User;
import com.example.ligmus.exception.InvalidTokenException;
import com.example.ligmus.security.auth.CustomUserDetails;
import com.example.ligmus.services.LigmusService;
import com.example.ligmus.services.SharedAccessService;
import com.example.ligmus.data.shared.AccessSharedToken;
import com.example.ligmus.validation.groups.OnCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/share")
public class ShareController {

    @Autowired
    SharedAccessService sharedAccessService;

    @Autowired
    LigmusService ligmusService;

    @GetMapping("/")
    public String showShareLinks(Model model) {
        List<AccessSharedToken> incomingTokens = this.sharedAccessService.getAvalaibleAccessSharedTokensToCurrentUser();
        List<AccessSharedToken> outgoingTokens = this.sharedAccessService.getAvalaibleAccessSharedTokensByCurrentUser();
        Map<Integer, String> TeachersDescriptions = this.ligmusService.getTeachersNamesForId();

        model.addAttribute("teachersMap", TeachersDescriptions);
        model.addAttribute("incomingTokens", incomingTokens);
        model.addAttribute("outgoingTokens", outgoingTokens);
        return "share";
    }


    @GetMapping("/{tokenValue}")
    public String getShare(Model model, @PathVariable String tokenValue,
                           @AuthenticationPrincipal CustomUserDetails user) {
        int userId = user.getId();
        AccessSharedToken token = this.sharedAccessService.getAccessToken(tokenValue);
        if(!this.sharedAccessService.checkAccessSharedToken(token, userId)) {
            throw new InvalidTokenException("Invalid token: " + token);
        }
        StudentsDTO studentsDTO = this.ligmusService.CreateDTO(token.getSharedSubject().getId());

        model.addAttribute("studentsDTO", studentsDTO);
        return "allStudentsSubjectGrades";


    }
    @GetMapping("/generate")
    public String ShowGenerateShareLinkForm(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        int userId = user.getId();
        System.out.println("userId = " + userId);
        List<User> otherTeachers = this.ligmusService.getOtherTeachers(userId);
        List<Subject> assignedSubjects = this.ligmusService.getTeacherSubjects(userId);
        model.addAttribute("otherTeachers", otherTeachers);
        model.addAttribute("subjects", assignedSubjects);
        model.addAttribute("shareForm", new ShareLinkDTO());
        return "generateShareLinks";
    }

    @PostMapping("/generate")
    public String   GenerateShareLink(@ModelAttribute("shareForm")  @Validated(OnCreate.class) ShareLinkDTO form,
                                      BindingResult result,
                                      @AuthenticationPrincipal CustomUserDetails user,
                                      Model model) {

        if(result.hasErrors()){
            System.out.println("errors" + result.getAllErrors());
            int userId = user.getId();
            model.addAttribute("otherTeachers", this.ligmusService.getOtherTeachers(userId));
            model.addAttribute("subjects",this.ligmusService.getTeacherSubjects(userId));
            return "generateShareLinks";
        }

        int userId = user.getId();
        this.sharedAccessService.generateAccessLink(form, userId);
        return "redirect:/teacher/share/";
    }


}
