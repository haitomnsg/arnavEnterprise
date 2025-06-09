package com.haitomns.arnaventerprise.ui.account;

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
import com.haitomns.arnaventerprise.databinding.FragmentAccountBinding;
import com.haitomns.arnaventerprise.FullscreenViewActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    private final String[] imageNames = {"Osteotide.jpg", "Hyroth.jpg", "Hyroth XL.jpg", "Tramense.jpg"};

    private final String folderPath = "virchowImages";
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;
        ArrayList<Bitmap> images = loadImagesFromAssets(imageNames);
        ArrayList<String> imageLabels = formatImageNames(imageNames);

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
        String[] imageCollectionImages;

        if(imageName.equals("Osteotide.jpg")){
            imageCollectionImages = new String[]{"Osteotide_4.jpg", "Osteotide_3.jpg", "Osteotide_1.jpg", "Osteotide_2.jpg"};
        }else if(imageName.equals("Hyroth.jpg")){
            imageCollectionImages = new String[]{"Hyroth.jpg"};
        }
        else if(imageName.equals("Hyroth XL.jpg")){
            imageCollectionImages = new String[]{"Hyroth XL_3.jpg", "Hyroth XL_1.jpg", "Hyroth XL_2.jpg"};
        }
        else if(imageName.equals("Tramense.jpg")){
            imageCollectionImages = new String[]{"Tramense.jpg"};
        }
        else{
            imageCollectionImages = new String[]{"Osteotide_1.jpg", "Osteotide_2.jpg", "Osteotide_3.jpg", "Osteotide_4.jpg"};
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