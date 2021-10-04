package com.simbirsoftintensiv.intensiv.repository;

import com.simbirsoftintensiv.intensiv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//здесь можно выполнять стандартные запросы к БД
public interface UserRepository extends JpaRepository<User, Long> {
    // ищем имя юзера в бд
    User findByUsername(String username);
}
