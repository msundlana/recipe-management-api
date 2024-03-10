package com.github.msundlana.recipemanagementservice.services;

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

import java.util.List;

@Service
public class RecipeSearchServiceImpl implements RecipeSearchService{
    private static final Logger logger = LoggerFactory.getLogger(RecipeSearchServiceImpl.class);
    @Autowired
    private RecipeRepository recipeRepository;
    @Override
    public Page<RecipeDto> searchFilteredRecipes(Boolean vegetarian, String searchText, Integer servings,
                                                 List<String> includedIngredients, List<String> excludedIngredients,
                                                 Pageable pageable) {

        logger.info("Searching filtered recipes with parameters: vegetarian={}, searchText={}, servings={}, " +
                        "includedIngredients={}, excludedIngredients={}, pageable={}", vegetarian, searchText, servings,
                includedIngredients, excludedIngredients, pageable);

        var filteredRecipes = recipeRepository.findFilteredRecipes(
                vegetarian, servings, includedIngredients, excludedIngredients, searchText, pageable);

        logger.info("Found {} filtered recipes", filteredRecipes.getTotalElements());

        return filteredRecipes.map(RecipeHelper::convertToDto);

    }
}
