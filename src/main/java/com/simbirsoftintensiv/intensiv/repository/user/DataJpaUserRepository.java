package com.simbirsoftintensiv.intensiv.repository.user;

import com.simbirsoftintensiv.intensiv.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaUserRepository implements CrudUserRepository{

    private final UserRepository userRepository;

    public DataJpaUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return userRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByPhone(Long phone) {
        return userRepository.getByPhone(phone);
    }
}
