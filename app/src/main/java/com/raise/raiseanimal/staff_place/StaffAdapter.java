package com.raise.raiseanimal.staff_place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.tool.ImageLoaderManager;

import java.util.ArrayList;
import java.util.Locale;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private ArrayList<AnimalObject> dataArray;

    private Context context;

    private OnAnimalItemClickListener listener;

    public void setOnAnimalItemClickListener(OnAnimalItemClickListener listener){
        this.listener = listener;
    }

    public StaffAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.animal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AnimalObject data = dataArray.get(position);


        if (data.getAlbumFile() == null || data.getAlbumFile().isEmpty()){
            holder.ivPhoto.setBackground(ContextCompat.getDrawable(context,R.drawable.story_shape));
            holder.ivPhoto.setImageResource(R.drawable.no_pic);
        }else {
            holder.ivPhoto.setBackground(null);
            ImageLoaderManager.getInstance(context).setPhotoUrl(data.getAlbumFile(),holder.ivPhoto);
        }

        if (data.getAnimalTitle().isEmpty()){
            holder.tvTitle.setText(data.getAnimalId());
        }else {
            holder.tvTitle.setText(String.format(Locale.getDefault(),"%s %s",data.getAnimalId(),data.getAnimalTitle()));
        }

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(data,position);
            }
        });

        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavorite(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArray == null ? 0 : dataArray.size();
    }

    public void setData(ArrayList<AnimalObject> dataArray) {
        this.dataArray =dataArray;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView ivPhoto;

        private TextView tvTitle;

        private ConstraintLayout clickArea;

        private ImageView ivFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.animal_item_photo);
            tvTitle= itemView.findViewById(R.id.animal_item_name);
            clickArea = itemView.findViewById(R.id.animal_item_click_area);
            ivFavorite = itemView.findViewById(R.id.animal_item_heart);
            ivFavorite.setVisibility(View.GONE);
        }
    }

    public interface OnAnimalItemClickListener{
        void onClick(AnimalObject data,int itemPosition);
        void onFavorite(AnimalObject data);
    }
}
