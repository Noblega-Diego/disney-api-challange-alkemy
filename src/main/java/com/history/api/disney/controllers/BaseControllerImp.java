package com.history.api.disney.controllers;

import com.history.api.disney.dto.BaseDTO;
import com.history.api.disney.models.Base;
import com.history.api.disney.services.BaseService;
import com.history.api.disney.utils.Mapeador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;

public abstract class BaseControllerImp<E extends Base,DTO extends BaseDTO,S extends BaseService<E>>
        implements BaseController<DTO, Long>{


    protected Class<E> E_CLASS;

    @Autowired
    protected S service;

    @Autowired
    protected Mapeador mapeador;

    public BaseControllerImp(Class<E> type){
        this.E_CLASS = type;
    }


    @Override
    @RequestMapping(value = "", method = RequestMethod.GET,params = {})
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        }catch (NoResultException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Not found\"}");
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody DTO entity) {
        try {
            Object o = mapeador.map(entity, E_CLASS);
            if (o == null)
                throw new Exception();
            return ResponseEntity.status(HttpStatus.OK).body(service.save(E_CLASS.cast(o)));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if(!service.delete(id))
                throw new Exception();
            return ResponseEntity.status(HttpStatus.OK).body("{\"response\":\"OK\"}");
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DTO entity) {
        try {
            Object o = service.update(id, mapeador.map(entity, E_CLASS));
            if (o == null)
                throw new Exception();
            return ResponseEntity.status(HttpStatus.OK).body(E_CLASS.cast(o));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }


}
