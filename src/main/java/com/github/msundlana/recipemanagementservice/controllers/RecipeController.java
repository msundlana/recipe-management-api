package com.github.msundlana.recipemanagementservice.controllers;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeService;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<RecipeDto>> filterRecipes(@RequestParam(required = false) boolean vegetarian,
                                                      @RequestParam(required = false) @Positive int servings,
                                                      @RequestParam(required = false) List<String> includedIngredients,
                                                      @RequestParam(required = false) List<String> excludedIngredients,
                                                      @RequestParam(required = false) String searchText) {
        List<RecipeDto> filteredRecipes = recipeService.filterRecipes(vegetarian, servings, includedIngredients, excludedIngredients, searchText);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable("id") @Positive Long id) {
        var recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> addRecipe(@RequestBody @Validated @NonNull RecipeDto recipe) {
        var addedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable("id") @Positive Long id, @RequestBody @Validated RecipeDto recipe) {
        var updatedRecipe = recipeService.updateRecipe(id, recipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") @Positive Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

