package com.interlink.quiz.service;

import com.interlink.quiz.object.User;
import com.interlink.quiz.object.UserDto;
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

    public void register(UserDto userDto) {
        User user = createUser(userDto);
        Integer id = userRepository.saveUser(user);
        user.setId(id);
        UserRole userRole = new UserRole();
        userRole.setRole(roleRepository.getRoleByName("STUDENT"));
        userRole.setUser(user);
        roleRepository.addRoleToUser(userRole);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    private User createUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }
}
