package org.example.demo2.service.general;

import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.exception.NotFoundException;

import java.util.List;

//Ipotizziamo che ogni service abbia bisogno dei metodi CRUD
public interface CrudService<T> {

    T insert(T dto);
    List<ClasseResponse> getAll();
    T update(T dto) throws NotFoundException;
    void delete(Long id) throws NotFoundException;

}

