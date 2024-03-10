package com.github.msundlana.recipemanagementservice;

import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class RecipeSearchControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RecipeSearchService recipeSearchService;

    private final String basePath = "/api/recipes/search";

    @Test
    public void testFindRecipesWithNoFilters() throws Exception {
        var recipe = RecipeDto.builder()
                .id(1L)
                .name("Test Recipe 1")
                .servings(4)
                .ingredients(List.of("potato","carrot"))
                .instructions("Test instructions for test recipe cook on stove")
                .vegetarian(true).build();

        var page = new PageImpl<>(Collections.singletonList(recipe));

        when(recipeSearchService.searchFilteredRecipes(null
                , null, null, null, null, PageRequest.of(0, 20)))
                .thenReturn(page);

        mockMvc.perform(get(basePath+"/findFilteredRecipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.[0].id").value(1))
                .andExpect(jsonPath("$.content.[0].name").value("Test Recipe 1"));
    }

    @Test
    public void testFindFilteredRecipes() throws Exception {
        var recipe = RecipeDto.builder()
                .id(1L)
                .name("Test Recipe 1")
                .servings(4)
                .ingredients(List.of("potato","carrot"))
                .instructions("Test instructions for test recipe cook on stove")
                .vegetarian(true).build();

        var page = new PageImpl<>(Collections.singletonList(recipe));
        var servings = 4;
        var isVegetarian = true;
        var searchText = "instructions";

        when(recipeSearchService.searchFilteredRecipes(isVegetarian
                , searchText, servings, null, null, PageRequest.of(0, 20)))
                .thenReturn(page);

        mockMvc.perform(get(basePath+"/findFilteredRecipes")
                        .param("vegetarian", "true")
                        .param("searchText", "instructions")
                        .param("servings", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content.[0].id").value(1))
                .andExpect(jsonPath("$.content.[0].name").value("Test Recipe 1"));
    }

    @Test
    public void testFilteredRecipesByAllCriteria() throws Exception {
        var recipe = RecipeDto.builder()
                .id(1L)
                .name("Test Recipe 1")
                .servings(4)
                .ingredients(List.of("potato","carrot"))
                .instructions("Test instructions for test recipe cook on stove")
                .vegetarian(true).build();

        var page = new PageImpl<>(Collections.singletonList(recipe));

        when(recipeSearchService.searchFilteredRecipes(anyBoolean(), anyString(), anyInt(), anyList(), anyList(), any()))
                .thenReturn(page);

        mockMvc.perform(get(basePath+"/findFilteredRecipes")
                        .param("vegetarian", "true")
                        .param("searchText", "instructions")
                        .param("servings", "4")
                        .param("includedIngredients", "potato")
                        .param("excludedIngredients", "lamb")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content.[0].name").value("Test Recipe 1"));


    }

    @Test
    public void testFilteredRecipesCriteriaConstrainViolation() throws Exception {
        mockMvc.perform(get(basePath+ "/findFilteredRecipes?servings=0"))
                .andExpect(status().isBadRequest());
    }

}
