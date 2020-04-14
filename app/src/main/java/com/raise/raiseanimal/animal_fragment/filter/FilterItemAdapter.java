package com.raise.raiseanimal.animal_fragment.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;

import java.util.ArrayList;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.ViewHolder> {

    private ArrayList<String> dataArray;

    private ArrayList<String> catchArray;

    private Context context;

    private int userPressPosition;

    private OnFilterItemClickListener listener;

    public void setOnFilterItemClickListener(OnFilterItemClickListener listener) {
        this.listener = listener;
    }

    public FilterItemAdapter(ArrayList<String> dataArray, Context context) {

        this.dataArray = dataArray;

        catchArray = new ArrayList<>();
        for (int i = 1; i < dataArray.size(); i++) {
            catchArray.add(dataArray.get(i));
        }

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.filter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String name = catchArray.get(position);
        holder.tvName.setText(name);

        if (userPressPosition == position) {
            holder.tvName.setBackground(ContextCompat.getDrawable(context, R.drawable.tag_item_shape));
        } else {
            holder.tvName.setBackground(null);
        }
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPressPosition = position;
                listener.onClick(name,dataArray.get(0));
            }
        });
    }

    @Override
    public int getItemCount() {
        return catchArray == null ? 0 : catchArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.filter_item_title);
        }
    }

    public interface OnFilterItemClickListener {
        void onClick(String name,String value);
    }
}
