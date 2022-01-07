package com.history.api.disney.services;

import java.util.List;
import java.util.stream.Collectors;

import com.history.api.disney.dto.BasicCharacterDTO;
import com.history.api.disney.dto.CharacterDTO;
import com.history.api.disney.dto.CompleteCharacterDTO;
import com.history.api.disney.utils.Mapeador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.history.api.disney.dao.CharacterDao;
import com.history.api.disney.models.CharacterModel;

@Service
public class CharacterService implements BaseService<CharacterModel> {

	@Autowired
	private CharacterDao characterRepository;

	@Autowired
	protected Mapeador maped;

	@Override
	public List<BasicCharacterDTO> findAll(){
		return characterRepository.listAll().stream()
				.map((entity)-> maped.map(entity, BasicCharacterDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public CharacterModel findById(Long id){
		CharacterModel character = characterRepository.findById(id);
		return character;
	}

	@Override
	public CharacterModel update(Long id, CharacterModel entity){
		entity.setId(id);
		return characterRepository.update(entity);
	}

	@Override
	public CharacterModel save(CharacterModel entity){
		return characterRepository.insert(entity);
	}

	@Override
	public boolean delete(Long id){
		return characterRepository.remove(id);
	}


	public List<BasicCharacterDTO> findByName(String name){
		return characterRepository.findByName(name).stream()
				.map((entity)->maped.map(entity,BasicCharacterDTO.class))
				.collect(Collectors.toList());
	}

	public List<BasicCharacterDTO> findByFilmTitle(String title){
		return characterRepository.findByFilmTitle(title).stream()
				.map((entity)->maped.map(entity,BasicCharacterDTO.class))
				.collect(Collectors.toList());
	}


	public List<BasicCharacterDTO> findByFilm(Long filmId) {
		return characterRepository.findByFilmId(filmId).stream()
				.map((entity)-> maped.map(entity, BasicCharacterDTO.class))
				.collect(Collectors.toList());
	}

	public List<BasicCharacterDTO> findByAge(Integer age) {
		return characterRepository.findByAge(age).stream()
				.map((entity)-> maped.map(entity, BasicCharacterDTO.class))
				.collect(Collectors.toList());
	}

	public List<BasicCharacterDTO> findByWeight(Integer weight) {
		return characterRepository.findByWeight(weight).stream()
				.map((entity)-> maped.map(entity, BasicCharacterDTO.class))
				.collect(Collectors.toList());
	}
}
