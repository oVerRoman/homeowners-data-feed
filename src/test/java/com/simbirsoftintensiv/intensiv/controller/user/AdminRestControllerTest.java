package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.UserTestData;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.simbirsoftintensiv.intensiv.TestUtil.user;
import static com.simbirsoftintensiv.intensiv.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestControllerTest extends AbstractUserRestControllerTest {

    private static final String REST_URL = "/rest/admin/users";

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(user(admin_60002)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonListMatcher(UserUtil.asTos(users), UserTestData::assertListEquals));
    }

    @Test
    void getAllWithUserRole() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(user(user_60001)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL+ "/" + "79000000000"))
                .andExpect(status().isUnauthorized())
                .andDo(print())
        ;
    }

    @Test
    void delete() {
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + "79000000000")
                .with(user(admin_60002)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(UserUtil.asTo(user_60000), UserTestData::assertEquals))
        ;
    }
}