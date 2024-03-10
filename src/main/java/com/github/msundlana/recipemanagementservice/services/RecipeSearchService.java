package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeSearchService {
    public Page<RecipeDto> searchFilteredRecipes(Boolean vegetarian, String searchText, Integer servings,
                                                 List<String> includedIngredients, List<String> excludedIngredients,
                                                 Pageable pageable);
}
