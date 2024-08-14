package com.company.flash.controllers;

import com.company.flash.domain.profile.Profile;
import com.company.flash.domain.profile.dtos.ProfileResponseDTO;
import com.company.flash.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/profile")
public class ProfileController {

    @Autowired
    private ProfileRepository repository;

    @GetMapping("/profile-all")
    public List<ProfileResponseDTO> getAll(){
        List<Profile> profileList = repository.findAll();
        return profileList.stream()
                .map(profile -> new ProfileResponseDTO(
                        profile.getId(),
                        profile.getUsername(),
                        profile.getRole()
                ))
                .collect(Collectors.toList());
    }
}
