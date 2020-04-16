package com.raise.raiseanimal.animal_fragment;

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
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.tool.ImageLoaderManager;

import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    private ArrayList<AnimalObject> dataArray;

    private Context context;

    private OnAnimalItemClickListener listener;

    private ArrayList<AnimalFavorite> favArray;

    public void setOnAnimalItemClickListener(OnAnimalItemClickListener listener){
        this.listener = listener;
    }

    public AnimalAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.animal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AnimalObject data = dataArray.get(position);

        //設定是否有最愛
        if (favArray != null && favArray.size() != 0){
            for (AnimalFavorite fav : favArray){
                if (fav.getNumber() == data.getAnimalId()){
                    holder.ivFavorite.setImageResource(R.drawable.heart_pressed);
                    break;
                }else {
                    holder.ivFavorite.setImageResource(R.drawable.heart_not_pressed);
                }
            }
        }
        if (data.getAlbumFile() == null || data.getAlbumFile().isEmpty()){
            holder.ivPhoto.setBackground(ContextCompat.getDrawable(context,R.drawable.story_shape));
        }else {
            holder.ivPhoto.setBackground(null);
            ImageLoaderManager.getInstance(context).setPhotoUrl(data.getAlbumFile(),holder.ivPhoto);
        }

        if (data.getAnimalTitle().isEmpty()){
            holder.tvTitle.setText("未命名");
        }else {
            holder.tvTitle.setText(data.getAnimalTitle());
        }

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(data);
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

    public void setFavoriteData(ArrayList<AnimalFavorite> favArray){
        this.favArray = favArray;
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
        }
    }

    public interface OnAnimalItemClickListener{
        void onClick(AnimalObject data);
        void onFavorite(AnimalObject data);
    }
}
