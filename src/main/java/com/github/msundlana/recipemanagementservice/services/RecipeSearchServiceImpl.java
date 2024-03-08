package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import com.github.msundlana.recipemanagementservice.utilities.RecipeHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeSearchServiceImpl implements RecipeSearchService{
    @Autowired
    private RecipeRepository recipeRepository;
    private static final ModelMapper mapper = new ModelMapper();
    @Override
    public List<RecipeDto> searchFilteredRecipes(boolean vegetarian, Integer servings,
                                                 List<String> includedIngredients,
                                                 List<String> excludedIngredients, String searchText) {
        servings = servings==null?-1:servings;
        var recipes = recipeRepository.findFilteredRecipes(vegetarian, servings,
                includedIngredients, excludedIngredients, searchText);

        return recipes.stream().map(RecipeHelper:: convertToDto).toList();
    }
}
