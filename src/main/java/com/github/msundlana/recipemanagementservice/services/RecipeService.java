package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;

import java.util.List;

public interface RecipeService {
    public List<RecipeDto> getAllRecipes() ;

    public RecipeDto getRecipeById(Long id) ;

    public List<RecipeDto> filterRecipes(boolean vegetarian, int servings, List<String> includedIngredients, List<String> excludedIngredients, String searchText);

    public RecipeDto addRecipe(RecipeDto recipeDto);

    public RecipeDto updateRecipe(long id,RecipeDto recipeDto);

    public void deleteRecipe(long id);
}
