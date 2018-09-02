package com.example.springbootshop.user.repository;

import com.example.springbootshop.user.model.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ShopUser, String> {

    ShopUser findByUserId(String userId);

}
