package com.simbirsoftintensiv.intensiv.controller.user;

import com.simbirsoftintensiv.intensiv.UserTestData;
import com.simbirsoftintensiv.intensiv.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.simbirsoftintensiv.intensiv.TestUtil.user;
import static com.simbirsoftintensiv.intensiv.UserTestData.jsonMatcher;
import static com.simbirsoftintensiv.intensiv.UserTestData.user_60000;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractUserRestControllerTest {

    private static final String REST_URL = "/rest/profile";

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(user(user_60000)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(UserUtil.asTo(user_60000), UserTestData::assertEquals))
        ;
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}