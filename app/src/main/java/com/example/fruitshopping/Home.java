package com.example.fruitshopping;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.CategoryAdapter;
import adapter.VegetableAdapter;
import helper.CategoryDBHelper;
import helper.VegetableDBHelper;
import model.Category;
import model.Vegetable;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBestProduct;

    private CategoryAdapter categorAdapter;
    private VegetableAdapter vegetableAdapter ;

    private CategoryDBHelper dbHelper;
    private VegetableDBHelper dbVegetableHelper;

    private List<Category> categoryList;
    private List<Vegetable> vegetableList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.cateList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerViewBestProduct = findViewById(R.id.bestProduct);
        recyclerViewBestProduct.setLayoutManager(new GridLayoutManager(this, 2));

        dbVegetableHelper = new VegetableDBHelper(this);
        dbHelper = new CategoryDBHelper(this);

        //insertSampleData();

        categoryList = new ArrayList<>();
        vegetableList  = new ArrayList<>();

        loadDataFromDatabase();
        categoryList.add(new Category(1, "Fruit"));
        categoryList.add(new Category(2, "Vegetable"));
        categorAdapter = new CategoryAdapter(this, categoryList);
        vegetableAdapter = new VegetableAdapter(this,vegetableList);
        recyclerView.setAdapter(categorAdapter);
        recyclerViewBestProduct.setAdapter(vegetableAdapter);
    }

    private void loadDataFromDatabase() {
        Cursor cursor = dbVegetableHelper.getBestDealProduct();

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

//    private void insertSampleData() {
//        dbVegetableHelper.addProduct("Organic Apples", "7pcs, Price: $4.99", 1, 4.99, 1, 0,
//                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_1200x800.jpg",
//                "https://images.wallpaperscraft.com/image/single/banana_fruit_vitamin_169308_320x240.jpg",
//                true);
//        dbVegetableHelper.addProduct("Tomato", "5pcs, Price: $2.50", 1, 2.50,199, 0,
//                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_1200x800.jpg",
//                "https://images.wallpaperscraft.com/image/single/carrot_root_crops_food_224645_320x240.jpg",
//                true);
//    }
}