package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.UserTestData;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.CreateUserTo;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.simbirsoftintensiv.intensiv.controller.user.RegistrationRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationRestControllerTest extends AbstractUserRestControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void register() throws Exception {
        CreateUserTo newUserTo = new CreateUserTo("79999999999", "qwe@asd.re", "fname",
                "sname", "pname", "city", "street", "house",
                "building", "apartment");

        UserTo registeredTo = UserTestData.asUserTo(perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserTo)))
                .andExpect(status().isCreated()).andReturn());
        int newId = registeredTo.id();
        newUserTo.setId(newId);

        UserTestData.assertEquals(registeredTo,
                UserUtil.asTo(userService.getByPhone(Long.parseLong(newUserTo.getPhone()))));
    }
}