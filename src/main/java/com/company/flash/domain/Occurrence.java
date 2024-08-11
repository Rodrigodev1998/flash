package com.company.flash.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class Occurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Delivery delivery;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime created_at;
}
