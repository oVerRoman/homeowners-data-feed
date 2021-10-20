package com.simbirsoftintensiv.intensiv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simbirsoftintensiv.intensiv.entity.Address;
import com.simbirsoftintensiv.intensiv.entity.Company;
import com.simbirsoftintensiv.intensiv.entity.Role;
import com.simbirsoftintensiv.intensiv.entity.User;
import com.simbirsoftintensiv.intensiv.to.UserTo;
import com.simbirsoftintensiv.intensiv.util.JsonUtil;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {

    public static final int USER_60000_ID = 60000;
    public static final int ADMIN_60002_ID = 60002;
    public static final int USER_60001_ID = 60001;
    public static final int USER_60003_ID = 60003;

    public static final Address user_60000_Address = new Address(80002,"Чикаго","улица Ленина",
            "1",null,"31");
    public static final Address admin_60002_Address = new Address(80001,"Чикаго","улица Ленина",
            "1",null,"21");
    public static final Address user_60001_Address = new Address(80000,"Чикаго","улица Ленина",
            "1",null,"11");
    public static final Address user_60003_Address = new Address(80003,"Чикаго","улица Ленина",
            "3","A","3");

    public static final Company company = new Company(50000,"ТСЖ","Чикаго",222333L);


    public static final User user_60000 = new User(USER_60000_ID, 79000000000L, "user0@mail.ru",
            "Петр", "Петров", "Петрович", Role.USER);
    public static final User admin_60002 = new User(ADMIN_60002_ID, 79222222222L, "user2@mail.ru",
            "Иван", "Иванов", "Иванович", Role.ADMIN, Role.USER);
    public static final User user_60001 = new User(USER_60001_ID, 79111111111L, "user1@mail.ru",
            "Сидор", "Сидоров", "Сидорович", Role.USER);
    public static final User user_60003 = new User(USER_60003_ID, 79333333333L, "user3@mail.ru",
            "Фирс", "Фирсов", "Фирсович", Role.USER);

    public static final List<User> users = List.of(user_60000, user_60001, admin_60002, user_60003);

    static {
        user_60000.setCompany(company);
        user_60000.setAddress(user_60000_Address);
        admin_60002.setCompany(company);
        admin_60002.setAddress(admin_60002_Address);
        user_60001.setCompany(company);
        user_60001.setAddress(user_60001_Address);
        user_60003.setCompany(company);
        user_60003.setAddress(user_60003_Address);
    }

    public static <T> void assertEquals(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("address.id", "company", "id", "roles").isEqualTo(expected);
    }

    public static <T> void assertListEquals(List<T> actual, List<T> expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("address.id", "company", "id", "roles").isEqualTo(expected);
    }

    public static ResultMatcher jsonMatcher(UserTo expected, BiConsumer<UserTo, UserTo> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asUserTo(mvcResult), expected);
    }

    public static ResultMatcher jsonListMatcher(List<UserTo> expected, BiConsumer<List<UserTo>, List<UserTo>> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asUserTos(mvcResult), expected);
    }

    public static List<UserTo> asUserTos(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValues(jsonActual, UserTo.class);
    }


    public static UserTo asUserTo(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, UserTo.class);
    }

    public static User asUser(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, User.class);
    }
}
