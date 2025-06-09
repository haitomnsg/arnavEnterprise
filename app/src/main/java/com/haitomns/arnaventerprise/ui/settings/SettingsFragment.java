package com.haitomns.arnaventerprise.ui.settings;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haitomns.arnaventerprise.FullscreenViewActivity;
import com.haitomns.arnaventerprise.ImageAdapter;
import com.haitomns.arnaventerprise.databinding.FragmentSettingsBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private final String[] imageNames = {"Main Page.jpg","ENT.jpg", "PORCTOLOGY.jpg", "8W Bright laser.jpg", "8W Bright laser 2.jpg"};
    private final String folderPath = "imdslImages";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
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
            String label = name.substring(0, name.lastIndexOf('.'));
            formattedNames.add(label);
        }
        return formattedNames;
    }

    private void onImageClick(String imageName) {
        String[] imageCollectionImages;

        if (imageName.equals("ENT.jpg")) {
            imageCollectionImages = new String[]{"ENT_1.jpg", "ENT_2.jpg"};
        } else if (imageName.equals("Main Page.jpg")) {
            imageCollectionImages = new String[]{"Main Page_2.jpg", "Main Page_1.jpg"};
        } else if (imageName.equals("8W Bright laser.jpg")) {
            imageCollectionImages = new String[]{"8W Bright laser-1.jpg", "8W Bright laser-2.jpg", "8W Bright laser-3.jpg", "8W Bright laser-4.jpg", "8W Bright laser-5.jpg", "8W Bright laser-6.jpg", "8W Bright laser-7.jpg", "8W Bright laser-8.jpg", "8W Bright laser-9.jpg", "8W Bright laser-10.jpg"};
        }
        else if (imageName.equals("8W Bright laser 2.jpg")) {
            imageCollectionImages = new String[]{"8W Bright laser-11.jpg", "8W Bright laser-12.jpg", "8W Bright laser-13.jpg", "8W Bright laser-14.jpg", "8W Bright laser-15.jpg", "8W Bright laser-16.jpg", "8W Bright laser-17.jpg", "8W Bright laser-18.jpg", "8W Bright laser-19.jpg", "8W Bright laser-20.jpg"};
        }
        else {
            imageCollectionImages = new String[]{"PORCTOLOGY_1.jpg", "PORCTOLOGY_2.jpg", "PORCTOLOGY_3.jpg", "PORCTOLOGY_4.jpg"};
        }

        Intent intent = new Intent(getActivity(), FullscreenViewActivity.class);
        intent.putExtra("imageName", imageName);
        intent.putExtra("imagesCollection", imageCollectionImages);
        intent.putExtra("folderPath", folderPath);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
