package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fyp.Adapters.ProductsAdapter;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.ProductList;
import com.fyp.ModelsClass.ProviderModel;
import com.fyp.R;
import com.fyp.Utils.SessionManagement;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SpinKitView progress;
    LinearLayoutManager manager;
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;
    SessionManagement sessionManagement;
    String provider_id;
    ArrayList<ProductList> lists = new ArrayList<>();
    ProductsAdapter adapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        sessionManagement = new SessionManagement(this);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        provider_id = user.get(SessionManagement.KEY_PROVIDER_ID);
        setContentView(R.layout.activity_products);
        adapter = new ProductsAdapter(this, lists);
        progress = findViewById(R.id.spin_kit);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        globa_api_call.getProducts("products_list.php?provider_id=" + provider_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("Products");
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String product_id = object.getString("product_id");
                        String provider_id = object.getString("provider_id");
                        String provider_name = object.getString("provider_name");
                        String provider_description = object.getString("provider_description");
                        String provider_image = object.getString("provider_image");
                        String provider_price = object.getString("provider_price");
                        lists.add(new ProductList(product_id, provider_id, provider_name, provider_description, provider_image, provider_price));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    if (count == 0) {
                        Toast.makeText(ProductsActivity.this, "No Product Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            startActivity(new Intent(ProductsActivity.this, AddProductActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}