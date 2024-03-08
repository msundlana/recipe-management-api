package com.github.msundlana.recipemanagementservice;

import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.github.msundlana.recipemanagementservice.services.RecipeSearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
@ActiveProfiles(value = "test")
public class RecipeSearchServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeSearchService recipeSearchService = new RecipeSearchServiceImpl();

        @Test
        public void testNonFilteredRecipes() {

            var mockRecipes = getMockRecipes();

            when(recipeRepository.findFilteredRecipes(false,-1,
                    null, null,null)).thenReturn(mockRecipes);

            var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,null,
                    null, null,null);

            assertEquals(2, filteredRecipes.size());
        }

        @Test
        public void testFilteredIsVegetarianRecipes() {

            var mockRecipes = getMockRecipes();
            var isVegetarian = true;

            when(recipeRepository.findFilteredRecipes(isVegetarian,-1,
                    null, null,null)).thenReturn(List.of(mockRecipes.get(0)));

            var filteredRecipes = recipeSearchService.searchFilteredRecipes(isVegetarian,null,
                    null, null,null);

            assertEquals(1, filteredRecipes.size());
            assertEquals("Test Recipe 1", filteredRecipes.get(0).getName());
            assertTrue(filteredRecipes.get(0).isVegetarian());
        }

        @Test
        public void testFilteredRecipesByServings() {

            var mockRecipes = getMockRecipes();
            var servings = 4;

            when(recipeRepository.findFilteredRecipes(false,servings,
                    null, null,null)).thenReturn(List.of(mockRecipes.get(0)));

            var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,servings,
                    null, null,null);

            assertEquals(1, filteredRecipes.size());
            assertEquals("Test Recipe 1", filteredRecipes.get(0).getName());
            assertEquals(servings,filteredRecipes.get(0).getServings());

        }

    @Test
    public void testFilteredRecipesByInstruction() {

        var mockRecipes = getMockRecipes();
        var searchText = "oven";

        when(recipeRepository.findFilteredRecipes(false,-1,
                null, null,searchText)).thenReturn(List.of(mockRecipes.get(1)));

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,-1,
                null, null,searchText);

        assertEquals(1, filteredRecipes.size());
        assertEquals("Test Recipe 2", filteredRecipes.get(0).getName());
        assertTrue(filteredRecipes.get(0).getInstructions().contains(searchText));

    }

    @Test
    public void testFilteredRecipesByIncludedIngredients() {

        var mockRecipes = getMockRecipes();
        var includedIngredients = List.of("potato");

        when(recipeRepository.findFilteredRecipes(false,-1,
                includedIngredients , null,null)).thenReturn(mockRecipes);

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,-1,
                includedIngredients, null,null);

        assertEquals(2, filteredRecipes.size());
        assertEquals("Test Recipe 1", filteredRecipes.get(0).getName());
        assertEquals("Test Recipe 2", filteredRecipes.get(1).getName());

    }

    @Test
    public void testFilteredRecipesByIncludedIngredients_Empty() {

        var includedIngredients = List.of("potato");
        when(recipeRepository.findFilteredRecipes(false,-1,
                includedIngredients , null,null)).thenReturn(List.of());

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,-1,
                includedIngredients, null,null);

        assertEquals(0, filteredRecipes.size());
    }

    @Test
    public void testFilteredRecipesByExcludesIngredients() {

        var mockRecipes = getMockRecipes();
        var excludedIngredients = List.of("lamb");

        when(recipeRepository.findFilteredRecipes(false,-1,
                null, excludedIngredients,null)).thenReturn(List.of(mockRecipes.get(0)));

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,-1,
                null, excludedIngredients,null);

        assertEquals(1, filteredRecipes.size());
        assertEquals("Test Recipe 1", filteredRecipes.get(0).getName());
    }

    @Test
    public void testFilteredRecipesByExcludedIngredients_Empty() {
        var excludedIngredients = List.of("lamb");
        when(recipeRepository.findFilteredRecipes(false,-1,
                null, excludedIngredients,null)).thenReturn(List.of());

       var filteredRecipes = recipeSearchService.searchFilteredRecipes(false,-1,
               null, excludedIngredients,null);
        assertEquals(0, filteredRecipes.size());
    }

    @Test
    public void testFilteredRecipesByAllCriteria() {
        var servings = 4;
        var isVegetarian = true;
        var searchText = "oven";
        var includedIngredients = List.of("potato");
        var excludedIngredients = List.of("lamb");

        var mockRecipes = getMockRecipes();

        when(recipeRepository.findFilteredRecipes(isVegetarian,servings,
                includedIngredients, excludedIngredients,searchText)).thenReturn(List.of(mockRecipes.get(0)));

        var filteredRecipes = recipeSearchService.searchFilteredRecipes(isVegetarian,servings,
                includedIngredients, excludedIngredients,searchText);

        assertEquals(1, filteredRecipes.size());
        assertEquals("Test Recipe 1", filteredRecipes.get(0).getName());
        assertEquals(servings,filteredRecipes.get(0).getServings());
        assertTrue(filteredRecipes.get(0).isVegetarian());

    }


    private List<Recipe> getMockRecipes(){
            return List.of(Recipe.builder()
                            .id(1L)
                            .name("Test Recipe 1")
                            .servings(4)
                            .ingredients(List.of("potato","carrot"))
                            .instructions("Test instructions for test recipe cook on stove")
                            .vegetarian(true).build(),
                    Recipe.builder()
                            .id(2L)
                            .name("Test Recipe 2")
                            .ingredients(List.of("potato","carrot","lamb"))
                            .servings(2)
                            .instructions("Test instructions for test recipe roast in oven")
                            .vegetarian(false).build());

    }

}
