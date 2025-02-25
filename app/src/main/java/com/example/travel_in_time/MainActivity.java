package com.example.travel_in_time;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    CardsRecViewAdapter adapter;
    private List<WikiDataModel> wikiDataList = new ArrayList<>();

    private Button eventsButton;
    private Button holidaysButton;
    private Button deathsButton;
    private Button birthsButton;

    private ConstraintLayout mainLayout;

    private String month;
    private String day;
    private Calendar calendar;


    @SuppressLint("ClickableViewAccessibility")
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

        //get the today date
        calendar = Calendar.getInstance();
        month = String.valueOf(calendar.get(Calendar.MONTH) +1);
        day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

        //get the views from the layout
        toolbar = findViewById(R.id.toolbar);
        eventsButton = findViewById(R.id.btnEvents);
        holidaysButton = findViewById(R.id.btnHolidays);
        deathsButton = findViewById(R.id.btnDeaths);
        birthsButton = findViewById(R.id.btnBirths);
        mainLayout = findViewById(R.id.main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CardsRecViewAdapter(this);
        adapter.setWikiContents(wikiDataList);

        // Set a layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setSupportActionBar(toolbar);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());

        // Set the subtitle of the toolbar to the current date
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(currentDate);
        }


        //listener for the buttons
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetch the data
                fetchEvents(month, day);
                recyclerView.setVisibility(View.VISIBLE);
                eventsButton.setVisibility(View.GONE);
                holidaysButton.setVisibility(View.GONE);
                deathsButton.setVisibility(View.GONE);
                birthsButton.setVisibility(View.GONE);
            }
        });

        holidaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetch the data
                fetchHolidays(month, day);
                recyclerView.setVisibility(View.VISIBLE);
                eventsButton.setVisibility(View.GONE);
                holidaysButton.setVisibility(View.GONE);
                deathsButton.setVisibility(View.GONE);
                birthsButton.setVisibility(View.GONE);
            }
        });

        deathsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetch the data
                fetchDeaths(month, day);
                recyclerView.setVisibility(View.VISIBLE);
                eventsButton.setVisibility(View.GONE);
                holidaysButton.setVisibility(View.GONE);
                deathsButton.setVisibility(View.GONE);
                birthsButton.setVisibility(View.GONE);
            }
        });

        birthsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetch the data
                fetchBirths(month, day);
                recyclerView.setVisibility(View.VISIBLE);
                eventsButton.setVisibility(View.GONE);
                holidaysButton.setVisibility(View.GONE);
                deathsButton.setVisibility(View.GONE);
                birthsButton.setVisibility(View.GONE);
            }
        });


        // Set an OnTouchListener on the main layout
        mainLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    eventsButton.setVisibility(View.VISIBLE);
                    holidaysButton.setVisibility(View.VISIBLE);
                    deathsButton.setVisibility(View.VISIBLE);
                    birthsButton.setVisibility(View.VISIBLE);
                }
            }
            return true;
        });


        //TODO : make the main activity more ux

    }


    //method to fetch the data
    private void fetchEvents(String month, String day){
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

        Call<OnThisDayResponse> events = wikiApiService.getEvents(month, day);
        events.enqueue(new Callback<OnThisDayResponse>() {
            @SuppressLint("NotifyDataSetChanged")
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

    private void fetchHolidays(String month, String day){
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

        Call<OnThisDayResponse> events = wikiApiService.getHolidays(month, day);
        events.enqueue(new Callback<OnThisDayResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<OnThisDayResponse> call, Response<OnThisDayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Wiki Access", "Successfully accessed Wikipedia");
                    wikiDataList.clear();
                    if (response.body().getHolidays() != null) {
                        wikiDataList.addAll(response.body().getHolidays());
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



    private void fetchDeaths(String month, String day){
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

        Call<OnThisDayResponse> events = wikiApiService.getDeaths(month, day);
        events.enqueue(new Callback<OnThisDayResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<OnThisDayResponse> call, Response<OnThisDayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Wiki Access", "Successfully accessed Wikipedia");
                    wikiDataList.clear();
                    if (response.body().getDeaths() != null) {
                        wikiDataList.addAll(response.body().getDeaths());
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

    private void fetchBirths(String month, String day){
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

        Call<OnThisDayResponse> events = wikiApiService.getBirths(month, day);
        events.enqueue(new Callback<OnThisDayResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<OnThisDayResponse> call, Response<OnThisDayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Wiki Access", "Successfully accessed Wikipedia");
                    wikiDataList.clear();
                    if (response.body().getBirths() != null) {
                        wikiDataList.addAll(response.body().getBirths());
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