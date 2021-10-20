package com.simbirsoftintensiv.intensiv.to;

import java.beans.ConstructorProperties;

public class UserTo extends AbstractUserTo {

    @ConstructorProperties({"id", "phone", "email", "firstName", "secondName", "patronymic", "city", "street", "house",
            "building", "apartment", "roles"})
    public UserTo(Integer id, String phone, String email, String firstName, String secondName, String patronymic,
                  String city, String street, String house, String building, String apartment, String roles) {
        super(phone, email, firstName, secondName, patronymic,
                city, street, house, building, apartment, roles);
        this.setId(id);
    }


//    public HashMap<String, String> info() {
//        String[] words = address.substring(7).split(", user=com");
//        String addressForFront = words[0]+"}";
//        HashMap<String, String> map = new HashMap<>();
//        map.put("user", String.valueOf(phone));
//        map.put("email", email);
//        map.put("firstName", firstName);
//        map.put("secondName", secondName);
//        map.put("patronymic", patronymic);
//        map.put("address", addressForFront);
//
//        return map;
//    }
}
