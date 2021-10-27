package com.simbirsoftintensiv.intensiv.controller.request;

import static com.simbirsoftintensiv.intensiv.TestUtil.user;
import static com.simbirsoftintensiv.intensiv.controller.RequestController.REST_URL;
import static com.simbirsoftintensiv.intensiv.controller.user.UserTestData.user_60000;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.simbirsoftintensiv.intensiv.entity.Request;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;

class RequestControllerTest extends AbstractRequestControllerTest {

    @Test
    void get() throws Exception {

        perform(MockMvcRequestBuilders.get(REST_URL + "/" + "80000")
                .with(user(user_60000)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUnAuth() throws Exception {

        perform(MockMvcRequestBuilders.get(REST_URL + "/" + "80002")
                .with(user(user_60000)))
                        .andExpect(status().isUnauthorized())
                        .andDo(print());
    }

    @Test
    void getRequestCount() throws Exception {

        perform(MockMvcRequestBuilders.get(REST_URL + "/" + "count")
                .with(user(user_60000)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(RequestTestData.jsonMatcher(
                                JsonUtil.readValue(RequestTestData.numberOfUser60000Requests,
                                        String.class),
                                (actual, expected) -> assertThat(actual).isEqualTo(expected)));
    }

    @Test
    void create() throws Exception {

        Request newRequest = new Request(100000, "Новая тестовая заявка", null, 1, "Комментарий", 1,
                60000, "file path");

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(user(user_60000))
                .content(JsonUtil.writeValue(newRequest)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn();
    }

    @Test
    void createUnAuth() throws Exception {

        Request newRequest = new Request(100000, "Новая тестовая заявка", null, 1, "Комментарий", 1,
                60000, "file path");

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRequest)))
                        .andExpect(status().isUnauthorized())
                        .andDo(print())
                        .andReturn();
    }

    @Test
    void deleteById() throws Exception {

        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + "80001")
                .with(user(user_60000)))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    void deleteNotOwner() throws Exception {

        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + "80002")
                .with(user(user_60000)))
                        .andExpect(status().isUnauthorized())
                        .andDo(print());
    }

    @Test
    void deleteUnAuth() throws Exception {

        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + "80002"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}