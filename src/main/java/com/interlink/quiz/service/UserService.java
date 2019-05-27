package com.interlink.quiz.service;

import com.interlink.quiz.object.User;
import com.interlink.quiz.object.UserRegistrationForm;
import com.interlink.quiz.object.UserRole;
import com.interlink.quiz.repository.RoleRepository;
import com.interlink.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void register(UserRegistrationForm userRegistrationForm) {
        User user = createUser(userRegistrationForm);
        Integer id = userRepository.saveUser(user);
        user.setId(id);
        UserRole userRole = new UserRole();
        userRole.setRole(roleRepository.getRoleByName("STUDENT"));
        userRole.setUser(user);
        roleRepository.addRoleToUser(userRole);
    }

    private User createUser(UserRegistrationForm userRegistrationForm) {
        User user = new User();
        user.setFirstName(userRegistrationForm.getFirstName());
        user.setLastName(userRegistrationForm.getLastName());
        user.setEmail(userRegistrationForm.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRegistrationForm.getPassword()));

        return user;
    }
}
