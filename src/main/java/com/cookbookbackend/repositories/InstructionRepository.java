package com.cookbookbackend.repositories;

import com.cookbookbackend.models.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructionRepository extends JpaRepository<Instruction, Integer> {
    public List<Instruction> findByRecipeId(Integer recipeId);
    public List<Instruction> findByRecipeIdOrderByIdAsc(Integer recipeId);
}
