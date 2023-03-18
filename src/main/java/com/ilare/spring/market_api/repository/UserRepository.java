package com.ilare.spring.market_api.repository;

import com.ilare.spring.market_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByNickname(String nickname);
    public User findByEmail(String email);
}
