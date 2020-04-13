package com.raise.raiseanimal.detail_activity.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;
import com.raise.raiseanimal.tool.ImageLoaderManager;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivPhoto;

    private Context context;

    public PhotoViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        ivPhoto = itemView.findViewById(R.id.detail_photo);
    }

    public void setData(String albumFile) {
        ImageLoaderManager.getInstance(context).setPhotoUrl(albumFile,ivPhoto);
    }
}
