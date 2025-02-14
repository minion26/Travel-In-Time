package com.example.travel_in_time;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface WikiApiService {

    @GET("feed/v1/wikipedia/en/onthisday/births/{mm}/{dd}")
    Call<OnThisDayResponse> getBirths(@Path("mm") String month, @Path("dd") String day);

    @GET("feed/v1/wikipedia/en/onthisday/deaths/{mm}/{dd}")
    Call<OnThisDayResponse> getDeaths(@Path("mm") String month, @Path("dd") String day);

    @GET("feed/v1/wikipedia/en/onthisday/holidays/{mm}/{dd}")
    Call<OnThisDayResponse> getHolidays(@Path("mm") String month, @Path("dd") String day);

    @GET("feed/v1/wikipedia/en/onthisday/events/{mm}/{dd}")
    Call<OnThisDayResponse> getEvents(@Path("mm") String month, @Path("dd") String day);


}
