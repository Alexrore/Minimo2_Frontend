package com.example.restclientapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {

    // IMPORTANTE: Usar 10.0.2.2 para conectar al localhost de tu PC desde el emulador.
    private static final String BASE_URL = "https://dsa2.upc.edu/dsaApp/";

    private static Retrofit retrofit = null;
    public static AuthService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AuthService.class);
    }
}
