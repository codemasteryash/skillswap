package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}