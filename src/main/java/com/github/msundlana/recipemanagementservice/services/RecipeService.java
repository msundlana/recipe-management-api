package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
    public Page<RecipeDto> getAllRecipes(Pageable pageable) ;

    public RecipeDto getRecipeById(Long id) ;

    public RecipeDto addRecipe(RecipeDto recipeDto);

    public RecipeDto updateRecipe(long id,RecipeDto recipeDto);

    public void deleteRecipe(long id);
}
