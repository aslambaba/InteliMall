package com.example.sairamkrishna.intelimall.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sairamkrishna.intelimall.HomeDrawer;
import com.example.sairamkrishna.intelimall.MainActivity;
import com.example.sairamkrishna.intelimall.R;
import com.example.sairamkrishna.intelimall.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        final EditText username = root.findViewById(R.id.username_et);
        final EditText phone = root.findViewById(R.id.phone_et);
        final EditText address = root.findViewById(R.id.address_et);
        final EditText email = root.findViewById(R.id.email_et);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), (Observer<User>) s -> {
                    username.setText(s.getUsername());
                    phone.setText(s.getPhone());
                    address.setText(s.getAddress());
                    email.setText(s.getEmail());
                }
        );


        return root;


    }
}