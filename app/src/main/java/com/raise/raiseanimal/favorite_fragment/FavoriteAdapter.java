package com.raise.raiseanimal.favorite_fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.makeramen.roundedimageview.RoundedImageView;
import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.favorite_fragment.tag_presenter.TagPresenter;
import com.raise.raiseanimal.favorite_fragment.tag_presenter.TagPresenterImpl;
import com.raise.raiseanimal.tool.ImageLoaderManager;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ArrayList<AnimalFavorite> dataArray;

    private Context context;

    private boolean isOpen = false;

    private ArrayList<Boolean> isOpenArray;

    private int mLayoutHeight;

    public FavoriteAdapter(Context context) {
        this.context = context;
        isOpenArray = new ArrayList<>();
    }

    private OnFavoriteItemClickListener listener;

    public void setOnFavoriteItemClickListener(OnFavoriteItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<AnimalFavorite> dataArray,ArrayList<Boolean> isOpenArray) {
        this.dataArray = dataArray;
        this.isOpenArray = isOpenArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AnimalFavorite data = dataArray.get(position);
        ImageLoaderManager.getInstance(context).setPhotoUrl(data.getPhoto(), holder.ivPhoto);
        if (data.getName().isEmpty()) {
            holder.tvName.setText("狗狗");
        } else {
            holder.tvName.setText(data.getName());
        }

        //可收式INFO VIEW
        TagPresenter presenter = new TagPresenterImpl();
        presenter.setData(data);
        final TagAdapter adapter = new TagAdapter(context);
        adapter.setPresenter(presenter);
        holder.recyclerView.setAdapter(adapter);



        holder.ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onHeartClick(data);
            }
        });

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShareClick(data);
            }
        });

        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhoneClick();
            }
        });

        if (isOpenArray.get(position)){
            holder.ivOpen.setImageResource(R.drawable.up_arrow);
            holder.recyclerView.setVisibility(View.VISIBLE);
        }else {
            holder.ivOpen.setImageResource(R.drawable.down_arrow);
            holder.recyclerView.setVisibility(View.GONE);
        }
        
        holder.ivOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                listener.onOpenInfoClick();
                isOpenArray.set(position,!isOpenArray.get(position));
                notifyItemChanged(position);
            }
        });

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoClick(data);
            }
        });
        holder.clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPhotoClick(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataArray == null ? 0 : dataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView ivPhoto;

        private TextView tvName;

        private ConstraintLayout clickArea;

        private ImageView ivHeart, ivShare, ivPhone, ivOpen;

        private RecyclerView recyclerView;

        private LinearLayout infoMask;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clickArea = itemView.findViewById(R.id.favorite_click_area);
            ivPhoto = itemView.findViewById(R.id.favorite_item_photo);
            tvName = itemView.findViewById(R.id.favorite_item_name);
            ivHeart = itemView.findViewById(R.id.favorite_item_heart);
            ivShare = itemView.findViewById(R.id.favorite_item_share);
            ivPhone = itemView.findViewById(R.id.favorite_item_phone);
            ivOpen = itemView.findViewById(R.id.favorite_item_open_info);
            recyclerView = itemView.findViewById(R.id.favorite_tag_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            infoMask = itemView.findViewById(R.id.favorite_item_info_mask);


            if (recyclerView.getItemAnimator() != null){
                ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
                recyclerView.getItemAnimator().setChangeDuration(500);
                recyclerView.getItemAnimator().setMoveDuration(500);
            }

        }
    }

    public interface OnFavoriteItemClickListener {
        void onPhotoClick(AnimalFavorite data);

        void onHeartClick(AnimalFavorite data);

        void onShareClick(AnimalFavorite data);

        void onPhoneClick();

        void onOpenInfoClick();

    }
}
