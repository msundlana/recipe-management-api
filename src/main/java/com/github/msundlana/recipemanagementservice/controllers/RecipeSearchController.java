package com.github.msundlana.recipemanagementservice.controllers;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;
import com.github.msundlana.recipemanagementservice.utilities.RecipeHelper;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("${api.base}/recipes/search")
public class RecipeSearchController {
    @Autowired
    private RecipeSearchService recipeSearchService;

    @GetMapping(value="/findFilteredRecipes")
    public ResponseEntity<Page<RecipeDto>> findFilteredRecipes(
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) @Positive Integer servings,
            @RequestParam(required = false) List<String> includedIngredients,
            @RequestParam(required = false) List<String> excludedIngredients,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(required = false, defaultValue = "20") @Positive Integer pageSize,
            @RequestParam(required = false) String... sort) {

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(
                vegetarian, searchText, servings, includedIngredients,
                excludedIngredients, RecipeHelper.getPageRequest(page,pageSize,sort));

        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

}
