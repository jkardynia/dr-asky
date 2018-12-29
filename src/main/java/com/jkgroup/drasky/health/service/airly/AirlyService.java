package com.jkgroup.drasky.health.service.airly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;

import static com.jkgroup.drasky.configuration.HttpClientsConfiguration.AIRLY_CLIENT_BEAN_NAME;

@Service
public class AirlyService {
    private AirlyServiceClient client;

    @Autowired
    public AirlyService(@Qualifier(AIRLY_CLIENT_BEAN_NAME) Retrofit retrofit){
        client = retrofit.create(AirlyServiceClient.class);
    }

    public AirQuality checkAirQuality(String location){
        try {
            return client.nearestMeasurements("50.061897", "19.936756", "1").execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

interface AirlyServiceClient{
    @GET("measurements/nearest")
    Call<AirQuality> nearestMeasurements(@Query("lat") String lat, @Query("lng") String lng, @Query("maxDistanceKM") String maxDistanceKM);
}


