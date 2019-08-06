package com.fyp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.R;
import com.fyp.Utils.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView signUp;
    Button loginBtn;
    EditText email, password;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        session = new SessionManagement(getApplicationContext());
        if (session.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, ProviderPanelActivity.class));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                finish();
            }
        }
        signUp = findViewById(R.id.signUp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signUp.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == signUp) {
            startActivity(new Intent(LoginActivity.this, SignUpServiceProviderActivity.class));
        }
        if (view == loginBtn) {
            String emails = email.getText().toString().trim();
            String passwords = password.getText().toString().trim();
            if (TextUtils.isEmpty(emails)) {
                email.setError("Required");
                email.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
                email.setError("Not a valid email");
                email.requestFocus();
            } else if (TextUtils.isEmpty(passwords)) {
                password.setError("Required");
                password.requestFocus();
            } else {
                GLOBA_API_CALL globa_api_call;
                Retrofit retrofit;
                retrofit = RetrofitClient.getInstance();
                globa_api_call = retrofit.create(GLOBA_API_CALL.class);
                globa_api_call.providerLogin(emails, passwords).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            JSONObject jsonObject = new JSONObject(res);
                            if (jsonObject.getString("response").equals("Found")) {
                                String id = jsonObject.getString("id");
                                String provider_id = jsonObject.getString("provider_id");
                                String service_id = jsonObject.getString("service_id");
                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                session.createLoginSession(id, provider_id, service_id, name, email);
                                startActivity(new Intent(LoginActivity.this, ProviderPanelActivity.class));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                } else {
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "" + jsonObject.getString("response"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}