package com.history.api.disney.controllers;

import com.history.api.disney.dto.BaseDTO;
import com.history.api.disney.models.Base;
import com.history.api.disney.services.BaseService;
import com.history.api.disney.utils.Mapeador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public abstract class BaseControllerImp<E extends Base,DTO extends BaseDTO,S extends BaseService<E>>
        implements BaseController<DTO, Long>{


    protected Class<E> E_CLASS;
    protected Class<DTO> DTO_CLASS;

    @Autowired
    protected S service;

    @Autowired
    protected Mapeador mapeador;

    public BaseControllerImp(Class<E> entityClass, Class<DTO> dtoClass){
        this.E_CLASS = entityClass;
        this.DTO_CLASS = dtoClass;
    }


    @Override
    @RequestMapping(value = "", method = RequestMethod.GET,params = {})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapeador.map(service.findById(id), DTO_CLASS));
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody DTO entity) {
        var o = mapeador.map(entity, E_CLASS);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapeador.map(service.save(o), DTO_CLASS));
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"OK\"}");
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DTO entity) {
        var o = service.update(id, mapeador.map(entity, E_CLASS));
        return ResponseEntity.status(HttpStatus.OK).body(mapeador.map(o,DTO_CLASS));
    }


}
