package com.cookbookbackend.controllers;

import com.cookbookbackend.models.Recipe;
import com.cookbookbackend.models.RecipeTime;
import com.cookbookbackend.repositories.RecipeRepository;
import com.cookbookbackend.repositories.RecipeTimeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000/", "https://cookbook-5qsd.onrender.com"})
@RestController
@RequestMapping("/api/v1")
public class RecipeTimeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeTimeRepository recipeTimeRepository;

    @GetMapping("/recipes/{recipeId}/time")
    public RecipeTime getRecipeTime(@PathVariable Integer recipeId) {
        if(!recipeTimeRepository.existsById(recipeId)) {
            //Throw exception
        }

        return recipeTimeRepository.findByRecipeId(recipeId);
    }

    @PostMapping("recipes/{recipeId}/time")
    public RecipeTime addRecipeTime(@PathVariable Integer recipeId, @RequestBody RecipeTime recipeTime) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Failed to get recipe"));
        recipeTime.setRecipe(recipe);
        return recipeTimeRepository.saveAndFlush(recipeTime);
    }

    @PutMapping("recipes/{recipeId}/time")
    public RecipeTime updateRecipeTime(@PathVariable Integer recipeId, @RequestBody RecipeTime recipeTime) {
        RecipeTime originalRecipeTime = recipeTimeRepository.findByRecipeId(recipeId);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Failed to get recipe"));
        recipeTime.setRecipe(recipe);
        BeanUtils.copyProperties(recipeTime, originalRecipeTime, "id");

        return recipeTimeRepository.saveAndFlush(originalRecipeTime);
    }

    @DeleteMapping("recipes/{recipeId}/time")
    public void deleteRecipeTime(@PathVariable Integer recipeId) {
        RecipeTime recipeTime = recipeTimeRepository.findByRecipeId(recipeId);
        if(recipeTime != null) {
            recipeTimeRepository.deleteById(recipeTime.getId());
        }
    }
}
