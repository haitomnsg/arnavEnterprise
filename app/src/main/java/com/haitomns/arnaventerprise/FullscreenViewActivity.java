package com.haitomns.arnaventerprise;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FullscreenViewActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RecyclerView thumbnailRecyclerView;
    private List<Bitmap> imageBitmaps; // Store the bitmaps of images
    private String folderPath;
    private int currentPosition;
    private List<String> imageNames; // Add this line

    private String[] imageCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        // Enable the up button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewPager = findViewById(R.id.viewPager);
        thumbnailRecyclerView = findViewById(R.id.recyclerViewThumbnails);

        // Retrieve data from Intent
        folderPath = getIntent().getStringExtra("folderPath");
        imageCollection = getIntent().getStringArrayExtra("imagesCollection");
        currentPosition = getIntent().getIntExtra("position", 0); // Default to the first image

        // Check if imageCollection is valid
        if (imageCollection != null && imageCollection.length > 0) {
            loadBitmaps(imageCollection); // Load bitmaps for all images
            setupViewPager();
            setupThumbnailRecyclerView();
        } else {
            Toast.makeText(this, "No images found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadBitmaps(String[] imageCollection) {
        imageBitmaps = new ArrayList<>();
        imageNames = new ArrayList<>(); // Initialize the imageNames list
        for (String imageName : imageCollection) {
            try {
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open(folderPath + "/" + imageName);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageBitmaps.add(bitmap);
                imageNames.add(imageName); // Add the image name to the list
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image: " + imageName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupViewPager() {
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageBitmaps, imageNames, folderPath, imageCollection);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition); // Set the current item to the clicked position
    }

    private void setupThumbnailRecyclerView() {
        thumbnailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(this, imageBitmaps, position -> {
            currentPosition = position;
            viewPager.setCurrentItem(currentPosition); // Update ViewPager to selected image
        });
        thumbnailRecyclerView.setAdapter(thumbnailAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Handle back button press
            return true;
        }

        //free the bitmap resources
        for (Bitmap bitmap : imageBitmaps) {
            if (bitmap != null) {
                bitmap.recycle(); // Recycle each bitmap to free memory
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Free resources when the activity is destroyed
        for (Bitmap bitmap : imageBitmaps) {
            if (bitmap != null) {
                bitmap.recycle(); // Recycle each bitmap to free memory
            }
        }
        viewPager.setAdapter(null); // Clear adapter
    }
}
