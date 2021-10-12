package com.simbirsoftintensiv.intensiv;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.user.UserRepository;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final OtpService otpService;

    public OtpAuthenticationProvider(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
            System.out.println("authenticate userName - " +userName);
        String password = authentication.getCredentials().toString();
          System.out.println("authenticate password- " + password);
        User user = userRepository.getByPhone(Long.parseLong(userName));
        System.out.println(user);
        if (user == null) {
            throw new BadCredentialsException("Unknown user " + userName);
        }
        String optPass = otpService.getOtp(Long.parseLong(userName)) +"";
        System.out.println("optPass" + optPass);
        if (!password.equals(optPass)) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(user.getPhone().toString())
                .password(otpService.getOtp(Long.parseLong(userName)) + "")
                .authorities(user.getRoles())
                .build();
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
