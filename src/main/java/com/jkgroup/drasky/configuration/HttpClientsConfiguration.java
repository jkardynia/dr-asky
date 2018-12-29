package com.jkgroup.drasky.configuration;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class HttpClientsConfiguration {

    public static final String AIRLY_CLIENT_BEAN_NAME = "AIRLY_CLIENT";

    @Bean(name = AIRLY_CLIENT_BEAN_NAME)
    public Retrofit airlyClient(@Value("${airly.api-key}") String apiKey, @Value("${airly.base-url}") String baseUrl){
        return retrofit(baseUrl, okHttpClient(Headers.of(
                "Accept", "application/json",
                "Accept-Language", "en",
//                "Accept-Encoding", "gzip",
                "apikey", apiKey)));
    }

    private Retrofit retrofit(String baseUrl, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    private OkHttpClient okHttpClient(Headers headers){
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .headers(headers)
                            .build();

                    return chain.proceed(request);
                }).build();
    }
}
