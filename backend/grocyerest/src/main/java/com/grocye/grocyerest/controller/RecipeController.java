package com.grocye.grocyerest.controller;

import com.grocye.grocyerest.assembler.RecipeModelAssembler;
import com.grocye.grocyerest.model.Recipe;
import com.grocye.grocyerest.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RecipeController {
    private final RecipeRepository repository;
    private final RecipeModelAssembler assembler;

    public RecipeController(RecipeRepository repository, RecipeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/recipes")
    public CollectionModel<EntityModel<Recipe>> index() {
        List<EntityModel<Recipe>> recipes = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(recipes, linkTo(methodOn(RecipeController.class).index()).withSelfRel());
    }

    @GetMapping("/recipes/list")
    public Page<Recipe> indexList(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @PostMapping("/recipes")
    public ResponseEntity<?> create(@RequestBody Recipe newRecipe) {
        EntityModel<Recipe> entityModel = assembler.toModel(repository.save(newRecipe));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/recipes/{id}")
    public EntityModel<Recipe> read(@PathVariable("id") Long id) {
        Recipe recipe = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "recipe not found"));

        return assembler.toModel(recipe);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Recipe newRecipe) {
        Recipe updatedRecipe = repository.findById(id) //
                .map(recipe -> {
                    recipe = newRecipe;
                    return repository.save(recipe);
                }) //
                .orElseGet(() -> {
                    newRecipe.setId(id);
                    return repository.save(newRecipe);
                });

        EntityModel<Recipe> entityModel = assembler.toModel(updatedRecipe);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
