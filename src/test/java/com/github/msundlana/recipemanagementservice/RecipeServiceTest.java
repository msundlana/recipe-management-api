package com.github.msundlana.recipemanagementservice;

import com.github.msundlana.recipemanagementservice.exception.RecipeNotFoundException;
import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import com.github.msundlana.recipemanagementservice.services.RecipeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.github.msundlana.recipemanagementservice.services.RecipeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles(value = "test")
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private RecipeService recipeService = new RecipeServiceImpl();


    @Test
    public void testGetRecipeById() {
        var recipeId = 1L;
        var recipe = createTestRecipe();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        var result = recipeService.getRecipeById(recipeId);

        assertNotNull(result);
        assertEquals("Test Recipe", result.getName());
        assertEquals(6, result.getServings());
        assertFalse(result.isVegetarian());
    }

    @Test
    public void testGetRecipeByInvalidId() {
        var recipeId = 1L;
        when(recipeRepository.findById(recipeId))
                .thenThrow(new RecipeNotFoundException("Recipe not found with id: " + recipeId));

        var exception = assertThrows(RecipeNotFoundException.class, ()->{
                recipeService.getRecipeById(recipeId);
        });
        assertTrue(exception.getMessage().contains("Recipe not found with id: " + recipeId));
    }

    @Test
    public void testGetAllRecipes() {
        var mockRecipes = List.of(Recipe.builder()
                .id(1L)
                .name("Test Recipe 1")
                .servings(4)
                .instructions("Test instructions for test recipe 1")
                .vegetarian(true).build(),
                Recipe.builder()
                .id(2L)
                .name("Test Recipe 2")
                .servings(2)
                .instructions("Test instructions for test recipe 2")
                .vegetarian(false).build());

        when(recipeRepository.findAll()).thenReturn(mockRecipes);

        var results = recipeService.getAllRecipes();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Test Recipe 1", results.get(0).getName());
        assertEquals("Test Recipe 2", results.get(1).getName());
    }

    @Test
    public void testAddRecipe() {
        var recipeDTO = new RecipeDto();
        recipeDTO.setName("Test Recipe");
        when(recipeRepository.save(any(Recipe.class))).thenReturn(createTestRecipe());

        var savedRecipe = recipeService.addRecipe(recipeDTO);

        assertNotNull(savedRecipe);
        assertEquals("Test Recipe", savedRecipe.getName());
    }

    @Test
    public void testDeleteRecipe() {
        var recipeId = 1L;
        recipeService.deleteRecipe(recipeId);

        assertFalse(recipeRepository.existsById(recipeId));

    }

    @Test
    public void testUpdateRecipe() {
        var recipeId = 1L;
        var existingRecipe = createTestRecipe();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(existingRecipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(existingRecipe);

        var updatedRecipeDto =  RecipeDto.builder()
                .id(recipeId)
                .name("Updated Recipe")
                .servings(4)
                .instructions("Test instructions for test recipe")
                .vegetarian(false)
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .build();

        var updatedRecipe = recipeService.updateRecipe(recipeId, updatedRecipeDto);

        assertNotNull(updatedRecipe);
        assertEquals("Updated Recipe", updatedRecipe.getName());
        assertTrue(4== updatedRecipe.getServings());
    }

    @Test
    public void testUpdateRecipeNotFound() {

        var recipeId = 1L;
        var updatedRecipeDto = new RecipeDto();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> recipeService.updateRecipe(recipeId, updatedRecipeDto));
    }

    private Recipe createTestRecipe() {
        var recipe = Recipe.builder()
                .id(1L)
                .name("Test Recipe")
                .servings(6)
                .instructions("Test instructions for test recipe")
                .vegetarian(false)
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .build();
        return recipe;
    }
}
