package com.company.flash.domain.profile.dtos;

import com.company.flash.domain.profile.TypeProfile;

public record RegisterDTO(String username,
                          String password,
                          TypeProfile role, String phone) {
}
