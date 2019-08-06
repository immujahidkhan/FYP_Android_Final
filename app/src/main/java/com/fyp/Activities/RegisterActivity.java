package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.ProductList;
import com.fyp.R;
import com.fyp.Utils.MyApplication;
import com.github.ybq.android.spinkit.SpinKitView;
import com.santalu.maskedittext.MaskEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    SpinKitView spin_kit;
    EditText fname, lname, email, address;
    MaskEditText mobile;
    Button proceed;
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;
    String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Check Out");
        spin_kit = findViewById(R.id.spin_kit);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(this);
        //createInvoice();
    }

    @Override
    public void onClick(View view) {
        if (view == proceed) {
            if (TextUtils.isEmpty(fname.getText().toString()) && TextUtils.isEmpty(lname.getText().toString()) && TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(mobile.getText().toString())) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            } else {
                mEmail = email.getText().toString();
                String username = email.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show();
                } else {
                    spin_kit.setVisibility(View.VISIBLE);
                    globa_api_call.user_reg(fname.getText().toString(), lname.getText().toString(), mobile.getText().toString(), email.getText().toString(), address.getText().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                String res = null;
                                try {
                                    res = response.body().string();
                                    JSONObject jsonObject = new JSONObject(res);
                                    String error = jsonObject.getString("error");
                                    String message = jsonObject.getString("message");
                                    if (error.equals("true")) {
                                        createInvoice();
                                    } else {
                                        spin_kit.setVisibility(View.GONE);
                                        Toast.makeText(RegisterActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            spin_kit.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

    private void createInvoice() {
        globa_api_call.getProducts("sendmail.php?email=" + mEmail).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    //JSONObject jsonObject = new JSONObject(res);
                    spin_kit.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity();
                    }
                    startActivity(new Intent(RegisterActivity.this, InvoiceActivity.class));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}