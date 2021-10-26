package com.simbirsoftintensiv.intensiv.config.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;

@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException {
        AuthorizedUser authorizedUser = (AuthorizedUser) authentication.getPrincipal();
        UserTo authUserTo = authorizedUser.getUserTo();
        System.out.println(httpServletRequest.getSession().getId());
//        String cookie = httpServletResponse.getHeader("Set-Cookie");if (cookie != null)
//        { httpServletResponse.setHeader("Set-Cookie", cookie + "; HttpOnly; SameSite=strict");}
        httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=" + httpServletRequest.getSession().getId() + ";"
                +"Secure;" + " SameSite=None; Path=/; HttpOnly");
        System.out.println(httpServletResponse.getHeader("Set-Cookie"));
        String requestUrl = httpServletRequest.getHeader("Origin");
        System.out.println("onAuthenticationSuccess " + requestUrl);

        httpServletResponse.setHeader("Access-Control-Allow-Origin", requestUrl);
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.writeValue(authUserTo));
        httpServletResponse.setStatus(200);
    }
}