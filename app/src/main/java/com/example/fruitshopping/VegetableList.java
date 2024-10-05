package com.example.fruitshopping;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import adapter.VegetableAdapter;
import helper.VegetableDBHelper;
import model.Product;


public class VegetableList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VegetableAdapter vegetableAdapter;
    private VegetableDBHelper dbHelper;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_list);

        recyclerView = findViewById(R.id.listVegetable);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        dbHelper = new VegetableDBHelper(this);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", -1);

        productList = new ArrayList<>();
        insertSampleData();
        if (categoryId != -1) {
            loadProductsByCategory(categoryId);
        } else {
            loadDataFromDatabase();
        }

        vegetableAdapter = new VegetableAdapter(this, productList);
        recyclerView.setAdapter(vegetableAdapter);
    }

    private void loadDataFromDatabase() {
        Cursor cursor = dbHelper.getAllProduct();
        productList.clear();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                productList.add(new Product(id, name, price, sale, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProductsByCategory(int categoryId) {
        Cursor cursor = dbHelper.getProductByCategory(categoryId);
        productList.clear();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                productList.add(new Product(id, name, price, sale, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No products found for this category", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertSampleData() {
        dbHelper.addProduct("Cai Xanh", "5pcs, Price: $2", 2, 2.79,199, 20,
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_320x240.jpg",
                false);
        dbHelper.addProduct("Trai Dua", "5pcs, Price: $2", 2, 2.79,199, 0,
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_320x240.jpg",
                false);
   }
}