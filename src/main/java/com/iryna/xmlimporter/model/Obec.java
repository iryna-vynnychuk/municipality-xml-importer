package com.iryna.xmlimporter.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "obec")
@NoArgsConstructor
@Getter
@Setter
public class Obec {

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private UUID Id;

    @Column(nullable = false)
    private String nazevObce;

    @Column(unique = true, nullable = false)
    private Integer kodObce;

    @OneToMany(mappedBy = "obec", cascade = CascadeType.ALL)
    private List<CastObce> castiObce = new ArrayList<>();

}
