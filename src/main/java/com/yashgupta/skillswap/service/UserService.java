package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    public List<User> findBySkill(String skillName) {
        return userSkillRepository.findUsersBySkillName(skillName);
    }
}
