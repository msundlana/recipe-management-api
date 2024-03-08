package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;

import java.util.List;

public interface RecipeSearchService {
    public List<RecipeDto> searchFilteredRecipes(boolean vegetarian, Integer servings,
                                                 List<String> includedIngredients,
                                                 List<String> excludedIngredients, String searchText);

}
