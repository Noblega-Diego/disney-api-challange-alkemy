package com.history.api.disney.dao;

import com.history.api.disney.models.Movie;

import java.io.Serializable;
import java.util.List;

public interface BasicDao<T extends Serializable, ID extends Serializable> {
    public T findById(ID id);

    public List<T> listAll();

    public boolean remove(ID id);

    public T update(T entity);

    public T insert(T entity);
}
