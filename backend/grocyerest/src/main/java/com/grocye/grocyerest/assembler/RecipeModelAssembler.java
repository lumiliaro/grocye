package com.grocye.grocyerest.assembler;

import com.grocye.grocyerest.controller.RecipeController;
import com.grocye.grocyerest.model.Recipe;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecipeModelAssembler implements RepresentationModelAssembler<Recipe, EntityModel<Recipe>> {
    @Override
    public EntityModel<Recipe> toModel(Recipe recipe) {

        return EntityModel.of(recipe, //
                linkTo(methodOn(RecipeController.class).read(recipe.getId())).withSelfRel(),
                linkTo(methodOn(RecipeController.class).index()).withRel("recipes"));
    }
}
