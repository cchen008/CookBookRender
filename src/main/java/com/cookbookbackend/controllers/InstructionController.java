package com.cookbookbackend.controllers;

import com.cookbookbackend.models.Instruction;
import com.cookbookbackend.models.Recipe;
import com.cookbookbackend.repositories.InstructionRepository;
import com.cookbookbackend.repositories.RecipeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/", "https://cookbook-5qsd.onrender.com"})
@RestController
@RequestMapping("/api/v1")
public class InstructionController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private InstructionRepository instructionRepository;

    @GetMapping("/recipes/{recipeId}/instructions")
    public List<Instruction> getRecipeInstructions(@PathVariable Integer recipeId) {
        if(!instructionRepository.existsById(recipeId)) {
            //Throw exception
        }

        return instructionRepository.findByRecipeIdOrderByIdAsc(recipeId);
    }

    @PostMapping("/recipes/{recipeId}/instructions")
    public Instruction addRecipeInstruction(@PathVariable Integer recipeId, @RequestBody Instruction instruction) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Failed to get recipe"));
        instruction.setRecipe(recipe);
        return instructionRepository.saveAndFlush(instruction);
    }

    @PutMapping("/recipes/{recipeId}/instructions/{instructionId}")
    public Instruction updateRecipeInstruction(@PathVariable Integer recipeId, @PathVariable Integer instructionId, @RequestBody Instruction instruction) {
        Instruction originalInstruction = instructionRepository.findById(instructionId).orElseThrow(() -> new RuntimeException("Error getting instruction"));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Error getting recipe"));
        instruction.setRecipe(recipe);
        BeanUtils.copyProperties(instruction, originalInstruction, "id");

        return instructionRepository.saveAndFlush(originalInstruction);
    }

    @DeleteMapping("/recipes/{recipeId}/instructions/{instructionId}")
    public void deleteRecipeInstruction(@PathVariable Integer instructionId) {
        instructionRepository.deleteById(instructionId);
    }

    @DeleteMapping("/recipes/{recipeId}/instructions")
    public void deleteAllInstructionsByRecipeId(@PathVariable Integer recipeId) {
        List<Instruction> recipeInstructions = getRecipeInstructions(recipeId);

        for(Instruction instruction: recipeInstructions) {
            instructionRepository.deleteById(instruction.getId());
        }
    }

}
