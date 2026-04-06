package com.yashgupta.skillswap.service;

import com.yashgupta.skillswap.entity.Role;
import com.yashgupta.skillswap.entity.User;
import com.yashgupta.skillswap.repository.UserRepository;
import com.yashgupta.skillswap.repository.UserSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> findBySkill(String skillName) {
        return userSkillRepository.findUsersBySkillName(skillName);
    }

    public User getByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }
    public void requireAdmin(Long userId) {
        User user = getByIdOrThrow(userId);
        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied: ADMIN role required");
        }
    }
}
