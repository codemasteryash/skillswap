package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.dto.AddUserSkillRequest;
import com.yashgupta.skillswap.dto.CreateSkillRequest;
import com.yashgupta.skillswap.dto.UserSkillResponse;
import com.yashgupta.skillswap.entity.Skill;
import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.entity.UserSkill;
import com.yashgupta.skillswap.repository.SkillRepository;
import com.yashgupta.skillswap.repository.UserRepository;
import com.yashgupta.skillswap.repository.UserSkillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillExchangeService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    public SkillExchangeService(
            SkillRepository skillRepository,
            UserRepository userRepository,
            UserSkillRepository userSkillRepository
    ) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
        this.userSkillRepository = userSkillRepository;
    }

    public List<Skill> getAllSkills(String q) {
        if (q == null || q.isBlank()) return skillRepository.findAll();
        return skillRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(q, q);
    }

    @Transactional
    public Skill createSkill(CreateSkillRequest req) {
        Skill s = new Skill();
        s.setName(req.getName().trim());
        s.setCategory(req.getCategory().trim());
        s.setPricePerHour(req.getPricePerHour());
        return skillRepository.save(s);
    }

    @Transactional
    public UserSkillResponse addUserSkill(AddUserSkillRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = skillRepository.findById(req.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        if (userSkillRepository.existsByUserUserIdAndSkillId(user.getUserId(), skill.getId())) {
            throw new RuntimeException("You already listed this skill");
        }

        UserSkill us = new UserSkill();
        us.setUser(user);
        us.setSkill(skill);
        us.setProficiencyLevel(req.getProficiencyLevel());

        return toResponse(userSkillRepository.save(us));
    }

    public List<UserSkillResponse> getMyListings(Long userId) {
        return userSkillRepository.findByUserUserId(userId).stream().map(this::toResponse).toList();
    }

    public List<UserSkillResponse> getTeachersBySkill(Long skillId) {
        return userSkillRepository.findBySkillId(skillId).stream().map(this::toResponse).toList();
    }

    private UserSkillResponse toResponse(UserSkill us) {
        return UserSkillResponse.builder()
                .id(us.getId())
                .userId(us.getUser().getUserId())
                .username(us.getUser().getUsername())
                .skillId(us.getSkill().getId())
                .skillName(us.getSkill().getName())
                .category(us.getSkill().getCategory())
                .pricePerHour(us.getSkill().getPricePerHour())
                .proficiencyLevel(us.getProficiencyLevel())
                .build();
    }
}