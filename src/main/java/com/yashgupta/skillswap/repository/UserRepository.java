package com.yashgupta.skillswap.repository;

import com.yashgupta.skillswap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
