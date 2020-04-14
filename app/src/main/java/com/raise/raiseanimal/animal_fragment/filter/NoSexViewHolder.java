package com.raise.raiseanimal.animal_fragment.filter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;

import java.util.ArrayList;

public class NoSexViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;
    private Context context;

    private TextView tvTitle;

    private FilterItemAdapter.OnFilterItemClickListener listener;

    public void setOnFilterClickListener(FilterItemAdapter.OnFilterItemClickListener listener){
        this.listener = listener;
    }

    public NoSexViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        recyclerView = itemView.findViewById(R.id.filter_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        tvTitle = itemView.findViewById(R.id.filter_title);
        tvTitle.setText(context.getString(R.string.no_sex));
    }

    public void setData(ArrayList<String> noSexArray) {
        final FilterItemAdapter adapter = new FilterItemAdapter(noSexArray,context);
        recyclerView.setAdapter(adapter);
        adapter.setOnFilterItemClickListener(new FilterItemAdapter.OnFilterItemClickListener() {
            @Override
            public void onClick(String name,String value) {
                Log.i("Michael","篩選點擊 : "+name+" , 類型 : "+value);
                adapter.notifyDataSetChanged();
                listener.onClick(name,value);
            }
        });


    }
}
