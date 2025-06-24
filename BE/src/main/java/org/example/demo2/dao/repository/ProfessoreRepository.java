package org.example.demo2.dao.repository;

import java.util.Optional;

/*
JpaRepository
Estende CrudRepository e PagingAndSortingRepository, quindi eredita tutte le operazioni CRUD + altre funzionalità avanzate.
Offre:
Paginazione e ordinamento (findAll(Pageable pageable))
Metodi comodi come:
findAll(Sort sort)
flush()
saveAllAndFlush()
deleteAllInBatch() e deleteAllByIdInBatch()

È specifico per JPA, quindi richiede che tu stia usando un database relazionale con JPA/Hibernate.
*/

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.example.demo2.utils.enums.Specializzazione;



@Repository
public interface ProfessoreRepository extends JpaRepository<ProfessoreEntity,Long> {

    Optional<ProfessoreEntity> findByNomeAndCognome(String nome, String cognome);

    List<ProfessoreEntity> findBySpecializzazione(Specializzazione specializzazione);

    // Trova tutti i professori assegnati a una certa classe
    List<ProfessoreEntity> findAllByClassi_Id(Long classeId);
        


}
