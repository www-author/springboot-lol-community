package com.lol.community.user.repository;

import com.lol.community.user.domain.User;
import com.lol.community.user.form.UserEditForm;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByName(String name) {
        List<User> user = em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();

        return user.stream().findAny();
    }

    public Optional<User> findByEmail(String email) {
        List<User> user = em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return user.stream().findAny();
    }

    public User findById(Integer id) {
        return em.find(User.class, id);

    }



    public void update(Integer id, UserEditForm updateParam) {
        User findUser = findById(id);

        User updatedUser = User.builder()
                .id(findUser.getId())
                .name(updateParam.getName())
                .email(updateParam.getEmail())
                .password(updateParam.getPassword())
                .grade(findUser.getGrade())
                .build();

        em.merge(updatedUser);
    }
}
