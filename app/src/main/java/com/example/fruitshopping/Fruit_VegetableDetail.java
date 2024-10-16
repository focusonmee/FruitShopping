package com.example.fruitshopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import helper.VegetableDBHelper;

public class Fruit_VegetableDetail extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView textNameDetail, textPriceDetail, textSaleDetail, textDescriptionDetail;
    private Button btnAddToBasket;
    private VegetableDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_vegetable_detail);

        imgDetail = findViewById(R.id.imgDetail);
        textNameDetail = findViewById(R.id.textNameDetail);
        textPriceDetail = findViewById(R.id.textPriceDetail);
        textSaleDetail = findViewById(R.id.textViewSale);
        textDescriptionDetail = findViewById(R.id.textDescriptionDetail);
        btnAddToBasket = findViewById(R.id.btnAddToBasket);

        dbHelper = new VegetableDBHelper(this);

        Intent intent = getIntent();
        int productId = intent.getIntExtra("productId", -1);
        if (productId != -1) {
            loadProductDetails(productId);
        }
    }

    private void loadProductDetails(int productId) {
        Cursor cursor = dbHelper.getProductDetail(productId);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image"));

            textNameDetail.setText(name);
            textPriceDetail.setText(String.format("$%.2f", price));
            textSaleDetail.setText(String.format("%d%% OFF", sale));
            textDescriptionDetail.setText(description);

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.carrot_logo)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imgDetail);
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
