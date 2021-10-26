package com.simbirsoftintensiv.intensiv.service.user;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.CompanyRepository;
import com.simbirsoftintensiv.intensiv.repository.user.CrudUserRepository;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import com.simbirsoftintensiv.intensiv.util.ValidationUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    public final CrudUserRepository userRepository;
    public final CompanyRepository companyRepository;
    public final OtpService otpService;

    public UserService(CrudUserRepository userRepository, CompanyRepository companyRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
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

    public User getByPhone(long phone) {
        return ValidationUtil.checkNotFoundWithPhone(userRepository.getByPhone(phone), phone);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User create(User user) {
        ValidationUtil.checkPhone(userRepository.getByPhone(user.getPhone()) != null);
        user.setCompany(companyRepository.getById(50000));// fixme заглушка
        return userRepository.save(user);
    }

    public void update(UserTo userTo, int authUserId) {
        ValidationUtil.checkIdEquality(userTo, authUserId);

        long phoneFromTo = Long.parseLong(userTo.getPhone());

        User user = getByPhone(phoneFromTo);
        //User cannot change phone number! Only the administrator can..
        ValidationUtil.checkPhoneEquality(phoneFromTo, user.getPhone());

        userRepository.save(UserUtil.updateFromTo(user, userTo));
    }

    public void delete(Long phone) {
        ValidationUtil.checkNotFoundWithPhone(userRepository.delete(phone), phone);
    }
}
