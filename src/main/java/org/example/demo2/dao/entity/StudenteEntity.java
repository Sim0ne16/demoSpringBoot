package org.example.demo2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudenteEntity {

    /*
    Annotazioni spesso utilizzate nelle entity :
    @Entity	Indica che la classe e' una entity JPA
    @Table(name = "nome_tabella")	Specifica il nome della tabella nel DB
    @Id	Identifica il campo chiave primaria
    @GeneratedValue(...)	Configura la generazione automatica dell’ID
    @Column(...)	Personalizza la colonna (nome, lunghezza, nullable…)
    @Temporal(...)	Specifica il tipo di dato Date (DATE, TIME, TIMESTAMP)
    @Enumerated(EnumType.STRING)	Per salvare enum come STRING nel DB
    */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Identity -> Il valore e' generato dal database alla INSERT
    private Long id;

    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private int eta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "data_nascita")
    private LocalDateTime dataNascita;

    @ManyToOne
    /*
    L’annotazione @JoinColumn serve a dire a JPA quale colonna del database usare per fare la relazione tra due tabelle.
    Se non specifichi @JoinColumn, JPA userà un nome automatico, tipo classe_id, ma potresti non avere il controllo che vuoi.
    */
    @JoinColumn(name = "classe_id")
    private ClasseEntity classe;

}

