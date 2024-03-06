package com.github.msundlana.recipemanagementservice.repositories;

import com.github.msundlana.recipemanagementservice.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r " +
            "WHERE (:vegetarian = false OR r.vegetarian = :vegetarian) " +
            "AND (:servings = -1 OR r.servings = :servings) " +
            "AND (COALESCE(:includedIngredients) IS NULL OR EXISTS (SELECT 1 FROM r.ingredients i WHERE i IN :includedIngredients)) " +
            "AND (COALESCE(:excludedIngredients) IS NULL OR NOT EXISTS (SELECT 1 FROM r.ingredients i WHERE i IN :excludedIngredients)) " +
            "AND (COALESCE(:searchText) IS NULL OR r.instructions LIKE %:searchText%)")
    List<Recipe> findFilteredRecipes(@Param("vegetarian") boolean vegetarian,
                                     @Param("servings") int servings,
                                     @Param("includedIngredients") List<String> includedIngredients,
                                     @Param("excludedIngredients") List<String> excludedIngredients,
                                     @Param("searchText") String searchText);
}

