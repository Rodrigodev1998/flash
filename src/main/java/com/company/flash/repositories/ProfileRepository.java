package com.company.flash.repositories;

import com.company.flash.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;


public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    Profile findByUsername(String username);

}
