package com.cookbookbackend.repositories;

import com.cookbookbackend.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findAllByRecipeId(Integer recipeId);
    Ingredient findByRecipeId(Integer recipeId);
}
