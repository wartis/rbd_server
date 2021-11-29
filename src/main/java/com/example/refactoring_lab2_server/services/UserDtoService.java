package com.example.refactoring_lab2_server.services;

import com.example.refactoring_lab2_server.entities.User;
import com.example.refactoring_lab2_server.entities.UserDto;
import com.example.refactoring_lab2_server.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UserDtoService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public User register(UserDto userData) {
        User user = new User();
        user.setUsername(userData.getUsername());
        user.setPassword(encoder.encode(userData.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    public boolean canLogin(String login, String password) {
        String encoded = encoder.encode(password);
        return userRepository.findByUsernameAndPassword(login, encoded) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDto(userRepository.findByUsername(username));
    }
}
