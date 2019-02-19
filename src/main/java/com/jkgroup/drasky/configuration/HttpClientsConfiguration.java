package com.jkgroup.drasky.configuration;

import com.jkgroup.drasky.health.service.airly.AirlyClient;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class HttpClientsConfiguration {

    @Bean
    @Profile("!mock")
    public AirlyClient airlyClient(@Value("${airly.api-key}") String apiKey, @Value("${airly.base-url}") String baseUrl){
        return retrofit(baseUrl, okHttpClient(Headers.of(
                "Accept", "application/json",
                "Accept-Language", "en",
//                "Accept-Encoding", "gzip",
                "apikey", apiKey)))
                .create(AirlyClient.class);
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
