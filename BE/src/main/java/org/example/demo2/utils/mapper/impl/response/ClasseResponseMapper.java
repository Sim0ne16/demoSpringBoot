package org.example.demo2.utils.mapper.impl.response;

import java.util.List;
import java.util.Set;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


//Allora avevi chiesto mapper che ti mappino dati completi e mapper che ti mappino i dati base allora li ho differenziati 
//Usato @Named anche se no obbligatoria ma consigliato se ho più metodi di mapping per lo stesso tipo 
//E cosi MapStruct non si confonde e IterableMapping() mi prende il metodo giusto
@Mapper(componentModel = "spring")
// Questa cosa di aggiungere questa dipendenza per mappare rapporti più completi
// non mi convice ma mi dirai tu
public interface ClasseResponseMapper extends GeneralRestMapper<ClasseEntity, ClasseResponse> {

    // Semplici
    @Override
    @Named("sempliceClasse")
    @Mapping(ignore = true, target = "studenti") // evita loop potenziali
    @Mapping(ignore = true, target = "professori")
    ClasseResponse fromEntityToRe(ClasseEntity entity);

    @Override
    @Named("sempliceClasse")
    @Mapping(ignore = true, target = "studenti")
    @Mapping(ignore = true, target = "professori")
    ClasseEntity fromReToEntity(ClasseResponse re);

    @Override
    @IterableMapping(qualifiedByName = "sempliceClasse")
    List<ClasseResponse> fromEntityListToReList(List<ClasseEntity> entityList);

    @Override
    @IterableMapping(qualifiedByName = "sempliceClasse")
    List<ClasseEntity> fromReListToEntityList(List<ClasseResponse> reList);

    // Completi
    /*
     * Mappa una singola ClasseEntity → ClasseResponse **includendo** studenti e
     * professori
     * (ma per i professori userà il metodo @Named("completo") di
     * ProfessoreResponseMapper)
     */
    @Named("completo")
    @Mapping(target = "studenti",source= "studenti", qualifiedByName = "sempliceStudenti")
    @Mapping(source="professori",target = "professori", qualifiedByName = "completoList")
    ClasseResponse fromEntityListToReCompleto(ClasseEntity entity);

    
    // Mappa una lista di ClasseEntity → ClasseResponse usando il metodo sopra

    List<ClasseResponse> fromEntityToReListCompleto(List<ClasseEntity> entityList);

    @Named("sempliceClasse")
    @IterableMapping(qualifiedByName = "sempliceClasse")
    List<ClasseResponse> mapClasseSetToClasseResponseSet(Set<ClasseEntity> classi);

    // Questo dice a MapStruct: "Se vuoi una lista/set di classi mappate in modo
    // semplice, usa fromEntityToRe (quello annotato con @Named("semplice"))".







    // Mapping method for professori with the qualifier "completoList"
    @Named("completoList")
    List<org.example.demo2.dto.response.ProfessoreResponse> mapProfessoriToProfessoreResponseCompletoList(List<org.example.demo2.dao.entity.ProfessoreEntity> professori);

    // Mapping method for studenti with the qualifier "sempliceStudenti"
    @Named("sempliceStudenti")
    List<org.example.demo2.dto.response.StudenteResponse> mapStudentiToStudentiResponse(List<org.example.demo2.dao.entity.StudenteEntity> studenti);

}
