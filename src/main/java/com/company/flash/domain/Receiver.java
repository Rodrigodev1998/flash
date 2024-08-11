package com.company.flash.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Receiver {

    @Column(name = "receiver_name")
    @NotBlank
    private String name;

    @Column(name = "receiver_street")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String street;

    @Column(name = "receiver_number")
    @NotBlank
    private String number;

    @Column(name = "receiver_complement")
    @NotBlank
    private String complement;

    @Column(name = "receiver_neighborhood")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String neighborhood;

}
