package com.example.travel_in_time;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private static final String AUTH_TOKEN = "";

    private RecyclerView recyclerView;
    private List<WikiDataModel> wikiDataList = new ArrayList<>();


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

        recyclerView = findViewById(R.id.recyclerView);
        CardsRecViewAdapter adapter = new CardsRecViewAdapter(this);
        adapter.setWikiContents(wikiDataList);

        // Set a layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


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

        //TODO : add buttons for holiday, births, deaths, events
        //TODO : make the main activity more ux
        //TODO : make the logic for the api calls


        Call<OnThisDayResponse> events = wikiApiService.getEvents("02", "14");
        events.enqueue(new Callback<OnThisDayResponse>() {
            @Override
            public void onResponse(Call<OnThisDayResponse> call, Response<OnThisDayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Wiki Access", "Successfully accessed Wikipedia");
                    wikiDataList.clear();
                    if (response.body().getEvents() != null) {
                        wikiDataList.addAll(response.body().getEvents());
                    }
                    adapter.notifyDataSetChanged();

                    // Procesează răspunsul aici
                    // Log.d("Wiki Access", "Events: " + response.body().getEvents());
                    // Log.d("Wiki Access", "Events: " + response.body().getEvents().get(0).getText());

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