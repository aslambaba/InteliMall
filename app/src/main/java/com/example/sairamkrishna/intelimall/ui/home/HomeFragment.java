package com.example.sairamkrishna.intelimall.ui.home;

import static com.example.sairamkrishna.intelimall.Constant.globalArraylist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sairamkrishna.intelimall.R;

import com.example.sairamkrishna.intelimall.adapters.ProductAdapter;
import com.example.sairamkrishna.intelimall.listner.SimpleClickListener;
import com.example.sairamkrishna.intelimall.models.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements TextWatcher, SimpleClickListener {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private List<Products> productsList;
    private ProductAdapter productAdapter;
    private EditText productSearchET;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "name";
    public static final String Phone = "price";
    SharedPreferences sharedpreferences;

    List<Integer> list = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView SeeAllCate = root.findViewById(R.id.seeallcate);
        globalArraylist.clear();
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//         SeeAllCate.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                Intent it = new Intent(HomeDrawer.this, MainCategories.class);
//                startActivity(it);
//             }
//         });
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview_products);
        productSearchET = root.findViewById(R.id.product_search_ET);
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productsList = new ArrayList<>();

        Log.d("TAG", "onDataChange: @@R1 "+productsList.size());
        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("Products");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        Products l = npsnapshot.getValue(Products.class);
                        productsList.add(l);
                    }
                    productAdapter = new ProductAdapter(getContext(), productsList);
                    recyclerView.setAdapter(productAdapter);
                    Log.d("TAG", "onDataChange: @@R2 "+productsList.size());


                }else {

                    Log.d("TAG", "onDataChange: @@R3 "+productsList.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("TAG", "onDataChange: @@R4 "+databaseError.getMessage());
            }
        });
        productSearchET.addTextChangedListener(this);
        return root;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!TextUtils.isEmpty(charSequence))
            productAdapter.getFilter().filter(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(int id) {
        list.add(id);
        Toast.makeText(getContext(), "Added to Cart"+list.size(), Toast.LENGTH_SHORT).show();
    }
}