package com.history.api.disney.services;

import java.util.List;

public interface BaseService<E>{
	
	public List<?> findAll() throws Exception;
	
	public Object findById(Long id) throws Exception;
	
	public Object update(Long id, E entity) throws Exception;
	
	public Object save(E entity) throws Exception;
	
	public boolean delete(Long id) throws Exception;
	
}
