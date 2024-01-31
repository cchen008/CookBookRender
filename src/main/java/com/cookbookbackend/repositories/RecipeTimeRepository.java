package com.cookbookbackend.repositories;

import com.cookbookbackend.models.RecipeTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeTimeRepository extends JpaRepository<RecipeTime, Integer> {
    RecipeTime findByRecipeId(Integer recipeId);
}
