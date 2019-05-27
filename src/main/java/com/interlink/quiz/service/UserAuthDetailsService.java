package com.interlink.quiz.service;

import com.interlink.quiz.object.Role;
import com.interlink.quiz.object.User;
import com.interlink.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(user.getPasswordHash());
        builder.roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new));

        return builder.build();
    }
}
