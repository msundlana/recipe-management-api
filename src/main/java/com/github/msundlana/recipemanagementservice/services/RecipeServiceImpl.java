package com.github.msundlana.recipemanagementservice.services;

import com.github.msundlana.recipemanagementservice.exception.RecipeNotFoundException;
import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import com.github.msundlana.recipemanagementservice.utilities.RecipeHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{

    @Autowired
    private RecipeRepository recipeRepository;
    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(RecipeHelper::convertToDto);
    }

    @Override
    public RecipeDto getRecipeById(Long id) {
        var recipe = findRecipeById(id);
        return RecipeHelper.convertToDto(recipe);
    }


    @Override
    public RecipeDto addRecipe(RecipeDto recipeDto) {
        var recipe = recipeRepository.save(RecipeHelper.convertToEntity(recipeDto));
        return RecipeHelper.convertToDto(recipe);
    }

    @Override
    public RecipeDto updateRecipe(long id,RecipeDto recipeDto) {
        var recipe = findRecipeById(id);
        mapper.map(recipeDto,recipe);
        var savedRecipe = recipeRepository.save(recipe);
        return RecipeHelper.convertToDto(savedRecipe);
    }

    @Override
    public void deleteRecipe(long id) {
        recipeRepository.deleteById(id);
    }

    private Recipe findRecipeById(long id){
        return recipeRepository.findById(id).orElseThrow(()
                -> new RecipeNotFoundException("Recipe not found with id: " + id));
    }

}
