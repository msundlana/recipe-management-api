package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.exception.RecipeNotFoundException;
import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import com.github.msundlana.recipemanagementservice.utilities.RecipeHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService{

    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    private RecipeRepository recipeRepository;
    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        logger.info("Retrieving all recipes with pagination: {}", pageable);
        var recipes = recipeRepository.findAll(pageable);
        logger.info("Retrieved {} recipes", recipes.getTotalElements());
        return recipes.map(RecipeHelper::convertToDto);
    }

    @Override
    public RecipeDto getRecipeById(Long id) {
        logger.info("Retrieving recipe by ID: {}", id);
        var recipe = findRecipeById(id);
        logger.info("Recipe found: {}", recipe);
        return RecipeHelper.convertToDto(recipe);
    }


    @Override
    public RecipeDto addRecipe(RecipeDto recipeDto) {
        logger.info("Adding new recipe: {}", recipeDto);
        var recipe = recipeRepository.save(RecipeHelper.convertToEntity(recipeDto));
        logger.info("Recipe added: {}", recipe);
        return RecipeHelper.convertToDto(recipe);
    }

    @Override
    public RecipeDto updateRecipe(long id,RecipeDto recipeDto) {
        logger.info("Updating recipe with ID {}: {}", id, recipeDto);
        var recipe = findRecipeById(id);
        mapper.map(recipeDto, recipe);
        var savedRecipe = recipeRepository.save(recipe);
        logger.info("Recipe updated: {}", savedRecipe);
        return RecipeHelper.convertToDto(savedRecipe);
    }

    @Override
    public void deleteRecipe(long id) {
        logger.info("Deleting recipe with ID: {}", id);
        recipeRepository.deleteById(id);
        logger.info("Recipe deleted with ID: {}", id);
    }

    private Recipe findRecipeById(long id){
        return recipeRepository.findById(id).orElseThrow(()
                -> new RecipeNotFoundException("Recipe not found with id: " + id));
    }

}
