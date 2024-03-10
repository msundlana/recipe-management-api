package com.github.msundlana.recipemanagementservice.utilities;

import com.github.msundlana.recipemanagementservice.models.Recipe;
import com.github.msundlana.recipemanagementservice.models.RecipeDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;

public class RecipeHelper {

    private static final ModelMapper mapper = new ModelMapper();

    public static Recipe convertToEntity(RecipeDto recipeDto) {
        return mapper.map(recipeDto, Recipe.class);
    }

    public static RecipeDto convertToDto(Recipe recipe) {
        return mapper.map(recipe, RecipeDto.class);
    }

    public static PageRequest getPageRequest(int page, int size, String ... sort){
        var sorting  = Sort.unsorted();
        if(sort!=null){
            sorting = Sort.by(Sort.Direction.ASC, sort);
        }
        return PageRequest.of(page,size,sorting);
    }
}
