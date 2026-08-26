package com.example.meniservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "kategorijajela")
public class Kategorijajela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkategorijaJela", nullable = false)
    private Integer id;

    @Column(name = "nazivKategorijaJela", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Size(min = 3, max = 45)
    private String nazivKategorijaJela;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 200)
    @Column(name = "opisKategorijaJela", nullable = false, length = 200)
    private String opisKategorijaJela;

    @JsonIgnore
    @OneToMany(mappedBy = "kategorijajelaIdkategorijajela")
    private Set<Jelo> jelos = new LinkedHashSet<>();

}
