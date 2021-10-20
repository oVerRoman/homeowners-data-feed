package com.simbirsoftintensiv.intensiv.config.handler;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        clearAuthenticationAttributes(request);
        AuthorizedUser authorizedUser = (AuthorizedUser) authentication.getPrincipal();
        UserTo authUser = authorizedUser.getUserTo();
        HashMap userFoFront = authUser.info();
        String role = String.valueOf(authentication.getAuthorities());
        String roleForFront = role.substring(1, role.length() - 1);

        userFoFront.put("role", roleForFront);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.writeValue(userFoFront));
        response.setStatus(200);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            logger.info(" Попытка входа. ");
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}