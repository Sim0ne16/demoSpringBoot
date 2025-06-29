package org.example.demo2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo2.utils.enums.Specializzazione;

import java.time.LocalDate;
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
    // Identity -> Il valore è generato dal database alla INSERT
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    /*
     * //@Temporal(TemporalType.TIMESTAMP) -> @Temporal si usa solo con
     * java.util.Date o java.util.Calendar
     * 
     * Se usi java.time.LocalDate o LocalDateTime (Java 8 Time API), non devi
     * usare @Temporal!
     * Hibernate non supporta @Temporal su java.time → quindi crasha subito quando
     * scansiona le entity.
     * 
     * 
     * OK????
     */

    @Column(nullable = false, name = "data_nascita")
    private LocalDate dataNascita;

    @Enumerated(EnumType.STRING)
    private Specializzazione specializzazione;

    /*
     * Nelle relazioni bidirezionali (cioè quando due entità si "conoscono" tra
     * loro),
     * dobbiamo dire a JPA dove si trova il "lato proprietario" della relazione.
     *
     * Per lasciar fare al DB tutto nella eliminazione del professore in relazione
     * con una Classe ho deciso di modificarlo cosi:
     * 
     * Quando cancelli un Professore:
     * 
     * Hibernate aggiorna la tabella classe_professore per rimuovere i record di
     * join.
     * 
     * Le ClasseEntity non vengono toccate.
     * 
     * Nessun errore di FK.
     * 
     * Zero clear() nel Service → Hibernate fa tutto.
     */

    @ManyToMany(mappedBy = "professori", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<ClasseEntity> classi;
}
