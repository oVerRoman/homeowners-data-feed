package com.simbirsoftintensiv.intensiv.repository.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.simbirsoftintensiv.intensiv.entity.User;

@Repository
public class DataJpaUserRepository implements CrudUserRepository {

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

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
