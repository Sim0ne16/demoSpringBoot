package org.example.demo2.utils.mapper.general;

import java.util.List;

//Utile per le entita' che sono prettamente semplici, in caso di complicazioni non si estende questa interfaccia
//e si lavora direttamente con il @Mappper

//Re in questo caso sta sia per Response che Request
public interface GeneralRestMapper<E,R> {

    //Se vogliamo verificare i file generati controllare il .target

    //Nei mapper va ignorato il campo della relazione altrimenti si va in loop infinito


    R fromEntityToRe(E entity);
    E fromReToEntity(R re);
    List<R> fromEntityListToReList(List<E> entityList);
    List<E> fromReListToEntityList(List<R> re);

}
