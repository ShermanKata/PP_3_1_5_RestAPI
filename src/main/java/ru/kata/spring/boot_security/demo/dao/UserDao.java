package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public void deleteUserById(int id) {
        entityManager.remove(getUserById(id));
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    public User getUserByUsername(String username) {
        Query query = entityManager.createQuery("select user from User user where user.username = :username", User.class);
        query.setParameter("username", username);
        //return entityManager.find(User.class, username);
        return (User) query.getSingleResult();
    }

    public List<User> getListOfUsers() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }
}
