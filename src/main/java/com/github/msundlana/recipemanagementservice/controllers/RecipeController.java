package com.github.msundlana.recipemanagementservice.controllers;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeService;
import com.github.msundlana.recipemanagementservice.utilities.RecipeHelper;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base}/recipes")
public class RecipeController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<Page<RecipeDto>> getAllRecipes(@RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer page,
                                                         @RequestParam(required = false, defaultValue = "20") @Positive Integer size,
                                                         @RequestParam(required = false) String... sort) {
        logger.info("Received request to get all recipes");

        var recipes = recipeService.getAllRecipes(RecipeHelper.getPageRequest(page, size, sort));

        logger.info("Returning {} recipes", recipes.getTotalElements());
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable("id") @Positive Long id) {
        logger.info("Received request to get recipe by ID: {}", id);

        var recipe = recipeService.getRecipeById(id);

        logger.info("Recipe found with ID {}: {}", id, recipe);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> addRecipe(@RequestBody @Validated @NonNull RecipeDto recipe) {
        logger.info("Received request to add recipe: {}", recipe);

        var addedRecipe = recipeService.addRecipe(recipe);

        logger.info("Recipe added successfully with ID: {}", addedRecipe.getId());
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable("id") @Positive Long id, @RequestBody @Validated RecipeDto recipe) {
        logger.info("Received request to update recipe with ID {}: {}", id, recipe);

        var updatedRecipe = recipeService.updateRecipe(id, recipe);

        logger.info("Recipe updated successfully with ID: {}", id);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") @Positive Long id) {
        logger.info("Received request to delete recipe with ID: {}", id);

        recipeService.deleteRecipe(id);

        logger.info("Recipe deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

