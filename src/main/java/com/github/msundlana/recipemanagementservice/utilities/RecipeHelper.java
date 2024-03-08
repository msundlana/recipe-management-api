package com.github.msundlana.recipemanagementservice.utilities;

import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import org.modelmapper.ModelMapper;

public class RecipeHelper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Recipe convertToEntity(RecipeDto recipeDto) {
        return mapper.map(recipeDto, Recipe.class);
    }

    public static RecipeDto convertToDto(Recipe recipe) {
        return mapper.map(recipe, RecipeDto.class);
    }
}
