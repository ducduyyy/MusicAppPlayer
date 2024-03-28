package com.example.musicappplayer.service;

public class APIService {
    private static String base_url = "https://muicsaver.000webhostapp.com/Server/connect/";

    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }

}
