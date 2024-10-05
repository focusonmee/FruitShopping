package com.example.fruitshopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
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

import adapter.BestDealAdapter;
import adapter.CategoryAdapter;
import adapter.VegetableAdapter;
import helper.CategoryDBHelper;
import helper.VegetableDBHelper;
import model.Category;
import model.Product;

public class Home extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBestProduct;

    private CategoryAdapter categorAdapter;
    private BestDealAdapter bestDealAdapter ;

    private CategoryDBHelper dbHelper;
    private VegetableDBHelper dbVegetableHelper;

    private List<Category> categoryList;
    private List<Product> productList;

    private SearchView searchHomeView;

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

        categoryList = new ArrayList<>();
        productList  = new ArrayList<>();

        searchHomeView = findViewById(R.id.searchHomeView);
        searchHomeView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(Home.this, SearchActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    Intent intent = new Intent(Home.this, SearchActivity.class);
                    intent.putExtra("query", "");
                    startActivity(intent);
                }
                return false;
            }
        });

        loadDataFromDatabase();
        categoryList.add(new Category(1, "Fruit"));
        categoryList.add(new Category(2, "Vegetable"));
        categorAdapter = new CategoryAdapter(this, categoryList);
        bestDealAdapter = new BestDealAdapter(this, productList);
        recyclerView.setAdapter(categorAdapter);
        recyclerViewBestProduct.setAdapter(bestDealAdapter);
    }

    private void loadDataFromDatabase() {
        Cursor cursor = dbVegetableHelper.getBestDealProduct();
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
}