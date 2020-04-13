package com.raise.raiseanimal.detail_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;

import java.util.ArrayList;

public class TagViewAdapter extends RecyclerView.Adapter<TagViewAdapter.ViewHolder> {

    private ArrayList<String> tagArray;

    private Context context;

    public TagViewAdapter(ArrayList<String> tagArray, Context context) {
        this.tagArray = tagArray;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = tagArray.get(position);

        holder.tvTag.setText(title);

    }

    @Override
    public int getItemCount() {
        return tagArray == null ? 0 : tagArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tag_item_title);
        }
    }
}
