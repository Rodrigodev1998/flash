package com.company.flash.repositories;

import com.company.flash.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface ProfileRepository extends JpaRepository<Profile, String> {

    UserDetails findByUsername(String username);
}
