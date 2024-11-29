package com.example.mysalud.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //IP
    private static final String BASE_URL = "http://192.168.1.40:5000/";  //http://10.0.2.2:5000/// URL de tu API
    private static Retrofit retrofit;

    // Obtener la instancia de Retrofit
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Base URL de tu API
                    .addConverterFactory(GsonConverterFactory.create())  // Convertir JSON a objetos Java
                    .build();
        }
        return retrofit;
    }
}

