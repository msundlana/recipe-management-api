package com.github.msundlana.recipemanagementservice;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msundlana.recipemanagementservice.exception.RecipeNotFoundException;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RecipeService recipeService;

    private String basePath = "/api/recipes";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllRecipes() throws Exception {
        var recipes = List.of(
                RecipeDto.builder().name("Test Recipe 1")
                        .servings(4)
                        .instructions("Test instructions for test recipe 1")
                        .vegetarian(true).build(),

                RecipeDto.builder().name("Test Recipe 2")
                        .servings(2)
                        .instructions("Test instructions for test recipe 2")
                        .vegetarian(false).build()
        );

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        mockMvc.perform(get(basePath)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test Recipe 1"))
                .andExpect(jsonPath("$[1].name").value("Test Recipe 2"));
    }

    @Test
    public void testGetRecipeById() throws Exception {
        var recipeId = 1L;
        var recipe = RecipeDto.builder()
                .id(recipeId).name("Test Recipe")
                .servings(4)
                .instructions("Test instructions for test recipe")
                .vegetarian(true)
                .build();

        when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

        mockMvc.perform(get(basePath+"/" + recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Recipe"))
                .andExpect(jsonPath("$.servings").value(4))
                .andExpect(jsonPath("$.vegetarian").value(true));
    }

    @Test
    public void testGetRecipeByInvalidId() throws Exception {
        var recipeId = 1L;

        when(recipeService.getRecipeById(recipeId)).thenThrow(
                new RecipeNotFoundException("Recipe not found with id: " + recipeId));;

        mockMvc.perform(get(basePath+"/" + recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetRecipeByConstrainViolationId() throws Exception {
        var recipeId = 0L;

        mockMvc.perform(get(basePath+"/" + recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddRecipe() throws Exception {
        var recipe = RecipeDto.builder()
                .id(1L).name("Test Recipe")
                .servings(4)
                .instructions("Test instructions for test recipe")
                .vegetarian(true)
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .build();

        when(recipeService.addRecipe(recipe)).thenReturn(recipe);

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Test Recipe"))
                .andExpect(jsonPath("$.servings").value(4))
                .andExpect(jsonPath("$.vegetarian").value(true))
                .andExpect(jsonPath("$.ingredients.length()").value(2));

    }

    @Test
    public void testDeleteRecipe() throws Exception {
        var recipeId = 1L;

        mockMvc.perform(delete(basePath+"/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        var recipeId = 1L;

        var updatedRecipe = RecipeDto.builder()
                .id(recipeId).name("Updated Recipe")
                .servings(6)
                .instructions("Test instructions for test recipe")
                .vegetarian(false)
                .ingredients(List.of("Ingredient 1", "Ingredient 2"))
                .build();

        when(recipeService.updateRecipe(recipeId,updatedRecipe)).thenReturn(updatedRecipe);

        mockMvc.perform(put(basePath+"/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Updated Recipe"))
                .andExpect(jsonPath("$.servings").value(6))
                .andExpect(jsonPath("$.vegetarian").value(false))
                .andExpect(jsonPath("$.ingredients.length()").value(2));;

    }


}

