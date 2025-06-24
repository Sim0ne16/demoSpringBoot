package org.example.demo2.utils.mapper.impl.response;

import java.util.List;
import java.util.Set;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProfessoreResponseMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreResponse> {
    
    //Semplici
    @Named("sempliceProfessore")
    @Override
    @Mapping(ignore = true, target = "classi")
    ProfessoreResponse fromEntityToRe(ProfessoreEntity entity);
    
    @Named("sempliceProfessore")
    @Override
    @Mapping(ignore = true, target = "classi")
    ProfessoreEntity fromReToEntity(ProfessoreResponse re);
    
    @Override
    @IterableMapping(qualifiedByName = "sempliceProfessore")
    List<ProfessoreResponse> fromEntityListToReList(List<ProfessoreEntity> entityList);

    @Override
    @IterableMapping(qualifiedByName = "sempliceProfessore")
    List<ProfessoreEntity> fromReListToEntityList(List<ProfessoreResponse> reList);
    
    // Mapping for ClasseEntity <-> ClasseResponse
    @Named("sempliceClasse")
    @Mapping(target = "studenti", ignore = true)
    @Mapping(target = "professori", ignore = true)
    org.example.demo2.dto.response.ClasseResponse fromClasseEntityToClasseResponse(org.example.demo2.dao.entity.ClasseEntity entity);

    @IterableMapping(qualifiedByName = "sempliceClasse")
    Set<org.example.demo2.dto.response.ClasseResponse> fromClasseEntitySetToClasseResponseSet(Set<org.example.demo2.dao.entity.ClasseEntity> entities);

    //Completi
    /**
     * Include la lista di classi, ma **mappa ogni classe** nel modo “semplice” 
     * (evitando di ri-deschiarare studenti/professori dentro ClasseResponse)
     */
    @Named("completo")
    @Mapping(target = "classi", qualifiedByName = "sempliceClasse")
    ProfessoreResponse fromEntityToReCompleto(ProfessoreEntity entity);
    
    @IterableMapping(qualifiedByName = "completo")
    @Named("completoList")
    List<ProfessoreResponse> fromEntityListToReListCompleto(List<ProfessoreEntity> entityList);

}
