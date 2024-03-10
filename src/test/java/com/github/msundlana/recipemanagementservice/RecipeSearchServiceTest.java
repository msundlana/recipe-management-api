package com.github.msundlana.recipemanagementservice;

import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.repositories.RecipeRepository;
import com.github.msundlana.recipemanagementservice.services.RecipeSearchService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.github.msundlana.recipemanagementservice.services.RecipeSearchServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
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

        var page = new PageImpl<>(mockRecipes);

        when(recipeRepository.findFilteredRecipes(
                null, null, null, null,null,PageRequest.of(0, 20)))
                .thenReturn(page);
        var result = recipeSearchService.searchFilteredRecipes(null
                , null, null, null, null, PageRequest.of(0, 20));
        assertNotNull(result.getContent());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindFilteredRecipes() {
        var recipes = Collections.singletonList(getMockRecipes().get(0));
        var page = new PageImpl<>(recipes);

        when(recipeRepository.findFilteredRecipes(
                anyBoolean(), anyInt(), anyList(), anyList(),anyString(),any()))
                .thenReturn(page);

        var result = recipeSearchService.searchFilteredRecipes(
                true, "instructions", 4, Collections.emptyList(), Collections.emptyList(), PageRequest.of(0, 20));

        assertNotNull(result.getContent());
        assertEquals(recipes.size(), result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("Test Recipe 1", result.getContent().get(0).getName());
    }

    @Test
    public void testFilteredIsVegetarianRecipes() {
        var isVegetarian = true;
        var mockRecipes = Collections.singletonList(getMockRecipes().get(0));
        var page = new PageImpl<>(mockRecipes);

        when(recipeRepository.findFilteredRecipes(
                isVegetarian, null, null, null,null,PageRequest.of(0, 20)))
                .thenReturn(page);
        var result = recipeSearchService.searchFilteredRecipes(isVegetarian
                , null, null, null, null, PageRequest.of(0, 20));

        assertNotNull(result.getContent());
        assertEquals(1, result.getContent().size());
        assertEquals("Test Recipe 1", result.getContent().get(0).getName());
        assertTrue(result.getContent().get(0).isVegetarian());
    }

    @Test
    public void testFilteredRecipesByServings() {

    var servings = 4;
        var mockRecipes = Collections.singletonList(getMockRecipes().get(0));
        var page = new PageImpl<>(mockRecipes);

        when(recipeRepository.findFilteredRecipes(
                null, servings, null, null,null,PageRequest.of(0, 20)))
                .thenReturn(page);
        var result = recipeSearchService.searchFilteredRecipes(null
                , null, servings, null, null, PageRequest.of(0, 20));

        assertNotNull(result.getContent());
        assertEquals(1, result.getContent().size());
        assertEquals("Test Recipe 1", result.getContent().get(0).getName());
        assertEquals(servings,result.getContent().get(0).getServings());

    }

@Test
public void testFilteredRecipesByInstruction() {

    var searchText = "oven";
    var mockRecipes = Collections.singletonList(getMockRecipes().get(1));
    var page = new PageImpl<>(mockRecipes);

    when(recipeRepository.findFilteredRecipes(
            null, null, null, null,searchText,PageRequest.of(0, 20)))
            .thenReturn(page);
    var result = recipeSearchService.searchFilteredRecipes(null
            , searchText, null, null, null, PageRequest.of(0, 20));

    assertNotNull(result.getContent());
    assertEquals("Test Recipe 2", result.getContent().get(0).getName());
    assertTrue(result.getContent().get(0).getInstructions().contains(searchText));

}

@Test
public void testFilteredRecipesByIncludedIngredients() {

    var mockRecipes = getMockRecipes();
    var includedIngredients = List.of("potato");
    var page = new PageImpl<>(mockRecipes);

    when(recipeRepository.findFilteredRecipes(
            null, null, includedIngredients, null,null,PageRequest.of(0, 20)))
            .thenReturn(page);
    var result = recipeSearchService.searchFilteredRecipes(null
            , null, null, includedIngredients, null, PageRequest.of(0, 20));

    assertNotNull(result.getContent());
    assertEquals(2, result.getContent().size());
    assertEquals("Test Recipe 1", result.getContent().get(0).getName());
    assertEquals("Test Recipe 2", result.getContent().get(1).getName());

}

@Test
public void testFilteredRecipesByIncludedIngredients_Empty() {

    var includedIngredients = List.of("potato");
    var page = new PageImpl<>(Collections.EMPTY_LIST);

    when(recipeRepository.findFilteredRecipes(
            null, null, includedIngredients, null,null,PageRequest.of(0, 20)))
            .thenReturn(page);
    var result = recipeSearchService.searchFilteredRecipes(null
            , null, null, includedIngredients, null, PageRequest.of(0, 20));

    assertNotNull(result.getContent());
    assertEquals(0, result.getContent().size());
}

@Test
public void testFilteredRecipesByExcludesIngredients() {

    var mockRecipes = getMockRecipes();
    var excludedIngredients = List.of("lamb");

    var page = new PageImpl<>(Collections.singletonList(mockRecipes.get(0)));

    when(recipeRepository.findFilteredRecipes(
            null, null, null, excludedIngredients,null,PageRequest.of(0, 20)))
            .thenReturn(page);
    var result = recipeSearchService.searchFilteredRecipes(null
            , null, null, null, excludedIngredients, PageRequest.of(0, 20));

    assertNotNull(result.getContent());
    assertEquals(1, result.getContent().size());
    assertEquals("Test Recipe 1", result.getContent().get(0).getName());
}

@Test
public void testFilteredRecipesByExcludedIngredients_Empty() {
    var excludedIngredients = List.of("lamb");
    var page = new PageImpl<>(Collections.EMPTY_LIST);

    when(recipeRepository.findFilteredRecipes(
            null, null, null, excludedIngredients,null,PageRequest.of(0, 20)))
            .thenReturn(page);
    var result = recipeSearchService.searchFilteredRecipes(null
            , null, null, null, excludedIngredients, PageRequest.of(0, 20));

    assertNotNull(result.getContent());
    assertEquals(0, result.getContent().size());
}

@Test
public void testFilteredRecipesByAllCriteria() {
    var servings = 4;
    var isVegetarian = true;
    var searchText = "stove";
    var includedIngredients = List.of("potato","carrot");
    var excludedIngredients = List.of("lamb");

    var mockRecipes = getMockRecipes();
    var page = new PageImpl<>(Collections.singletonList(mockRecipes.get(0)));

    when(recipeRepository.findFilteredRecipes(
            anyBoolean(), anyInt(), anyList(), anyList(),anyString(),any()))
            .thenReturn(page);
    var result = recipeSearchService.searchFilteredRecipes(isVegetarian
            , searchText, servings, includedIngredients, excludedIngredients, PageRequest.of(0, 20));

    assertNotNull(result.getContent());
    assertEquals(1, result.getContent().size());
    assertEquals("Test Recipe 1", result.getContent().get(0).getName());
    assertEquals(servings,result.getContent().get(0).getServings());
    assertTrue(result.getContent().get(0).isVegetarian());

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
