package com.history.api.disney.dao;

import java.util.Arrays;
import java.util.List;
import com.history.api.disney.models.CharacterModel;
import org.springframework.transaction.annotation.Transactional;

public interface CharacterDao extends BasicDao<CharacterModel, Long>{

    public List<CharacterModel> findByName(String name);

	public List<CharacterModel> findByAge(Integer age);

	@Transactional
	List<CharacterModel> findByWeight(Integer weight);

	public List<CharacterModel> findByFilmTitle(String title);

	public List<CharacterModel> findByFilmId(Long id);
}
