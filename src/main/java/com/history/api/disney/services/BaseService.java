package com.history.api.disney.services;

import java.util.List;

public interface BaseService<E>{
	
	public List<?> findAll();
	
	public E findById(Long id);
	
	public E update(Long id, E entity);
	
	public E save(E entity);
	
	public boolean delete(Long id);
	
}
