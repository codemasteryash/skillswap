package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
