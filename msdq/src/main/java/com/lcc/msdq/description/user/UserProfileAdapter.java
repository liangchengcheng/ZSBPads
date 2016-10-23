package com.lcc.msdq.description.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lcc.msdq.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zsbpj.lccpj.frame.ImageManager;


public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int PHOTO_ANIMATION_DELAY = 600;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();


    private final Context context;
    private final int cellSize;

    private final List<String> photos;

    private boolean lockedAnimations = false;
    private int lastAnimatedItem = -1;

    public UserProfileAdapter(Context context) {
        this.context = context;
        this.cellSize = UserUtils.getScreenWidth(context) / 3;
        this.photos = Arrays.asList(context.getResources().getStringArray(R.array.user_photos));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false);
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        layoutParams.height = cellSize;
        layoutParams.width = cellSize;
        layoutParams.setFullSpan(false);
        view.setLayoutParams(layoutParams);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindPhoto((PhotoViewHolder) holder, position);
    }

    private void bindPhoto(final PhotoViewHolder holder, int position) {
        ImageManager.getInstance().loadUrlImage(context,photos.get(position),holder.ivPhoto);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.flRoot)
        FrameLayout flRoot;
        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;

        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setLockedAnimations(boolean lockedAnimations) {
        this.lockedAnimations = lockedAnimations;
    }
}
