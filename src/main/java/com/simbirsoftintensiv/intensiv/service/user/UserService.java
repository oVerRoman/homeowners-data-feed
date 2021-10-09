package com.simbirsoftintensiv.intensiv.service.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.user.CrudUserRepository;
import com.simbirsoftintensiv.intensiv.service.OtpService;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em; // запрос к БД
    @Autowired
    CrudUserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public OtpService otpService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        System.out.println(phone);
        User user = userRepository.getByPhone(Long.parseLong(phone));

        if (user == null) {
            throw new UsernameNotFoundException("User with phone " + phone + " not found");
        }

        return user;
    }

    public boolean haveLoginInDB(String phone) {
        // находим Юзера в БД
        User userFromDB = userRepository.getByPhone(Long.parseLong(phone));
        System.out.println("checkLogin " + phone);
        if (userFromDB == null) {
            return false;
        } else {
// получаем пароль с кэша
            int serverPassword = otpService.getOtp(phone);
            System.out.println("serverPassword " + serverPassword);
// Юзеру закидываем пароль
            userFromDB.setPassword(bCryptPasswordEncoder.encode(String.valueOf(serverPassword)));
            // и сохраняем старого Юзера в БД
            userRepository.save(userFromDB);

            return true;
        }
    }

    public User get(int userId) {
        return userRepository.get(userId);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User save(User user) {

        if (userRepository.getByPhone(user.getPhone()) != null) {
            return null;// fixme нужно исключение пользователь с таким телефоном уже существует
        }

//        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // сохраняем в БД
        return userRepository.save(user);
    }

    public boolean delete(Integer userId) {
        if (userRepository.get(userId) != null) {
            userRepository.delete(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
