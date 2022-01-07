package com.history.api.disney.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import com.history.api.disney.exceptions.BadRequestException;
import com.history.api.disney.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;
import com.history.api.disney.models.Movie;


@Repository
public class MovieDaoImp implements MovieDao {
	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public Movie findById(Long id) {
		String query = "FROM Movie WHERE id=:id";
		try {
			return (Movie) entityManager.createQuery(query)
					.setParameter("id", id)
					.getSingleResult();
		}catch (NoResultException e) {
			throw new NotFoundException("movie with id ("+id+") not exist");
		}
	}

	@Override
	public List<Movie> listAll() {
		String query = "FROM Movie";
		return entityManager.createQuery(query, Movie.class)
				.getResultList();
	}

	@Override
	@Transactional
	public boolean remove(Long id) {
		try {
			entityManager.remove(findById(id));
			return true;
		}catch (Exception e){
			return false;
		}
	}

	@Override
	@Transactional
	public Movie update(Movie movie) {
		return entityManager.merge(movie);
	}

	@Override
	@Transactional
	public Movie insert(Movie movie) {
		entityManager.persist(movie);
		return movie;
	}

	@Override
	public List<Movie> findByGenere(Long id) {
		String query = "FROM Movie as m where m.genere.id=:id";
		return entityManager.createQuery(query, Movie.class)
				.setParameter("id", id)
				.getResultList();
	}

	@Override
	public List<Movie> findByTitle(String title, String order) {
		if(order.equals("ASC") || order.equals("DESC")){
			String query = "FROM Movie as m where m.title LIKE :title ORDER BY m.creationDate " + (order.equals("DESC")? "desc":"ASC");
			return entityManager.createQuery(query, Movie.class)
				.setParameter("title", "%"+ title +"%")
				.getResultList();
		}
		else return new ArrayList<>();
	}

	@Override
	public List<Movie> listAll(String order) {
		if(order.equals("ASC") || order.equals("DESC")) {
			String query = "FROM Movie as m ORDER BY m.creationDate " + ((order.equals("DESC"))? "desc":"asc");
			return entityManager.createQuery(query, Movie.class)
					.getResultList();
		}else return new ArrayList<>();
	}
}
