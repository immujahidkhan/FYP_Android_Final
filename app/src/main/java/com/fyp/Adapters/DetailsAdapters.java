package com.fyp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.Activities.Details_Page;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.GlobalInfo;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.DetailsModel;
import com.fyp.ModelsClass.Provider;
import com.fyp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsAdapters extends RecyclerView.Adapter<DetailsAdapters.ViewHolder> {

    Activity context;
    ArrayList<DetailsModel> list;
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;

    public DetailsAdapters(Activity context, ArrayList<DetailsModel> list) {
        this.context = context;
        this.list = list;
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailsModel provider = list.get(position);
        holder.username.setText(provider.getUsername());
        holder.address.setText(provider.getAddress());
        holder.title.setText(provider.getProduct_name());
        holder.description.setText(provider.getProduct_description());
        holder.user_desc.setText(provider.getUser_desc());
        holder.username.setText(provider.getUsername());
        holder.price.setText(provider.getProduct_price());
        //Glide.with(context).load(GlobalInfo.IMAGE_PATH + provider.getImg()).into(holder.img);
        Picasso.with(context).load(GlobalInfo.IMAGE_PATH + provider.getImg()).error(R.drawable.logo).into(holder.img);
        holder.addCArd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "" + provider.getProduct_id(), Toast.LENGTH_SHORT).show();
                globa_api_call.addToCart(GlobalInfo.Addtocart + provider.getProduct_id()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                Toast.makeText(context, response.body().string(), Toast.LENGTH_SHORT).show();
                                //Log.d("ADDTMDF", response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, address, user_desc, title, description, price;
        ImageView img, addCArd;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            img = itemView.findViewById(R.id.img);
            addCArd = itemView.findViewById(R.id.addCArd);
            address = itemView.findViewById(R.id.address);
            user_desc = itemView.findViewById(R.id.user_desc);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
        }
    }
}