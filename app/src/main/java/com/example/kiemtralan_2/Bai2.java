package com.example.kiemtralan_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Bai2 extends AppCompatActivity {
    // Bài 2
     ListView listViewCocktails;
     ArrayAdapter<String> adapter;
     Button btnAlcoholic, btnNonAlcoholic, btnGin, btnVodka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        addEvents();
        addControls();
    }

    public void addEvents(){
        listViewCocktails = (ListView) findViewById(R.id.listViewCocktails);
        btnAlcoholic = (Button) findViewById(R.id.btnAlcoholic);
        btnNonAlcoholic = (Button) findViewById(R.id.btnNonAlcoholic);
        btnGin = (Button) findViewById(R.id.btnGin);
        btnVodka = (Button) findViewById(R.id.btnVodka);
    }

    public void addControls(){

        btnAlcoholic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCocktails("Alcoholic");
            }
        });

        btnNonAlcoholic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCocktails("Non_Alcoholic");
            }
        });

        btnGin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCocktailsI("Gin");
            }
        });

        btnVodka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCocktailsI("Vodka");
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewCocktails.setAdapter(adapter);

        listViewCocktails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cocktail selectedCocktail = (Cocktail) parent.getItemAtPosition(position);

                // Lấy thông tin tên cocktail và URL hình ảnh từ đối tượng Cocktail được chọn
                String cocktailName = selectedCocktail.getStrDrink();
                String imageUrl = selectedCocktail.getStrDrinkThumb();

                // Chuyển sang activity chi tiết và truyền tên cocktail và URL hình ảnh
                Intent intent = new Intent(Bai2.this, CocktailDetailActivity.class);
                intent.putExtra("cocktailName", cocktailName);
                intent.putExtra("imageUrl", imageUrl);
                startActivity(intent);
            }
        });


    }
    public interface CocktailApiService {
        @GET("filter.php")
        Call<CocktailResponse> getCocktailsByTypeA(@Query("a") String type);
        @GET("filter.php")
        Call<CocktailResponse> getCocktailsByTypeI(@Query("i") String type);
    }

    private void loadCocktails(String typeA) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CocktailApiService service = retrofit.create(CocktailApiService.class);

        Call<CocktailResponse> call = service.getCocktailsByTypeA(typeA);
        call.enqueue(new Callback<CocktailResponse>() {
            @Override
            public void onResponse(Call<CocktailResponse> call, Response<CocktailResponse> response) {
                if (response.isSuccessful()) {
                    CocktailResponse cocktailResponse = response.body();
                    if (cocktailResponse != null && cocktailResponse.getDrinks() != null) {
                        List<String> cocktailNames = new ArrayList<>();
                        for (Cocktail drink : cocktailResponse.getDrinks()) {
                            cocktailNames.add(drink.getStrDrink());
                        }
                        adapter.clear();
                        adapter.addAll(cocktailNames);
                    }
                }
            }

            @Override
            public void onFailure(Call<CocktailResponse> call, Throwable t) {

            }
        });
    }
    private void loadCocktailsI(String typeI) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CocktailApiService service = retrofit.create(CocktailApiService.class);

        Call<CocktailResponse> call = service.getCocktailsByTypeI(typeI);
        call.enqueue(new Callback<CocktailResponse>() {
            @Override
            public void onResponse(Call<CocktailResponse> call, Response<CocktailResponse> response) {
                if (response.isSuccessful()) {
                    CocktailResponse cocktailResponse = response.body();
                    if (cocktailResponse != null && cocktailResponse.getDrinks() != null) {
                        List<String> cocktailNames = new ArrayList<>();
                        for (Cocktail drink : cocktailResponse.getDrinks()) {
                            cocktailNames.add(drink.getStrDrink());
                        }
                        adapter.clear();
                        adapter.addAll(cocktailNames);
                    }
                }
            }
            @Override
            public void onFailure(Call<CocktailResponse> call, Throwable t) {
            }
        });
    }
}