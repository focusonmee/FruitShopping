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
import model.Vegetable;

public class VegetableList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VegetableAdapter vegetableAdapter;
    private VegetableDBHelper dbHelper;
    private List<Vegetable> vegetableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_list);

        recyclerView = findViewById(R.id.listVegetable);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        dbHelper = new VegetableDBHelper(this);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", -1);

        vegetableList = new ArrayList<>();
        insertSampleData();
        if (categoryId != -1) {
            loadProductsByCategory(categoryId);
        } else {
            loadDataFromDatabase();
        }

        vegetableAdapter = new VegetableAdapter(this, vegetableList);
        recyclerView.setAdapter(vegetableAdapter);
    }

    private void loadDataFromDatabase() {
        Cursor cursor = dbHelper.getAllProduct();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                vegetableList.add(new Vegetable(id, name, quantity, description, price, sale, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProductsByCategory(int categoryId) {
        Cursor cursor = dbHelper.getProductByCategory(categoryId);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                vegetableList.add(new Vegetable(id, name, quantity, description, price, sale, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No products found for this category", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertSampleData() {
        dbHelper.addProduct("Tomato", "7pcs, Price: $4.99", 2, 4.99, 1, 0,
                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_320x240.jpg",
                false);
        dbHelper.addProduct("Biscuit", "5pcs, Price: $2.50", 2, 2.50,199, 10,
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_320x240.jpg",
                true);
    }
}