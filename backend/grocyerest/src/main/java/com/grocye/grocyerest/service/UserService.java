package com.grocye.grocyerest.service;

import com.grocye.grocyerest.dto.RegistrationDto;
import com.grocye.grocyerest.model.User;
import com.grocye.grocyerest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository /*, PasswordEncoder passwordEncoder*/) {
        this.repository = repository;
        // this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerNewUser(RegistrationDto registrationDto) {
        // Überprüfen, ob der Benutzername oder die E-Mail bereits existiert
        if (repository.existsByUsername(registrationDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
        }
        if (repository.existsByEmail(registrationDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-Mail already exists.");
        }
        if (!Objects.equals(registrationDto.getPassword(), registrationDto.getPasswordConfirmation())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password and password confirmation are not equal");
        }

        // Erstellen eines neuen User-Objekts
        User newUser = User.builder()
            .username(registrationDto.getUsername())
            .email(registrationDto.getEmail())
            .passwordHash(registrationDto.getPassword())
        .build();


        return repository.save(newUser);
    }
}