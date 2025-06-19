package org.example.demo2.dao.repository;

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

@Repository
public interface ProfessoreRepository extends JpaRepository<ProfessoreEntity,Long> {
}
