package com.simbirsoftintensiv.intensiv;

import com.simbirsoftintensiv.intensiv.controller.user.LoginController;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.repository.user.UserRepository;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    static final Logger log =
            LoggerFactory.getLogger(OtpAuthenticationProvider.class);
    private final UserRepository userRepository;
    private final OtpService otpService;

    public OtpAuthenticationProvider(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userRepository.getByPhone(Long.parseLong(userName))
                .orElseThrow(() -> new BadCredentialsException("Unknown user " + userName));

        String optPass = otpService.getOtp(Long.parseLong(userName)) + "";
         if (!password.equals(optPass)) {
            log.info("Неудачная попытка авторизации "+ userName+" .");
            throw new BadCredentialsException("Bad password");
        }
        log.info("User "+ userName+" authorized .");

        AuthorizedUser principal = new AuthorizedUser(user);

        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
