package com.iryna.xmlimporter.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table (name = "castObce")
@NoArgsConstructor
@Getter
@Setter
public class CastObce {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private UUID id;

    private String nazevCastiObce;

    @Column(unique = true, nullable = false)
    private Integer kodCastiObce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "kodObce",
            referencedColumnName = "kodObce",
            nullable = false
    )
    private Obec obec;

}
