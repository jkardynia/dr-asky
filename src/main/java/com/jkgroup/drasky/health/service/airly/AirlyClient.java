package com.jkgroup.drasky.health.service.airly;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AirlyClient {

    @GET("measurements/nearest")
    Call<AirQuality> nearestMeasurements(@Query("lat") String lat, @Query("lng") String lng, @Query("maxDistanceKM") String maxDistanceKM);
}
