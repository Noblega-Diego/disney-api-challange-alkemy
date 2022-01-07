package com.history.api.disney.dao;

import java.util.List;

import javax.persistence.*;

import com.history.api.disney.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import com.history.api.disney.models.CharacterModel;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CharacterDaoImp implements CharacterDao{

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	@Transactional
	public CharacterModel findById(Long id){
		String query = "FROM CharacterModel where id=:id";
		try {
			return entityManager.createQuery(query, CharacterModel.class)
					.setParameter("id", id)
					.getSingleResult();
		}catch (NoResultException e) {
			throw new NotFoundException("character with id ("+id+") not exist");
		}
	}

	@Override
	@Transactional
	public CharacterModel update(CharacterModel character) {
		return entityManager.merge(character);
	}

	@Override
	@Transactional
	public List<CharacterModel> listAll() {
		String query = "FROM CharacterModel";
		return entityManager.createQuery(query, CharacterModel.class)
		 		.getResultList();
	}

	@Override
	@Transactional
	public boolean remove(Long id) {
		try {
			String hql = "DELETE from CharacterModel as ch where ch.id=:id";
			Query query = entityManager.createQuery(hql)
					.setParameter("id", id);
			if(query.executeUpdate() != 1) throw new Exception();
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	@Transactional
	public CharacterModel insert(CharacterModel character) {
		entityManager.persist(character);
		return character;
	}

	@Override
	@Transactional
	public List<CharacterModel> findByName(String name) {
		String query = "FROM CharacterModel as ch WHERE ch.name LIKE :name";
		return entityManager.createQuery(query, CharacterModel.class)
				.setParameter("name", "%"+name+"%")
				.getResultList();
	}

	@Override
	@Transactional
	public List<CharacterModel> findByAge(Integer age) {
		String query = "FROM CharacterModel as ch WHERE ch.age=:age";
		return entityManager.createQuery(query, CharacterModel.class)
				.setParameter("age", age)
				.getResultList();
	}

	@Override
	@Transactional
	public List<CharacterModel> findByWeight(Integer weight) {
		String query = "FROM CharacterModel as ch WHERE ch.weight=:weight";
		return entityManager.createQuery(query, CharacterModel.class)
				.setParameter("weight", weight)
				.getResultList();
	}


	@Override
	@Transactional
	public List<CharacterModel> findByFilmTitle(String title) {
		String query = "SELECT character FROM CharacterModel as character " +
				"JOIN character.movies as movie " +
				"WHERE movie.title LIKE :title";
		return entityManager.createQuery(query, CharacterModel.class)
				.setParameter("title", "%"+ title +"%")
				.getResultList();
	}

	@Override
	public List<CharacterModel> findByFilmId(Long id) {
		String query = "SELECT character FROM CharacterModel as character " +
				"JOIN character.movies as movie " +
				"WHERE movie.id=:id";
		return entityManager.createQuery(query, CharacterModel.class)
				.setParameter("id", id)
				.getResultList();
	}
}
