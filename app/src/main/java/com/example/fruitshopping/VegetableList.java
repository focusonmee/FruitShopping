package com.example.fruitshopping;

import android.annotation.SuppressLint;
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

        // Insert sample data if needed (optional)
        insertSampleData();

        vegetableList = new ArrayList<>();

        loadDataFromDatabase();
        System.out.println(vegetableList);
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
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                vegetableList.add(new Vegetable(id, name, description, price, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertSampleData() {
        dbHelper.addProduct("Organic Bananas", "7pcs, Price: $4.99", 1, 4.99, 0,
                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_320x240.jpg",
                false);
        dbHelper.addProduct("Carrot", "5pcs, Price: $2.50", 1, 2.50, 0,
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_1200x800.jpg",
                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_320x240.jpg",
                true);
    }
}