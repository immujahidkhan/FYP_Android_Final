package com.fyp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.Orders;
import com.fyp.ModelsClass.OrdersList;
import com.fyp.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OrdersAdaptersClass extends RecyclerView.Adapter<OrdersAdaptersClass.ViewHolder> {

    Activity context;
    ArrayList<OrdersList> list;
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;

    public OrdersAdaptersClass(Activity context, ArrayList<OrdersList> list) {
        this.context = context;
        this.list = list;
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrdersList provider = list.get(position);
        holder.title.setText(provider.getName());
        holder.price.setText("Rs." + provider.getUserId());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globa_api_call.removeCart("remove_cart.php?id=" + provider.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                Toast.makeText(context, "" + response.body().string(), Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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
        TextView title, price;
        ImageView remove;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}