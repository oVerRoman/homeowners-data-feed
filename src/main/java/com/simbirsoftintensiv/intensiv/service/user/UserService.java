package com.simbirsoftintensiv.intensiv.service.user;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.user.CrudUserRepository;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    public final CrudUserRepository userRepository;
    public final OtpService otpService;

    public UserService(CrudUserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String phone) throws UsernameNotFoundException {
        System.out.println(phone);
        User user = userRepository.getByPhone(Long.parseLong(phone));

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AuthorizedUser(user);
    }

    public User getByPhone(Long phone) {//fixme del
        return userRepository.getByPhone(phone);
    }

    public List<User> getAll() {
        return  userRepository.getAll();
    }

    public User create(User user) {
        if (userRepository.getByPhone(user.getPhone()) != null) {
            return null;//fixme нужно исключение пользователь с таким телефоном уже существует
        }

        return userRepository.save(user);
    }

    public User save(User user) {
        if (userRepository.getByPhone(user.getPhone()) != null) {
            return null;//fixme нужно исключение пользователь с таким телефоном уже существует
        }
//        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        return userRepository.save(user);
    }

    public boolean delete(Long phone) {
        if (userRepository.getByPhone(phone) != null) {
            userRepository.delete(phone);
            return true;
        }
        return false;
    }
}
