package com.history.api.disney.dao;

import com.history.api.disney.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User findById(Long id) {
        String query = "FROM User WHERE id=:id";
        return (User) entityManager.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<User> listAll() {
        String query = "FROM User";
        return entityManager.createQuery(query, User.class)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean remove(Long id) {
        try {
            String query = "DELETE FROM User as m WHERE m.id=:id";
            entityManager.createQuery(query)
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    @Transactional
    public User insert(User user) {
        User u = new User();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setLastname(user.getLastname());
        entityManager.persist(u);
        return u;
    }

    @Override
    public User findByEmail(String email) {
        String query = "FROM User WHERE email=:email";
        try {
            return (User) entityManager.createQuery(query)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}
