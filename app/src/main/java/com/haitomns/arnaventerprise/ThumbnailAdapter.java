package com.haitomns.arnaventerprise;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> {
    private final List<Bitmap> thumbnails;
    private final Context context;
    private final OnThumbnailClickListener listener;

    public interface OnThumbnailClickListener {
        void onThumbnailClick(int position);
    }

    public ThumbnailAdapter(Context context, List<Bitmap> thumbnails, OnThumbnailClickListener listener) {
        this.context = context;
        this.thumbnails = thumbnails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thumbnail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(thumbnails.get(position));
        holder.itemView.setOnClickListener(v -> listener.onThumbnailClick(position));
    }

    @Override
    public int getItemCount() {
        return thumbnails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.thumbnail_image);
        }
    }
}
