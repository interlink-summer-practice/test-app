package com.interlink.quiz.service;

import com.interlink.quiz.auth.security.SignUpRequest;
import com.interlink.quiz.object.QuizSession;
import com.interlink.quiz.object.User;
import com.interlink.quiz.repository.QuizSessionRepository;
import com.interlink.quiz.repository.RoleRepository;
import com.interlink.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final QuizSessionRepository quizSessionRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       QuizSessionRepository quizSessionRepository) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.quizSessionRepository = quizSessionRepository;
    }

    public void register(SignUpRequest signUpRequest, HttpSession httpSession) {
        User user = userRepository.saveUser(createUser(signUpRequest));
        saveGuestResults(user, httpSession);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public boolean isCurator(Authentication authentication) {
        String curatorRole = "CURATOR";
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (curatorRole.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }

        return false;
    }

    private User createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.getRoleByName("STUDENT")));

        return user;
    }

    private void saveGuestResults(User user, HttpSession httpSession) {
        List<QuizSession> quizSessions = quizSessionRepository.getQuizSessionBySessionId(httpSession.getId());
        for (QuizSession quizSession : quizSessions) {
            quizSession.setUser(user);
            quizSessionRepository.updateQuizSession(quizSession);
        }
    }
}
