package com.company.flash.domain.profile.dtos;

import com.company.flash.domain.profile.TypeProfile;

import java.util.UUID;

public record ProfileResponseDTO(UUID id,
                                 String username,
                                 TypeProfile role) {
}
