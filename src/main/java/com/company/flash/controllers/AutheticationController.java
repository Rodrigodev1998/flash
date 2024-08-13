package com.company.flash.controllers;

import com.company.flash.domain.profile.Profile;
import com.company.flash.domain.profile.dtos.AuthenticationDTO;
import com.company.flash.domain.profile.dtos.LoginResponseDTO;
import com.company.flash.domain.profile.dtos.ProfileResponseDTO;
import com.company.flash.domain.profile.dtos.RegisterDTO;
import com.company.flash.infra.security.TokenService;
import com.company.flash.repositories.ProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        Profile newUser = new Profile(data.username(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

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
