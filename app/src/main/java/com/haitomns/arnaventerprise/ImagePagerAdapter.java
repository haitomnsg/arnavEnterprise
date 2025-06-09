package com.haitomns.arnaventerprise;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private Context context;
    private List<Bitmap> imageBitmaps;
    private List<String> imageNames; // Add this to hold the image names
    private String folderPath; // Add this to hold the folder path

    private String[] imageCollection;

    // Update the constructor to accept image names and folder path
    public ImagePagerAdapter(Context context, List<Bitmap> imageBitmaps, List<String> imageNames, String folderPath, String[] imageCollection) {
        this.context = context;
        this.imageBitmaps = imageBitmaps;
        this.imageNames = imageNames;
        this.folderPath = folderPath;
        this.imageCollection = imageCollection;
    }

    @Override
    public int getCount() {
        return imageBitmaps.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_page, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageBitmap(imageBitmaps.get(position));

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SingleImageFullscreenActivity.class);
            intent.putExtra("imageCollection", imageCollection); // Pass the entire image collection
            intent.putExtra("folderPath", folderPath);           // Pass the folder path
            intent.putExtra("position", position);               // Pass the starting position
            context.startActivity(intent);
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
