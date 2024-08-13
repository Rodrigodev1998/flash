package com.company.flash.domain.profile.dtos;

import com.company.flash.domain.profile.TypeProfile;

public record ProfileResponseDTO(String id,
                                 String username,
                                 TypeProfile role) {
}
