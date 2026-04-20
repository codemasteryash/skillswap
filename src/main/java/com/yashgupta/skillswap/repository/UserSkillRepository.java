package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    @Query("SELECT us.user FROM UserSkill us WHERE us.skill.name = :skillName")
    List<User> findUsersBySkillName(@Param("skillName") String skillName);

    List<UserSkill> findByUserUserId(Long userId);

    List<UserSkill> findBySkillId(Long skillId);

    boolean existsByUserUserIdAndSkillId(Long userId, Long skillId);
}