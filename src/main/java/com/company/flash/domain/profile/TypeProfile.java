package com.company.flash.domain.profile;

public enum TypeProfile {
    ADMIN("admin"),
    DELIVERY_PERSON("delivery_person"),
    SHOP("shop");

    private String role;

    TypeProfile(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
