package com.jkgroup.drasky.health.service.airly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;

import static com.jkgroup.drasky.configuration.HttpClientsConfiguration.AIRLY_CLIENT_BEAN_NAME;

@Service
@CacheConfig(cacheNames = "AirlyService")
public class AirlyService {
    private AirlyClient client;

    @Autowired
    public AirlyService(@Qualifier(AIRLY_CLIENT_BEAN_NAME) Retrofit retrofit){
        client = retrofit.create(AirlyClient.class);
    }

    @Cacheable
    public AirQuality checkAirQuality(String lat, String lng){
        try {
            Response<AirQuality> response = client.nearestMeasurements(lat, lng, "1.3").execute();

            if(!response.isSuccessful()){
                throw new AirlyServiceException("Response is not successful");
            }

            return response.body();
        } catch (IOException e) {
            throw new AirlyServiceException("Response is not successful", e);
        }
    }
}

interface AirlyClient {

    @GET("measurements/nearest")
    Call<AirQuality> nearestMeasurements(@Query("lat") String lat, @Query("lng") String lng, @Query("maxDistanceKM") String maxDistanceKM);
}


