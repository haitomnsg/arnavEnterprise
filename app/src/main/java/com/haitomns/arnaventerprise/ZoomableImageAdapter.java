package com.haitomns.arnaventerprise;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import java.util.List;

public class ZoomableImageAdapter extends PagerAdapter {

    private Context context;
    private List<Bitmap> categoryImages;

    public ZoomableImageAdapter(Context context, List<Bitmap> categoryImages) {
        this.context = context;
        this.categoryImages = categoryImages;
    }

    @Override
    public int getCount() {
        return categoryImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.image_fullscreen, container, false);

        // Set up PhotoView with bitmap
        PhotoView photoView = itemView.findViewById(R.id.fullscreenImageView);
        photoView.setImageBitmap(categoryImages.get(position));

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
