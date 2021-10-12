package com.simbirsoftintensiv.intensiv.repository.user;

import com.simbirsoftintensiv.intensiv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

//здесь можно выполнять стандартные запросы к БД
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.phone=:phone")
    int delete(@Param("phone") Long phone);

    @Query("SELECT u FROM User u WHERE u.phone =:phone")
    User getByPhone(@Param("phone") Long phone);
}
