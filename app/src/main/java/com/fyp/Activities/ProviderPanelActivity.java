package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fyp.R;
import com.fyp.Utils.SessionManagement;

import java.util.HashMap;

public class ProviderPanelActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManagement session;
    String id, providerId;
    Button updateInfo, products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_panel);
        products = findViewById(R.id.products);
        updateInfo = findViewById(R.id.updateInfo);
        session = new SessionManagement(this);
        HashMap<String, String> user = session.getUserDetails();
        id = user.get(SessionManagement.KEY_USER_ID);
        providerId = user.get(SessionManagement.KEY_PROVIDER_ID);
        updateInfo.setOnClickListener(this);
        products.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
        startActivity(new Intent(ProviderPanelActivity.this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v == updateInfo) {
            startActivity(new Intent(ProviderPanelActivity.this, UpdateProviderInfoActivity.class));
        }
        if (v == products) {
            startActivity(new Intent(ProviderPanelActivity.this, ProductsActivity.class));
        }
    }
}