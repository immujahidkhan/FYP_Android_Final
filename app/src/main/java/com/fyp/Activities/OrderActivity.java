package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fyp.Adapters.OrdersAdaptersClass;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.Orders;
import com.fyp.ModelsClass.OrdersList;
import com.fyp.R;
import com.fyp.Utils.MyApplication;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SpinKitView progress;
    ArrayList<OrdersList> ordersArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }

    private void loadData() {
        progress.setVisibility(View.VISIBLE);
        GLOBA_API_CALL globa_api_call;
        Retrofit retrofit;
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        globa_api_call.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Orders>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Orders orders) {
                        ordersArrayList = (ArrayList<OrdersList>) orders.getOrdersList();
                        OrdersAdaptersClass adaptersClass = new OrdersAdaptersClass(OrderActivity.this, ordersArrayList);
                        recyclerView.setAdapter(adaptersClass);
                        adaptersClass.notifyDataSetChanged();
                        setList(ordersArrayList);
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
                        //adaptersClass.notifyDataSetChanged();
                    }
                });
    }

    private void setList(ArrayList<OrdersList> ordersArrayList) {
        ((MyApplication) this.getApplication()).orderArrayList.addAll(ordersArrayList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            if (ordersArrayList != null) {
                if (ordersArrayList.size() > 0) {
                    startActivity(new Intent(OrderActivity.this, RegisterActivity.class));
                    //startActivity(new Intent(OrderActivity.this, InvoiceActivity.class));
                } else {
                    Toast.makeText(this, "Please Add Cart", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}