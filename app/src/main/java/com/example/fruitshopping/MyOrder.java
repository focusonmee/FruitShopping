package com.example.fruitshopping;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import adapter.OrderAdapter;
import helper.OrderDBHelper;

public class MyOrder extends AppCompatActivity {
    private OrderDBHelper orderDBHelper;
    private ListView orderListView;
//    private OrderAdapter<String> orderAdapter;
    private ArrayList<String> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);


    }
}