package com.haitomns.arnaventerprise.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.haitomns.arnaventerprise.R;
import com.haitomns.arnaventerprise.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find image views and set click listeners
        ImageView suturePlanet = view.findViewById(R.id.suture_planet);
        ImageView bafnaLogo = view.findViewById(R.id.bafnaLogo);
        ImageView puristoLogo = view.findViewById(R.id.puristo);
        ImageView imdslLogo = view.findViewById(R.id.imdsl);
        ImageView virchowLogo = view.findViewById(R.id.virchowLogo);

        NavController navController = Navigation.findNavController(view);

        suturePlanet.setOnClickListener(v -> {
            navController.navigate(R.id.nav_home); // Navigate to gallery fragment
        });

        bafnaLogo.setOnClickListener(v -> {
            navController.navigate(R.id.nav_gallery); // Navigate to slideshow fragment
        });

        puristoLogo.setOnClickListener(v -> {
            navController.navigate(R.id.nav_slideshow); // Navigate to slideshow fragment
        });

        imdslLogo.setOnClickListener(v -> {
            navController.navigate(R.id.nav_setting); // Navigate to slideshow fragment
        });

        virchowLogo.setOnClickListener(v -> {
            navController.navigate(R.id.nav_account); // Navigate to slideshow fragment
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}