package com.sydoruk;

import com.sydoruk.service.MongoService;

public class Main {

    public static void main(String[] arg) {
        MongoService.getInstance().execute();
    }

}