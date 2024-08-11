package com.company.flash.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Setter
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @NotNull
    @Valid
    private Profile user;

    @ManyToOne
    @NotNull
    @Valid
    private Receiver receiver;

    @Enumerated(EnumType.STRING)
    private StatusDelivery status;


    private OffsetDateTime order;

    private OffsetDateTime finished;
}
