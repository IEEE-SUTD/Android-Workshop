package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private ArrayList<ListItem> itemList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = itemList.get(position);
        holder.title.setText(listItem.getTitle());
        holder.desc.setText(listItem.getDesc());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public RecyclerAdapter(ArrayList<ListItem> itemList) {
        this.itemList = itemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.desc = itemView.findViewById(R.id.desc);
        }
    }


}
