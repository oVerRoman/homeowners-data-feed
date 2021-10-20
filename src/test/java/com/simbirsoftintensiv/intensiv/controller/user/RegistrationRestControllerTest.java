package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.UserTestData;
import com.simbirsoftintensiv.intensiv.entity.Role;
import com.simbirsoftintensiv.intensiv.exception_handling.NotFoundException;
import com.simbirsoftintensiv.intensiv.service.OtpService;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.simbirsoftintensiv.intensiv.controller.user.RegistrationRestController.REST_URL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationRestControllerTest extends AbstractUserRestControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private OtpService otpService;
    private static int otp;

    @BeforeEach
    void getOtp(){
        otpService.generateOTP(79999999999L);
        otp = otpService.getOtp(79999999999L);
    }

    @Test
    void register() throws Exception {
        int otPass = otp;
        CreateUserTo newUserTo = new CreateUserTo("79999999999", "qwe@asd.re", "fname",
                "sname", "pname", "city", "street", "house",
                "building", "apartment", Role.USER.toString(), otPass + "");

            UserTo registeredTo = UserTestData.asUserTo(perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(newUserTo)))
                    .andExpect(status().isCreated())
                    .andReturn());
            int newId = registeredTo.id();
            newUserTo.setId(newId);

            UserTestData.assertEquals(registeredTo,
                    UserUtil.asTo(userService.getByPhone(Long.parseLong(newUserTo.getPhone()))));
    }

    @Test
    void doubleRegisterForSamePhone() {
        int otPass = otp;
        CreateUserTo newUserTo = new CreateUserTo("79999999999", "qwe@asd.re", "fname",
                "sname", "pname", "city", "street", "house",
                "building", "apartment", Role.USER.toString(), otPass + "");

        userService.create(UserUtil.toEntity(newUserTo));
        assertThrows(NotFoundException.class, () -> userService.create(UserUtil.toEntity(newUserTo)));

    }

     /* @Test
    void registerWithWrongOtPassword() throws Exception {
        int otPass = otp - 1;
        CreateUserTo newUserTo = new CreateUserTo("79999999999", "qwe@asd.re", "fname",
                "sname", "pname", "city", "street", "house",
                "building", "apartment", "USER", otPass + "");

        assertThrows(BadCredentialsException.class, () -> perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserTo))));
    }*/

}