package com.simbirsoftintensiv.intensiv.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.simbirsoftintensiv.intensiv.util.UserUtil;

public class MySimpleUrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    protected Log logger = LogFactory.getLog(this.getClass());


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        clearAuthenticationAttributes(request);
        Long phone = null;
        try {
            phone = Long.valueOf(authentication.getName());
        }catch (Exception e){
            logger.warn(e.getMessage() + " " + "MySimpleUrlAuthenticationSuccessHandler");
        }
        User user = userService.getByPhone(phone);
        UserTo userTo = UserUtil.asTo(user);
        response.getWriter().write(new ObjectMapper().writeValueAsString("{"+userTo.info() + ", role:" +authentication.getAuthorities()+"}"));
        response.setStatus(200);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.info(" Попытка входа. ");
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}