package com.haitomns.arnaventerprise.ui.gallery;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.haitomns.arnaventerprise.ImageAdapter;
import com.haitomns.arnaventerprise.databinding.FragmentGalleryBinding;
import com.haitomns.arnaventerprise.FullscreenViewActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private final String[] imageNames = {"RARICAP FORTE.jpg","CLARIMYCIN.jpg", "BAFTOR.jpg", "RANDIP.jpg", "AZIBIAL.jpg"};
    private final String folderPath = "bafanaImages";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        ArrayList<Bitmap> images = loadImagesFromAssets(imageNames);
        ArrayList<String> imageLabels = formatImageNames(imageNames); // Extracted names without extensions

        ImageAdapter adapter = new ImageAdapter(images, imageLabels, position -> {
            // Call onImageClick when an image is clicked
            onImageClick(imageNames[position]);
        });

        // Check device orientation and set the layout manager accordingly
        int orientation = getResources().getConfiguration().orientation;
        int spanCount = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? 5 : 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        recyclerView.setAdapter(adapter);

        return root;
    }

    private ArrayList<Bitmap> loadImagesFromAssets(String[] names) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        AssetManager assetManager = getContext().getAssets();

        for (String name : names) {
            try (InputStream is = assetManager.open(folderPath + "/" + name)) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                bitmaps.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmaps;
    }

    private ArrayList<String> formatImageNames(String[] names) {
        ArrayList<String> formattedNames = new ArrayList<>();
        for (String name : names) {
            formattedNames.add(name.substring(0, name.lastIndexOf('.')));
        }
        return formattedNames;
    }

    private void onImageClick(String imageName) {
        String[] imageCollectionImages = new String[] {"AZIBIAL.jpg", "BAFTOR.jpg", "RANDIP.jpg", "RARICAP FORTE.jpg", "CLARIMYCIN.jpg"};

        if (imageName.equals("AZIBIAL.jpg")) {
            imageCollectionImages = new String[]{"AZIBIAL.jpg", "BAFTOR.jpg", "RANDIP.jpg", "RARICAP FORTE.jpg", "CLARIMYCIN.jpg"};
        }
        else if (imageName.equals("BAFTOR.jpg")) {
            imageCollectionImages = new String[]{"BAFTOR.jpg", "AZIBIAL.jpg", "RANDIP.jpg", "RARICAP FORTE.jpg", "CLARIMYCIN.jpg"};
        }
        else if (imageName.equals("RANDIP.jpg")) {
            imageCollectionImages = new String[]{"RANDIP.jpg", "AZIBIAL.jpg", "BAFTOR.jpg", "RARICAP FORTE.jpg", "CLARIMYCIN.jpg"};
        }
        else if (imageName.equals("RARICAP FORTE.jpg")) {
            imageCollectionImages = new String[]{"RARICAP FORTE.jpg", "AZIBIAL.jpg", "BAFTOR.jpg", "RANDIP.jpg", "CLARIMYCIN.jpg"};
        }
        else {
            imageCollectionImages = new String[]{"CLARIMYCIN.jpg", "AZIBIAL.jpg", "BAFTOR.jpg", "RANDIP.jpg", "RARICAP FORTE.jpg"};
        }

        Intent intent = new Intent(getContext(), FullscreenViewActivity.class);
        intent.putExtra("imageName", imageName);
        intent.putExtra("imagesCollection", imageCollectionImages);
        intent.putExtra("folderPath", "bafanaImages");
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}