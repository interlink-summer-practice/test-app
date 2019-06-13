package com.interlink.quiz.auth.security;

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
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id.intValue());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with id : " + id);
        }

        return UserPrincipal.create(user);
    }
}
