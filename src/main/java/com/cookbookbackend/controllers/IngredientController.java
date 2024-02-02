package com.cookbookbackend.controllers;

import com.cookbookbackend.models.Ingredient;
import com.cookbookbackend.models.Recipe;
import com.cookbookbackend.repositories.IngredientRepository;
import com.cookbookbackend.repositories.RecipeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000/", "https://cookbook-5qsd.onrender.com"})
@RestController
@RequestMapping("/api/v1")
public class IngredientController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping("/recipes/{recipeId}/ingredients")
    public List<Ingredient> getAllIngredientsByRecipeId(@PathVariable Integer recipeId) {
        if(!ingredientRepository.existsById(recipeId)) {
            //Throw exception
        }

        return ingredientRepository.findAllByRecipeId(recipeId);
    }

    @GetMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public Ingredient getIngredient(@PathVariable Integer recipeId, @PathVariable Integer ingredientId) {
        return ingredientRepository.findById(ingredientId).orElseThrow(() -> new RuntimeException("Failed to get ingredient"));
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public void deleteIngredient(@PathVariable Integer ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients")
    public void deleteAllIngredientsByRecipeId(@PathVariable Integer recipeId) {
        List<Ingredient> ingredients = getAllIngredientsByRecipeId(recipeId);

        for(Ingredient ingredient: ingredients) {
            ingredientRepository.deleteById(ingredient.getId());
        }
    }

    @PostMapping("/recipes/{recipeId}/ingredients")
    public Ingredient addOneIngredientByRecipeId(@PathVariable Integer recipeId, @RequestBody Ingredient ingredientRequest) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Failed to add ingredients by recipe"));
        ingredientRequest.setRecipe(recipe);
        return ingredientRepository.saveAndFlush(ingredientRequest);
    }

    @PutMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public Ingredient updateIngredient(@PathVariable Integer recipeId, @PathVariable Integer ingredientId, @RequestBody Ingredient ingredient) {
        Ingredient originalIngredient = ingredientRepository.findById(ingredientId).orElseThrow(() -> new RuntimeException("Failed to get ingredient"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Failed to get recipe"));
        ingredient.setRecipe(recipe);
        BeanUtils.copyProperties(ingredient, originalIngredient, "id");

        return ingredientRepository.saveAndFlush(originalIngredient);
    }

}
