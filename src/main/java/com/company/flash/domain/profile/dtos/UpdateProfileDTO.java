package com.company.flash.domain.profile.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileDTO(String username, @NotBlank String password) {}
