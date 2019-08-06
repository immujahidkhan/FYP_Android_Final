package com.fyp.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.fyp.Adapters.ProviderAdaptersClass;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.Provider;
import com.fyp.ModelsClass.ProviderModel;
import com.fyp.R;
import com.fyp.Utils.SessionManagement;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    SpinKitView progress;
    ArrayList<Provider> providerModelArrayList = new ArrayList<>();
    ProviderAdaptersClass adaptersClass;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement = new SessionManagement(this);

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHeaderUI();
    }

    private void loadHeaderUI() {
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.name);
        TextView email = header.findViewById(R.id.email);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        String value = user.get(SessionManagement.KEY_NAME);
        if (value != null) {
            name.setText(user.get(SessionManagement.KEY_NAME));
            email.setText(user.get(SessionManagement.KEY_EMAIL));
        } else {
            name.setText("Service Provider");
            email.setText("");
        }
    }

    private void loadData() {
        progress.setVisibility(View.VISIBLE);
        GLOBA_API_CALL globa_api_call;
        Retrofit retrofit;
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        globa_api_call.getProvider()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProviderModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProviderModel providerModel) {
                        if (providerModelArrayList != null) {
                            providerModelArrayList.clear();
                        }
                        providerModelArrayList = (ArrayList<Provider>) providerModel.getProviders();
                        adaptersClass = new ProviderAdaptersClass(MainActivity.this, providerModelArrayList);
                        recyclerView.setAdapter(adaptersClass);
                        adaptersClass.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Network Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("ERRRRR", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        progress.setVisibility(View.GONE);
                        adaptersClass.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(MainActivity.this, OrderActivity.class));
        } else if (id == R.id.nav_service_provider) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else if (id == R.id.nav_AboutUs) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        } else if (id == R.id.nav_ic_contact) {
            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
        } else if (id == R.id.nav_Add_products) {
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
        } else if (id == R.id.nav_logout) {
            sessionManagement.logoutUser();
            loadHeaderUI();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}