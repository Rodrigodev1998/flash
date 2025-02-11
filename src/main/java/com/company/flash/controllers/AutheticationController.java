package com.company.flash.controllers;

import com.company.flash.domain.profile.Profile;
import com.company.flash.domain.profile.dtos.*;
import com.company.flash.infra.security.TokenService;
import com.company.flash.repositories.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1")
public class AutheticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((Profile) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByUsername(data.username()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Profile newUser = new Profile(data.username(), encryptedPassword, data.role(), data.phone());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/auth/update")
    public ResponseEntity<String> updateProfile(@RequestBody @Valid UpdateProfileDTO data) {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile existingProfile = repository.findByUsername(currentUsername);

        if (existingProfile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        existingProfile.setUsername(data.username());
        existingProfile.setPassword(new BCryptPasswordEncoder().encode(data.password()));

        repository.save(existingProfile);
        return ResponseEntity.ok("Profile updated successfully");
    }

}
