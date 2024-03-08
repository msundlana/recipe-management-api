package com.github.msundlana.recipemanagementservice.controllers;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("${api.base}/recipes/search")
public class RecipeSearchController {
    @Autowired
    private RecipeSearchService recipeSearchService;

    @GetMapping("/findFilteredRecipes")
    public ResponseEntity<List<RecipeDto>> searchFilteredRecipes(@RequestParam(required = false) boolean vegetarian,
                                                         @RequestParam(required = false) @Positive Integer servings,
                                                         @RequestParam(required = false) List<String> includedIngredients,
                                                         @RequestParam(required = false) List<String> excludedIngredients,
                                                         @RequestParam(required = false) String searchText) {
        List<RecipeDto> filteredRecipes = recipeSearchService.searchFilteredRecipes(vegetarian, servings, includedIngredients, excludedIngredients, searchText);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }
}
