package com.fyp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.Adapters.MySpinnerAdapter;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.ServiceList;
import com.fyp.ModelsClass.SpinnerModel;
import com.fyp.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.santalu.maskedittext.MaskEditText;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpServiceProviderActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner service_list;
    String spinnerId, spinnerText;
    SpinKitView progress;
    private ArrayList<ServiceList> students = new ArrayList<ServiceList>();
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;
    Button register_providerBtn;
    TextView signIn;
    EditText fname, lname, email, password, description, address;
    MaskEditText contact, otherContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_service_provider);
        getSupportActionBar().setTitle("Sign Up For Service Provider");
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
        fname = findViewById(R.id.fname);
        signIn = findViewById(R.id.signIn);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        otherContact = findViewById(R.id.othercontact);
        password = findViewById(R.id.password);
        description = findViewById(R.id.desc);
        address = findViewById(R.id.address);
        service_list = findViewById(R.id.service_list);
        progress = findViewById(R.id.spin_kit);
        register_providerBtn = findViewById(R.id.register_providerBtn);
        service_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* spinnerText = String.valueOf(students.get(position).getText());
                spinnerId = students.get(position).getId();*/
                //Toast.makeText(AddGroupLinkActivity.this, spinnerType, Toast.LENGTH_SHORT).show();
                if (parent.getId() == R.id.service_list) {
                    if (service_list != null && service_list.getSelectedItem() != null) {
                        spinnerId = students.get(position).getServiceId();
                        spinnerText = students.get(position).getName();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int position = 0;
                spinnerId = students.get(position).getServiceId();
                spinnerText = students.get(position).getName();
            }
        });
        loadServiceData();
        register_providerBtn.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    private void loadServiceData() {
        progress.setVisibility(View.VISIBLE);
        globa_api_call.getService_list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SpinnerModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SpinnerModel spinnerModel) {
                        ServiceList serviceList = new ServiceList();
                        serviceList.setName("Choose Service");
                        serviceList.setServiceId("0");
                        students.add(0, serviceList);
                        students = (ArrayList<ServiceList>) spinnerModel.getServiceList();
                        MySpinnerAdapter adapter2 = new MySpinnerAdapter(SignUpServiceProviderActivity.this, android.R.layout.simple_spinner_item, students);
                        service_list.setAdapter(adapter2);
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
    public void onClick(View v) {
        if (v == register_providerBtn) {
            if (TextUtils.isEmpty(fname.getText()) || TextUtils.isEmpty(lname.getText()) || TextUtils.isEmpty(email.getText())) {
                Toast.makeText(this, "Put details", Toast.LENGTH_SHORT).show();
            } else {
                String username = email.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show();
                } else {
                    progress.setVisibility(View.VISIBLE);
                    globa_api_call.register_provider(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), password.getText().toString(), contact.getText().toString(), otherContact.getText().toString(), description.getText().toString(), address.getText().toString(), spinnerId).enqueue(new Callback<ResponseBody>() {
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
                                    email.setText("");
                                    password.setText("");
                                    contact.setText("");
                                    otherContact.setText("");
                                    description.setText("");
                                    address.setText("");
                                }
                                Toast.makeText(SignUpServiceProviderActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SignUpServiceProviderActivity.this, "" + t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
        if (v == signIn) {
            finish();
        }
    }
}