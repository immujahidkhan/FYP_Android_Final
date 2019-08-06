package com.fyp.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.Activities.ProductsActivity;
import com.fyp.ApiCalling.GLOBA_API_CALL;
import com.fyp.ApiCalling.RetrofitClient;
import com.fyp.ModelsClass.ProductList;
import com.fyp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    Activity context;
    ArrayList<ProductList> lists;
    GLOBA_API_CALL globa_api_call;
    Retrofit retrofit;

    public ProductsAdapter(Activity context, ArrayList<ProductList> lists) {
        this.context = context;
        this.lists = lists;
        retrofit = RetrofitClient.getInstance();
        globa_api_call = retrofit.create(GLOBA_API_CALL.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductList productList = lists.get(position);
        holder.id.setText("Sr." + productList.getProduct_id());
        holder.name.setText("Name : " + productList.getName());
        holder.desc.setText("Description : " + productList.getDesc());
        holder.price.setText("Price : " + productList.getPrice());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] charSequence = new CharSequence[]{"Done", "Pending"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Updating")
                        .setSingleChoiceItems(charSequence, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // utility.toast(" "+charSequence);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lw = ((AlertDialog) dialog).getListView();
                                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                                funCall(checkedItem, productList.getProduct_id());
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globa_api_call.getProducts("delete_products.php?product_id=" + productList.getProduct_id()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String res = response.body().string();
                                JSONObject jsonObject = new JSONObject(res);
                                Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            notifyDataSetChanged();
                            lists.remove(position);
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

    private void funCall(Object checkedItem, String product_id) {
        globa_api_call.getProducts("update_products.php?status=" + checkedItem + "&product_id=" + product_id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String res = response.body().string();
                        JSONObject jsonObject = new JSONObject(res);
                        Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
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

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, desc, price;
        Button delete, edit;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.desc);
            price = itemView.findViewById(R.id.price);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}