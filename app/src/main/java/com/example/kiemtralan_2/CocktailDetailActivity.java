package com.example.kiemtralan_2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class CocktailDetailActivity extends AppCompatActivity {

    private ImageView imageCocktail;
    private TextView textCocktailName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_detail);

        imageCocktail = findViewById(R.id.imageCocktail);
        textCocktailName = findViewById(R.id.textCocktailName);

        // Nhận tên cocktail và URL hình ảnh từ Intent
        String cocktailName = getIntent().getStringExtra("cocktailName");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Hiển thị thông tin chi tiết của cocktail và hình ảnh
        displayCocktailDetails(cocktailName, imageUrl);
    }

    private void displayCocktailDetails(String cocktailName, String imageUrl) {
        // Hiển thị tên cocktail
        textCocktailName.setText(cocktailName);

        // Sử dụng thư viện Picasso để tải và hiển thị hình ảnh
        Picasso.get().load(imageUrl).into(imageCocktail);
    }
}
