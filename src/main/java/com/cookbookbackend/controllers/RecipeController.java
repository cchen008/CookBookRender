package com.cookbookbackend.controllers;

import com.cookbookbackend.models.Recipe;
import com.cookbookbackend.models.RecipeTime;
import com.cookbookbackend.repositories.RecipeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    IngredientController ingredientController = new IngredientController();
    @Autowired
    InstructionController instructionController = new InstructionController();
    @Autowired
    RecipeTimeController recipeTimeController = new RecipeTimeController();

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public Optional<Recipe> getRecipe(@PathVariable Integer id) {
        return recipeRepository.findById(id);
    }

    @PostMapping("/recipes")
    public Recipe createRecipe(@RequestBody final Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    @DeleteMapping("/recipes/{id}")
    public void removeRecipe(@PathVariable Integer id) {
        recipeTimeController.deleteRecipeTime(id);
        ingredientController.deleteAllIngredientsByRecipeId(id);
        instructionController.deleteAllInstructionsByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    @PutMapping("/recipes/{id}")
    public Recipe editRecipe(@PathVariable Integer id, @RequestBody Recipe recipe) {
        Recipe originalRecipe = recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Failed to get original recipe"));
        BeanUtils.copyProperties(recipe, originalRecipe, "id");
        return recipeRepository.saveAndFlush(originalRecipe);
    }

}
