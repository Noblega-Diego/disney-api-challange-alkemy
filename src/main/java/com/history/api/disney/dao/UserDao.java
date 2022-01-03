package com.history.api.disney.dao;

import com.history.api.disney.models.User;


public interface UserDao extends BasicDao<User, Long>{
    public User findByEmail(String email);
}
