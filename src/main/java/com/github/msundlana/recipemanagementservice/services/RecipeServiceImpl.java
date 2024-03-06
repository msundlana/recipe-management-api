package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.exception.RecipeNotFoundException;
import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    private RecipeRepository recipeRepository;
    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public List<RecipeDto> getAllRecipes() {
        var recipes = recipeRepository.findAll();
        return recipes.stream().map(this:: convertToDto).toList();
    }

    @Override
    public RecipeDto getRecipeById(Long id) {
        var recipe = findRecipeById(id);
        return convertToDto(recipe);
    }

    @Override
    public List<RecipeDto> filterRecipes(boolean vegetarian, int servings,
                                         List<String> includedIngredients, List<String> excludedIngredients, String searchText) {

        var recipes = recipeRepository.findFilteredRecipes(vegetarian, servings,
                includedIngredients, excludedIngredients, searchText);

        return recipes.stream().map(this:: convertToDto).toList();
    }

    @Override
    public RecipeDto addRecipe(RecipeDto recipeDto) {
        var recipe = recipeRepository.save(convertToEntity(recipeDto));
        return convertToDto(recipe);
    }

    @Override
    public RecipeDto updateRecipe(long id,RecipeDto recipeDto) {
        var recipe = findRecipeById(id);
        mapper.map(recipeDto,recipe);
        var savedRecipe = recipeRepository.save(recipe);
        return convertToDto(savedRecipe);
    }

    @Override
    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    private Recipe findRecipeById(long id){
        return recipeRepository.findById(id).orElseThrow(()
                -> new RecipeNotFoundException("Recipe not found with id: " + id));
    }

    private Recipe convertToEntity(RecipeDto recipeDto) {
        return mapper.map(recipeDto, Recipe.class);
    }

    private RecipeDto convertToDto(Recipe recipe) {
        return mapper.map(recipe, RecipeDto.class);
    }
}
