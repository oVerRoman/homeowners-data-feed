package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.exception_handling.NotFoundException;
import com.simbirsoftintensiv.intensiv.service.user.UserService;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.simbirsoftintensiv.intensiv.TestUtil.user;
import static com.simbirsoftintensiv.intensiv.controller.user.ProfileRestController.REST_URL;
import static com.simbirsoftintensiv.intensiv.controller.user.UserTestData.jsonMatcher;
import static com.simbirsoftintensiv.intensiv.controller.user.UserTestData.user_60000;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractUserRestControllerTest {

    @Autowired
    UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(user(user_60000)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonMatcher(UserUtil.asTo(user_60000), UserTestData::assertEquals));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update() throws Exception {
        UserTo updatedUserTo = new UserTo(60000, "79000000000", "qwe@asd.re", "updateFName",
                "sname", "pname", "city", "street", "house",
                "building", "apartment", "USER");

        User user = UserUtil.toEntity(updatedUserTo);

        perform(MockMvcRequestBuilders.put(REST_URL + "/" + "update")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(user_60000)).content(JsonUtil.writeValue(updatedUserTo)))
                        .andExpect(status().isNoContent())
                        .andDo(print());
        User updated = userService.getByPhone(79000000000L);
        UserTestData.assertEquals(user, updated);

    }

    @Test
    void updatePhone() throws Exception {
        UserTo updatedUserTo = new UserTo(60000, "79000000001", "qwe@asd.re", "updateFName",
                "sname", "pname", "city", "street", "house",
                "building", "apartment", "USER");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + "update")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(user_60000)).content(JsonUtil.writeValue(updatedUserTo)))
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void updateWithNotAuthId() throws Exception {
        UserTo updatedUserTo = new UserTo(60000, "79000000000", "qwe@asd.re", "updateFName",
                "sname", "pname", "city", "street", "house",
                "building", "apartment", "USER");
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + "update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedUserTo)))
                        .andExpect(status().isUnauthorized())
                        .andDo(print());

    }
}