package com.haitomns.arnaventerprise;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SingleImageFullscreenActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int startPosition;
    Context context = this;
    List<Bitmap> categoryImages; // List of bitmaps for images in the selected category
    List<String> imageNames;     // List of names for each image in the selected category
    String folderPath;
    String[] imageCollection;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_fullscreen);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewPager = findViewById(R.id.viewPager);

        // Retrieve the image collection and folder path from the intent
        folderPath = getIntent().getStringExtra("folderPath");
        imageCollection = getIntent().getStringArrayExtra("imageCollection");
        startPosition = getIntent().getIntExtra("position", 0);

        loadCategoryImages(folderPath, imageCollection);
        setupViewPager();

        // Initialize GestureDetector to listen for single taps
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //echo that the single tap was detected
                Toast.makeText(context, "Tap Detected", Toast.LENGTH_SHORT).show();
                return super.onSingleTapConfirmed(e);
            }
        });

        viewPager.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    private void loadCategoryImages(String folderPath, String[] imageCollection) {
        categoryImages = new ArrayList<>();
        for (String imageName : imageCollection) {
            try {
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open(folderPath + "/" + imageName);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                categoryImages.add(bitmap);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager() {
        ZoomableImageAdapter adapter = new ZoomableImageAdapter(context, categoryImages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPosition); // Start from the clicked image
    }
}
