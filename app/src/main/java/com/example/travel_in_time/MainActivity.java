package com.example.travel_in_time;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String WIKIPEDIA_URL = "https://api.wikimedia.org/";
    private static final String AUTH_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI1ZjhiMmMyYzE1ZjAwYzE2ZDRhZmNiNzBlZDMxZmQxOSIsImp0aSI6ImVmZDIzYTM0MTZiMjEwN2VkNzE5ZjY1NTIyNWVlZGZjZGE2M2YxOWFhMmE5ZmJhMGI4MGJjYzJmYzEzZDE3Y2FkODhjMDY3NzBkZjU1ZDhjIiwiaWF0IjoxNzM5NTM4NTcwLjM2Njg0NCwibmJmIjoxNzM5NTM4NTcwLjM2Njg0NywiZXhwIjozMzI5NjQ0NzM3MC4zNjU1MTcsInN1YiI6Ijc3NjQwMjczIiwiaXNzIjoiaHR0cHM6Ly9tZXRhLndpa2ltZWRpYS5vcmciLCJyYXRlbGltaXQiOnsicmVxdWVzdHNfcGVyX3VuaXQiOjUwMDAsInVuaXQiOiJIT1VSIn0sInNjb3BlcyI6WyJiYXNpYyJdfQ.Pms36aRiT0Pq_nZ1r16owU7pY32WAcRY7m62hiRXDfOo7C1b3bWi_DamK5iUNb8LSXJFLicKTPtXVRGGnjYeNjI54tAh5Nxza5pYYX8YaSAhbk6Yj2SudHt9Zw6jAnyHe63OvdfeSlncflM8GUhXllK-ka5hs3WdQIeKaVsI-DFFZkjw6ptYJwpFzHICvOff1QiZx3i0d0fmTlUGMovpbVHAZ4IzdS6CDaBilRv8BSy2csDKniN4NMGciOnyXWBA0HSFefVEQMhSRjdzwdPI1zerKuLOHfcJ0_aLti_nytuusag2hMRpa8c7plQnfvsa2y3QZqctOzf1n7z3WD1pZ8InBoudM70VEAMfvmzs5OKCWjKDNMG5NymTJpQV7BrY_OkjyFZpZkZGXym_8xKAw5Ccqiw6Yv_RZWOiA2fztp_RcO5lozS1iA6_5digkmVIGfqmgAFPs2tQeWiRGF-xhHQVDRDtavrwn-E9h0HBI4lQve3cCXit-q677qkl8xhNAudtorC5gIksImjmBGkNdLdBU1RdOHN3Oe2wM6_QqYKmVMfwPAt8sC4_8M2dxTKJ0oOpqY1gY0NiRsfk2Rf4MLFahxtuL4sIVDtIPhxNHV0udkjaZKs9MMlZYrdNZh2nP_3EElqSqdY2eqa9xyqwyCOg2acvmehSg7SDu4zD2IQ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer " + AUTH_TOKEN)
                    .header("User-Agent", "TravelInTime/1.0")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        });
        httpClient.addInterceptor(logging);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WIKIPEDIA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WikiApiService wikiApiService = retrofit.create(WikiApiService.class);

        //TODO : modify the data model to reflect the JSON response
        //TODO : change the adapter to reflect the new data model
        //TODO : change the layout of the card
        //TODO : make the logic for the api calls


        Call<OnThisDayResponse> call = wikiApiService.getEvents("02", "14");
        call.enqueue(new Callback<OnThisDayResponse>() {
            @Override
            public void onResponse(Call<OnThisDayResponse> call, Response<OnThisDayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Wiki Access", "Successfully accessed Wikipedia");
                    // Procesează răspunsul aici
//                    Log.d("Wiki Access", "Events: " + response.body().getEvents().size());
                    Log.d("Wiki Access", "Events: " + response.body().getEvents().get(0).getTitle());
                } else {
                    Log.e("Wiki Access Error", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OnThisDayResponse> call, Throwable t) {
                Log.e("Wiki Access Error", t.getMessage());
            }
        });
    }
}