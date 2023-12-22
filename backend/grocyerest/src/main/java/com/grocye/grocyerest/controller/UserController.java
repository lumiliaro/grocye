package com.grocye.grocyerest.controller;

import com.grocye.grocyerest.assembler.UserModelAssembler;
import com.grocye.grocyerest.dto.RegistrationDto;
import com.grocye.grocyerest.model.User;
import com.grocye.grocyerest.repository.UserRepository;
import com.grocye.grocyerest.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserRepository repository;
    private final UserModelAssembler assembler;
    private final UserService service;

    public UserController(UserRepository repository, UserModelAssembler assembler, UserService service) {
        this.repository = repository;
        this.assembler = assembler;
        this.service = service;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> index() {
        List<EntityModel<User>> users = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).index()).withSelfRel());
    }

    @GetMapping("/users/list")
    public Page<User> indexList(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@Valid @RequestBody RegistrationDto registrationDto) {

        EntityModel<User> newUser = assembler.toModel(service.registerNewUser(registrationDto));

        return ResponseEntity //
                .created(newUser.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(newUser);
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> read(@PathVariable("id") Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        return assembler.toModel(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User newUser) {
        User updatedUser = repository.findById(id) //
                .map(user -> {
                    user = newUser;
                    return repository.save(user);
                }) //
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
