package com.simbirsoftintensiv.intensiv.service;

import com.simbirsoftintensiv.intensiv.entity.Role;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.RoleRepository;
import com.simbirsoftintensiv.intensiv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em; //запрос к БД
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public OtpService otpService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userRepository.findByUsername(username);

        if (user == null) {

            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }


    public boolean haveLoginInDB(String username) {
        //находим Юзера в БД
        User userFromDB = userRepository.findByUsername(username);
        System.out.println("checkLogin " + username);
        if (userFromDB == null) {
            return false;
        } else {
// получаем пароль с кэша
            int serverPassword = otpService.getOtp(username);
            System.out.println("serverPassword " + serverPassword);
// Юзеру закидываем пароль
            userFromDB.setPassword(bCryptPasswordEncoder.encode(String.valueOf(serverPassword)));
            // и сохраняем старого Юзера в БД
            userRepository.save(userFromDB);

        return true;
        }
    }


    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

       // сохраняем в БД
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
