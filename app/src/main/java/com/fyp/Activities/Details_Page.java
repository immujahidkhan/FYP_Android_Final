package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.Adapters.DetailsAdapters;
import com.fyp.Adapters.ProviderAdaptersClass;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.DetailsModel;
import com.fyp.ModelsClass.Provider;
import com.fyp.ModelsClass.ProviderModel;
import com.fyp.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class Details_Page extends AppCompatActivity {

    String user_id;
    SpinKitView progress;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<DetailsModel> list = new ArrayList<>();
    DetailsAdapters adapters;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__page);
        progress = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        user_id = getIntent().getStringExtra("user_id");
        progress.setVisibility(View.VISIBLE);
        GLOBA_API_CALL globa_api_call;
        Retrofit retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        globa_api_call.getProvider_details(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        updateUI(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "IP - Address " + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        progress.setVisibility(View.GONE);
                    }
                });
    }

    private void updateUI(ResponseBody responseBody) {
        try {
            String response = responseBody.string();
            //Toast.makeText(this, "" + response, Toast.LENGTH_SHORT).show();
            Log.d("USER_DETAILS", "" + response);
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("provider");
            String provider_id = jsonArray.getJSONObject(0).getString("provider_id");
            String user_id = jsonArray.getJSONObject(0).getString("user_id");
            String description = jsonArray.getJSONObject(0).getString("description");
            String views = jsonArray.getJSONObject(0).getString("views");
            String email = jsonArray.getJSONObject(0).getString("email");
            String addressS = jsonArray.getJSONObject(0).getString("address");
            String name = jsonArray.getJSONObject(0).getString("name");
            String phone = jsonArray.getJSONObject(0).getString("phone");
            String phone2 = jsonArray.getJSONObject(0).getString("phone2");
            JSONObject productObj = jsonArray.getJSONObject(0);
            JSONArray productArray = productObj.getJSONArray("products");
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject obj = productArray.getJSONObject(i);
                //Toast.makeText(this, "Ok" + obj.getString("product_name"), Toast.LENGTH_SHORT).show();
                Log.d("product_name", "" + obj.getString("product_name"));
                String product_id = obj.getString("product_id");
                String product_name = obj.getString("product_name");
                String product_description = obj.getString("product_description");
                String product_price = obj.getString("product_price");
                String service_id = obj.getString("service_id");
                String img = obj.getString("img");

                list.add(new DetailsModel(name, addressS, description, product_id, product_name,
                        product_description, product_price, provider_id, service_id, img));
            }
            adapters = new DetailsAdapters(this, list);
            recyclerView.setAdapter(adapters);
            adapters.notifyDataSetChanged();
            progress.setVisibility(View.GONE);
            /*user_name.setText(name);
            address.setText(addressS);*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}