package com.github.msundlana.recipemanagementservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {

    private Long id;

    private String name;

    private List<String> ingredients;

    private String instructions;

    private int servings;

    private boolean vegetarian;
}
