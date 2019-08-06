package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.fyp.Adapters.InvoiceAdapters;
import com.fyp.Adapters.OrdersAdaptersClass;
import com.fyp.ModelsClass.OrdersList;
import com.fyp.R;
import com.fyp.Utils.MyApplication;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<OrdersList> ordersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ordersArrayList = ((MyApplication) this.getApplication()).orderArrayList;
        InvoiceAdapters adaptersClass = new InvoiceAdapters(InvoiceActivity.this, ordersArrayList);
        recyclerView.setAdapter(adaptersClass);
        adaptersClass.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(InvoiceActivity.this, MainActivity.class));
        //super.onBackPressed();
    }
}