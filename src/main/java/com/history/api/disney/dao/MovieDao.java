package com.history.api.disney.dao;


import com.history.api.disney.models.*;

import java.util.Arrays;
import java.util.List;

public interface MovieDao extends BasicDao<Movie, Long>{

    public List<Movie> findByGenere(Long id);

    public List<Movie> findByTitle(String title, String order);

    public List<Movie> listAll(String order);
}
