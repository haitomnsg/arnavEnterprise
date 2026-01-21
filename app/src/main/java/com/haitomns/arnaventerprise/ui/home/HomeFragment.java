package com.haitomns.arnaventerprise.ui.home;

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
import com.haitomns.arnaventerprise.databinding.FragmentHomeBinding;
import com.haitomns.arnaventerprise.FullscreenViewActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final String[] imageNames = {"SP Promo.jpg", "Main SP Promo.jpeg", "ULTRANET.jpg", "NETPTFE.jpg", "NETPLER.jpg", "V-FIX.jpg", "Hemonet.jpg", "CMESH.jpg", "Trocar.jpg", "NETBOND.jpeg"};
    private final String folderPath = "suturePlanetImages";


    private List<Bitmap> images;
    private List<String> imageLabels;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
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

        if(imageName.equals("SP Promo.jpg")){
            imageCollectionImages = new String[]{"SP Promo (1).jpg", "SP Promo (2).jpg"};
        }
        else if(imageName.equals("V-FIX.jpg")){
            imageCollectionImages = new String[]{"V-FIX_1.jpg", "V-FIX_2.jpg"};
        }
        else if (imageName.equals("ULTRANET.jpg")) {
            imageCollectionImages = new String[]{"ULTRANET_1.jpg", "ULTRANET_2.jpg"};
        }
        else if(imageName.equals("NETPTFE.jpg")){
            imageCollectionImages = new String[]{"NETPTFE_1.jpg", "NETPTFE_2.jpg", "NETPTFE_3.jpg", "NETPTFE_4.jpg", "NETPTFE_5.jpg", "NETPTFE_6.jpg", "NETPTFE_7.jpg", "NETPTFE_8.jpg"};
        }
        else if(imageName.equals("NETPLER.jpg")){
            imageCollectionImages = new String[]{"NETPLER_1.jpg", "NETPLER_2.jpg"};
        }
        else if (imageName.equals("CMESH.jpg")) {
            imageCollectionImages = new String[]{"C-MESH_1.jpg", "C-MESH_2.jpg", "C-MESH_3.jpg", "C-MESH_4.jpg", "C-MESH_5.jpg", "C-MESH_6.jpg", "C-MESH_7.jpg", "C-MESH_8.jpg", "C-MESH_9.jpg", "C-MESH_10.jpg", "C-MESH_11.jpg", "C-MESH_12.jpg", "C-MESH_13.jpg", "C-MESH_14.jpg", "C-MESH_15.jpg", "C-MESH_16.jpg"};
        }
        else if (imageName.equals("Hemonet.jpg")) {
            imageCollectionImages = new String[]{"Hemonet_1.jpg", "Hemonet_2.jpg"};
        }
        else if (imageName.equals("Main SP Promo.jpeg")) {
            imageCollectionImages = new String[]{"Netcryl (1).jpeg", "Netcryl (2).jpeg", "Netcryl (3).jpeg", "Netcryl (4).jpeg", "Netcryl (5).jpeg", "Netcryl (6).jpeg", "Netcryl (7).jpeg", "Netcryl (8).jpeg", "Netcryl (9).jpeg", "Netcryl (10).jpeg", "Netcryl (11).jpeg", "Netcryl (12).jpeg", "Netcryl (13).jpeg", "Netcryl (14).jpeg", "Netcryl (15).jpeg", "Netcryl (16).jpeg", "Netcryl (17).jpeg", "Netcryl (18).jpeg", "Netcryl (19).jpeg", "Netcryl (20).jpeg", "Netcryl (21).jpeg", "Netcryl (22).jpeg", "Netcryl (23).jpeg"};
        }
        else if (imageName.equals("Trocar.jpg")){
            imageCollectionImages = new String[]{"Trocar_1.jpg", "Trocar_2.jpg", "Trocar_3.jpg", "Trocar_4.jpg"};
        }
        else if (imageName.equals("NETBOND.jpeg")){
            imageCollectionImages = new String[]{"NETBOND.jpeg"};
        }
        else {
            imageCollectionImages = new String[]{"ABSORBABLE.jpg", "NON-ABSORBABLE.jpg", "NETFIX.jpg"};
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
