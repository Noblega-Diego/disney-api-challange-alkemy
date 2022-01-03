package com.history.api.disney.controllers;

import com.history.api.disney.models.Base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

public interface BaseController <E, ID extends Serializable>{
    public ResponseEntity<?> getAll();
    public ResponseEntity<?> getOne(ID id);
    public ResponseEntity<?> save(E entity);
    public ResponseEntity<?> delete(ID id);
    public ResponseEntity<?> update(ID id, E entity);
}
