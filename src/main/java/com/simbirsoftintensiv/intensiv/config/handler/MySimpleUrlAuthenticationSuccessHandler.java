package com.simbirsoftintensiv.intensiv.config.handler;

import com.simbirsoftintensiv.intensiv.AuthorizedUser;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    protected Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        AuthorizedUser authorizedUser = (AuthorizedUser) authentication.getPrincipal();
        UserTo authUserTo = authorizedUser.getUserTo();
        System.out.println(httpServletRequest.getHeader("Origin"));
        String requestUrl =  httpServletRequest.getHeader("Origin");
        System.out.println("onAuthenticationSuccess "+requestUrl);

        httpServletResponse.setHeader("Access-Control-Allow-Origin", requestUrl);
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("SameSite=strict","secure");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JsonUtil.writeValue(authUserTo));
        httpServletResponse.setStatus(200);
    }
}