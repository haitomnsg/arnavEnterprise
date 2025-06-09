package com.haitomns.arnaventerprise.ui.slideshow;

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

import com.haitomns.arnaventerprise.FullscreenViewActivity;
import com.haitomns.arnaventerprise.ImageAdapter;
import com.haitomns.arnaventerprise.databinding.FragmentSlideshowBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private final String[] imageNames = {"GYN.jpg", "ORTHO.jpg", "PAEDIATRIC.jpg", "PHYSICIAN.jpg"};
    private final String folderPath = "puristroImages";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
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

        if(imageName.equals("GYN.jpg")){
            imageCollectionImages = new String[]{"PURISTOR_1.jpg","GYN_1.jpg", "GYN_2.jpg", "GYN_3.jpg", "GYN_4.jpg", "GYN_5.jpg", "GYN_6.jpg", "GYN_7.jpg", "PURISTOR_2.jpg"};
        } else if (imageName.equals("ORTHO.jpg")) {
            imageCollectionImages = new String[]{"PURISTOR_1.jpg", "ORTHO_1.jpg", "ORTHO_2.jpg", "ORTHO_3.jpg", "PURISTOR_2.jpg"};
        } else if (imageName.equals("PAEDIATRIC.jpg")) {
            imageCollectionImages = new  String[]{"PURISTOR_1.jpg", "PAEDIATRIC_1.jpg", "PAEDIATRIC_2.jpg", "PURISTOR_2.jpg"};
        } else {
            imageCollectionImages = new String[]{"PURISTOR_1.jpg", "PHYSICIAN_1.jpg", "PHYSICIAN_2.jpg", "PHYSICIAN_3.jpg", "PHYSICIAN_4.jpg", "PURISTOR_2.jpg"};
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