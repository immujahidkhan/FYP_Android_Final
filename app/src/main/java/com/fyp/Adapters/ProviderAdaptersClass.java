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

import com.fyp.Activities.Details_Page;
import com.fyp.ModelsClass.Provider;
import com.fyp.R;

import java.util.ArrayList;

public class ProviderAdaptersClass extends RecyclerView.Adapter<ProviderAdaptersClass.ViewHolder> {

    Activity context;
    ArrayList<Provider> list;

    public ProviderAdaptersClass(Activity context, ArrayList<Provider> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Provider provider = list.get(position);
        holder.name.setText(provider.getName());
        holder.views.setText(provider.getViews() + " views");
        holder.description.setText(provider.getDescription());
        holder.city.setText(provider.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "" + provider.getUserId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Details_Page.class);
                intent.putExtra("user_id", "" + provider.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, views, description, city;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            views = itemView.findViewById(R.id.views);
            description = itemView.findViewById(R.id.description);
            city = itemView.findViewById(R.id.city);
        }
    }
}