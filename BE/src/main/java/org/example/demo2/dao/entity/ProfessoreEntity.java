package org.example.demo2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo2.utils.enums.Specializzazione;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "professori")
public class ProfessoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identity -> Il valore è generato dal database alla INSERT
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "data_nascita")
    private LocalDateTime dataNascita;

    @Enumerated(EnumType.STRING)
    private Specializzazione specializzazione;

    /*
    Nelle relazioni bidirezionali (cioè quando due entità si "conoscono" tra loro),
    dobbiamo dire a JPA dove si trova il "lato proprietario" della relazione.
     */

    @ManyToMany(mappedBy = "professori", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ClasseEntity> classi;
}
