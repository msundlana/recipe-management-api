package com.github.msundlana.recipemanagementservice.repositories;

import com.github.msundlana.recipemanagementservice.models.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r " +
            "WHERE (COALESCE(:vegetarian) IS NULL OR r.vegetarian = :vegetarian) " +
            "AND (COALESCE(:servings) IS NULL OR r.servings = :servings) " +
            "AND (COALESCE(:includedIngredients) IS NULL OR EXISTS (SELECT 1 FROM r.ingredients i WHERE i IN :includedIngredients)) " +
            "AND (COALESCE(:excludedIngredients) IS NULL OR NOT EXISTS (SELECT 1 FROM r.ingredients i WHERE i IN :excludedIngredients)) " +
            "AND (COALESCE(:searchText) IS NULL OR r.instructions LIKE %:searchText%)")
    Page<Recipe> findFilteredRecipes(@Param("vegetarian") Boolean vegetarian,
                                     @Param("servings") Integer servings,
                                     @Param("includedIngredients") List<String> includedIngredients,
                                     @Param("excludedIngredients") List<String> excludedIngredients,
                                     @Param("searchText") String searchText,Pageable pageable);

}

