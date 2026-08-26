package com.example.meniservice.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "jelo")
public class Jelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idjelo", nullable = false)
    private Integer id;

    @Column(name = "nazivJelo", nullable = false, length = 45)
    @NotNull
    @NotBlank
    @Length(min=3, max = 45)
    private String nazivJelo;

    @Column(name = "opisJelo", nullable = false, length = 200)
    @NotNull
    @NotBlank
    @Length(min=3, max = 200)
    private String opisJelo;

    @Column(name = "cenaJelo", nullable = false)
    @NotNull
    @Positive
    @Min(100)
    private Double cenaJelo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kategorijaJela_idkategorijaJela", nullable = false)
    @NotNull
    private Kategorijajela kategorijajelaIdkategorijajela;
}
