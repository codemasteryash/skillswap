package com.yashgupta.skillswap.controller;

import com.yashgupta.skillswap.dto.AddUserSkillRequest;
import com.yashgupta.skillswap.dto.CreateSkillRequest;
import com.yashgupta.skillswap.dto.UserSkillResponse;
import com.yashgupta.skillswap.entity.Skill;
import com.yashgupta.skillswap.service.SkillExchangeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillExchangeService skillExchangeService;

    public SkillController(SkillExchangeService skillExchangeService) {
        this.skillExchangeService = skillExchangeService;
    }

    @GetMapping
    public List<Skill> listSkills(@RequestParam(required = false) String q) {
        return skillExchangeService.getAllSkills(q);
    }

    @PostMapping
    public Skill createSkill(@Valid @RequestBody CreateSkillRequest request) {
        return skillExchangeService.createSkill(request);
    }

    @PostMapping("/listings")
    public UserSkillResponse addListing(@Valid @RequestBody AddUserSkillRequest request) {
        return skillExchangeService.addUserSkill(request);
    }

    @GetMapping("/listings")
    public List<UserSkillResponse> myListings(@RequestParam Long userId) {
        return skillExchangeService.getMyListings(userId);
    }

    @GetMapping("/teachers")
    public List<UserSkillResponse> teachersBySkill(@RequestParam Long skillId) {
        return skillExchangeService.getTeachersBySkill(skillId);
    }
}