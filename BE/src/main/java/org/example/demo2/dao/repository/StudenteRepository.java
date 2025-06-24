package org.example.demo2.dao.repository;


import org.example.demo2.dao.entity.StudenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudenteRepository extends JpaRepository<StudenteEntity, Long> {

    //L'optional ci aiuta con i casi dove non abbiamo riscontro lato DB
    Optional<StudenteEntity> findByNomeAndCognome(String nome, String cognome);

    //Se vogliamo creare delle query personalizzate
    @Query("SELECT student FROM StudenteEntity student WHERE student.id = :id")
    Optional<StudenteEntity> findByIdCustom(@Param("id") Long id);

    List<StudenteEntity> findAllByClasseId(Long classeId);


}
