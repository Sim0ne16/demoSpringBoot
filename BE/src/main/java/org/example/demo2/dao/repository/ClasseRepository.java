package org.example.demo2.dao.repository;

import org.example.demo2.dao.entity.ClasseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<ClasseEntity, Long> {

    List<ClasseEntity> findByIdentificativo(char identificativo);
    
    //Aggiunti per forzare il FETCH 
    @Query("SELECT c FROM ClasseEntity c LEFT JOIN FETCH c.studenti WHERE c.id = :id")
    Optional<ClasseEntity> findByIdWithStudenti(@Param("id") Long id);

    @Query("SELECT c FROM ClasseEntity c LEFT JOIN FETCH c.professori WHERE c.id = :id")
    Optional<ClasseEntity> findByIdWithProfessori(@Param("id") Long id);

}
