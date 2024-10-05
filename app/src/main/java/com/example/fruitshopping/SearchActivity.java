package com.example.fruitshopping;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.SearchAdapter;
import helper.VegetableDBHelper;
import model.Product;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private VegetableDBHelper dbHelper;
    private List<Product> productList;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new VegetableDBHelper(this);
        productList = new ArrayList<>(); // Khởi tạo productList

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.listSearch);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Khởi tạo Adapter
        searchAdapter = new SearchAdapter(this, productList);
        recyclerView.setAdapter(searchAdapter);

        // Khởi tạo SearchView
        searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);

        String query = getIntent().getStringExtra("query");
        if(query != null && !query.isEmpty()) {
            searchView.setQuery(query, false);
            searchProduct(query);
        } else {
            loadDataFromDatabase();
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProduct(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadDataFromDatabase();
                } else {
                    searchProduct(newText);
                }
                return true;
            }
        });
    }

    private void searchProduct(String name) {
        Cursor cursor = dbHelper.getProductsByName(name);
        productList.clear();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int sale = cursor.getInt(cursor.getColumnIndexOrThrow("sale"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

                productList.add(new Product(id, productName, price, sale, image));
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        }

        searchAdapter.notifyDataSetChanged();
    }

    private void loadDataFromDatabase() {
        Cursor cursor = dbHelper.getBestDealProduct();
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
