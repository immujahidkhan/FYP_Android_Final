package com.fyp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.R;
import com.fyp.Utils.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView image;
    Button add;
    Uri path;
    Bitmap bitmap;
    EditText name, price, description;
    ProgressBar progress;
    SessionManagement sessionManagement;
    String provider_id, sERVICE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement = new SessionManagement(this);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        provider_id = user.get(SessionManagement.KEY_PROVIDER_ID);
        sERVICE_ID = user.get(SessionManagement.KEY_SERVICE_ID);
        setContentView(R.layout.activity_add_product);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
        progress = findViewById(R.id.progress);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        image.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == image) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 12);
        }
        if (v == add) {
            Log.d("IMAGE", "" + getStringImage().trim());
            if (TextUtils.isEmpty(name.getText()) && TextUtils.isEmpty(price.getText()) && TextUtils.isEmpty(description.getText())) {
                Toast.makeText(this, "Please fill details", Toast.LENGTH_SHORT).show();
            } else {
                if (bitmap == null) {
                    Toast.makeText(this, "Please Choose Image", Toast.LENGTH_SHORT).show();
                } else {
                    postAds();
                }
            }
        }
    }

    private void postAds() {
        progress.setVisibility(View.VISIBLE);
        GLOBA_API_CALL globa_api_call;
        Retrofit retrofit;
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        globa_api_call.add_product(getStringImage().trim(), sERVICE_ID, provider_id, name.getText().toString(), price.getText().toString(), description.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String response = responseBody.string();
                            name.setText("");
                            description.setText("");
                            price.setText("");
                            Toast.makeText(AddProductActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.setVisibility(View.GONE);
                        if (isOnline()) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String s = body.string();
                                        Log.e("ErrorFromToken", "" + s);
                                        try {
                                            JSONObject jsonObject = new JSONObject(s);
                                            String success = jsonObject.getString("status");
                                            if (success.equals("false")) {
                                                JSONArray message = jsonObject.getJSONObject("error").getJSONArray("error");
                                                Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
                                                //logOut();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onComplete() {
                        progress.setVisibility(View.GONE);
                    }
                });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}