package org.example.demo2.dao.repository;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.utils.enums.Specializzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProfessoreRepository extends JpaRepository<ProfessoreEntity, Long> {

    Optional<ProfessoreEntity> findByNomeAndCognome(String nome, String cognome);

    List<ProfessoreEntity> findBySpecializzazione(Specializzazione specializzazione);

    // Trova tutti i professori assegnati a una certa classe
    List<ProfessoreEntity> findAllByClassi_Id(Long classeId);

    List<ProfessoreEntity> findByDataNascitaBetween(LocalDate dataInizio, LocalDate dataFine);


}
