package com.github.msundlana.recipemanagementservice.controllers;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;
import com.github.msundlana.recipemanagementservice.utilities.RecipeHelper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("${api.base}/recipes/search")
public class RecipeSearchController {
    private static final Logger logger = LoggerFactory.getLogger(RecipeSearchController.class);
    @Autowired
    private RecipeSearchService recipeSearchService;

    @GetMapping(value="/findFilteredRecipes")
    public ResponseEntity<Page<RecipeDto>> findFilteredRecipes(
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) @Positive Integer servings,
            @RequestParam(required = false) @Size(min = 1) List<String> includedIngredients,
            @RequestParam(required = false) @Size(min = 1) List<String> excludedIngredients,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(required = false, defaultValue = "20") @Positive Integer pageSize,
            @RequestParam(required = false) String... sort) {
        logger.info("Received request to find filtered recipes");

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(
                vegetarian, searchText, servings, includedIngredients,
                excludedIngredients, RecipeHelper.getPageRequest(page,pageSize,sort));

        logger.info("Filtered recipes successfully retrieved");
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

}
