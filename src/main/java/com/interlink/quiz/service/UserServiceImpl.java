package com.interlink.quiz.service;

import com.interlink.quiz.object.User;
import com.interlink.quiz.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl {

    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserServiceImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Transactional
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Transactional
    public User findById(int id) {
        return userJpaRepository.findById(id);
    }

    @Transactional
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
