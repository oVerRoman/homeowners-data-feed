package com.simbirsoftintensiv.intensiv.controller.request;

import com.simbirsoftintensiv.intensiv.entity.Request;

public class RequestTestData {

    public static Request request_user_60000_1 = new Request(80000, 1, "Заявка 1",
            null,1,"Описание заявки 1",1,"filename",60000);

    public static Request request_user_60000_2 = new Request(80001, 1, "Заявка 2",
            null,1,"Когда будет готова Заявка 1?",1,"filename",60000);

    public static Request request_user_60001_1 = new Request(80002, 2, "Заявка 1",
            null,2,"А у нас в квартире газ",2,"filename",60001);



}
