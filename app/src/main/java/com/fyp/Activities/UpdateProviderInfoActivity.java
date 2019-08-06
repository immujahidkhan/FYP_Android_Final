package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.R;
import com.fyp.Utils.SessionManagement;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateProviderInfoActivity extends AppCompatActivity implements View.OnClickListener {
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;
    Button register_providerBtn;
    TextView signIn;
    SpinKitView progress;
    String user_id;
    SessionManagement sessionManagement;
    EditText fname, lname, contact, otherContact, password, description, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement = new SessionManagement(this);
        HashMap<String, String> user = sessionManagement.getUserDetails();
        user_id = user.get(SessionManagement.KEY_USER_ID);
        setContentView(R.layout.activity_update_provider_info);
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        fname = findViewById(R.id.fname);
        signIn = findViewById(R.id.signIn);
        lname = findViewById(R.id.lname);
        contact = findViewById(R.id.contact);
        otherContact = findViewById(R.id.othercontact);
        password = findViewById(R.id.password);
        description = findViewById(R.id.desc);
        address = findViewById(R.id.address);
        progress = findViewById(R.id.spin_kit);
        register_providerBtn = findViewById(R.id.register_providerBtn);
        register_providerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == register_providerBtn) {
            if (TextUtils.isEmpty(fname.getText()) || TextUtils.isEmpty(lname.getText()) || TextUtils.isEmpty(contact.getText())) {
                Toast.makeText(this, "Put details", Toast.LENGTH_SHORT).show();
            } else {
                progress.setVisibility(View.VISIBLE);
                globa_api_call.update_provider(fname.getText().toString(), lname.getText().toString(), password.getText().toString(), contact.getText().toString(), otherContact.getText().toString(), description.getText().toString(), address.getText().toString(), user_id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            Log.d("register_provider", "" + res);
                            JSONObject jsonObject = new JSONObject(res);
                            String error = jsonObject.getString("error");
                            if (error.equals("true")) {
                                fname.setText("");
                                lname.setText("");
                                password.setText("");
                                contact.setText("");
                                otherContact.setText("");
                                description.setText("");
                                address.setText("");
                            }
                            Toast.makeText(UpdateProviderInfoActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(UpdateProviderInfoActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}