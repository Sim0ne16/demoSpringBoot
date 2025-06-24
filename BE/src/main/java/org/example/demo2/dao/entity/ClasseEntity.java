package org.example.demo2.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


// Le entity vanno marcate con @Entity, le repository con @Repository etc con service e controller

@Entity
// Ci fornisce toString, getter, setter ... etc
@Data
@AllArgsConstructor
@NoArgsConstructor
//Pattern utile ( to see )
@Builder
// e' buona cosa specificare la tabella di riferimento lato DB cosi' da avere piu' controllo
//@Table(name = "studenti")

@Table(name = "classi")
public class ClasseEntity {

    @Id
    private Long id;

    @Column(nullable = false)
    private char identificativo;

    @Column(nullable = false)
    private String grado;

    /*
    Fetch : se non mi devo preoccupare di PRESTAZIONI posso usare EAGER altrimenti LAZY

    CascadeType.PERSIST: L'operazione di persistenza viene propagata alle entità associate. Se si salvano l'entità principale, l'entità associata viene salvata automaticamente.
    CascadeType.MERGE: L'operazione di merge viene propagata alle entità associate. Se si aggiorna l'entità principale, l'entità associata viene aggiornata o persistita se non esiste.
    CascadeType.REMOVE: L'operazione di rimozione viene propagata alle entità associate. Se si cancella l'entità principale, l'entità associata viene cancellata.
    CascadeType.REFRESH: L'operazione di refresh viene propagata alle entità associate. Se si ricarica l'entità principale, l'entità associata viene ricaricata.
    CascadeType.DETACH: L'operazione di detach viene propagata alle entità associate. Se si stacca l'entità principale, l'entità associata viene staccata.
    CascadeType.ALL: Applica tutte le operazioni cascade. È equivalente a PERSIST, MERGE, REMOVE, REFRESH e DETACH.
    */

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudenteEntity> studenti;

    /*
    Tipico caso di relazione MOLTI A MOLTI
    Tabella di relazione -> classe_professore
    joinColumns e' la colonna che rappresenta l'entità corrente (in questo caso Classe) nella tabella di join
    inverseJoinColumns e' la colonna che rappresenta l'altra entità (cioè Professore)
    */

    @ManyToMany
    @JoinTable(
            name = "classe_professore",
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "professore_id")
    )
    private List<ProfessoreEntity> professori;

}
