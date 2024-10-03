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

import adapter.FruitAdapter;
import helper.VegetableDBHelper;
import model.Fruit;

public class FruitList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FruitAdapter fruitAdapter;
    private VegetableDBHelper dbHelper;
    private List<Fruit> fruitList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_list);

        recyclerView = findViewById(R.id.listFruit);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        dbHelper = new VegetableDBHelper(this);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", -1);

        fruitList = new ArrayList<>();
        insertSampleData();
        if (categoryId != -1) {
            loadProductsByCategory(categoryId);
        } else {
            loadDataFromDatabase();
        }

        fruitAdapter = new FruitAdapter(this, fruitList);
        recyclerView.setAdapter(fruitAdapter);
    }

    private void loadDataFromDatabase() {
        Cursor cursor = dbHelper.getAllProduct();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                fruitList.add(new Fruit(id, name, price, sale, image));
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
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                fruitList.add(new Fruit(id, name, price, sale, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No products found for this category", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertSampleData() {
        dbHelper.addProduct("Penpaya", "7pcs, Price: $4.99", 1, 10, 1, 50,
                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_320x240.jpg",
                true);
    }
}