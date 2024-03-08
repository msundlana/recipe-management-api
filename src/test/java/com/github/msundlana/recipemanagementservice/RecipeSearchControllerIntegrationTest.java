package com.github.msundlana.recipemanagementservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class RecipeSearchControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RecipeSearchService recipeSearchService;

    private String basePath = "/api/recipes";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFilteredRecipesByAllCriteria() throws Exception {
        var recipe = RecipeDto.builder()
                .name("Test Recipe 1")
                .servings(4)
                .ingredients(List.of("potato","carrot"))
                .instructions("Test instructions for test recipe cook on stove")
                .vegetarian(true).build();

        when(recipeSearchService.searchFilteredRecipes(any(Boolean.class), any(Integer.class), any(List.class), any(List.class), any(String.class)))
                .thenReturn(List.of(recipe));

        mockMvc.perform(get(basePath+ "/search/findFilteredRecipes?servings=2&vegetarian=true" +
                        "&includedIngredients=potato&excludedIngredients=lamb&searchText=oven"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Recipe 1"));

    }

    @Test
    public void testFilteredRecipesByAllCriteriaConstrainViolation() throws Exception {
        mockMvc.perform(get(basePath+ "/search/findFilteredRecipes?servings=0"))
                .andExpect(status().isBadRequest());
    }

}
