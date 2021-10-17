package com.example.sairamkrishna.intelimall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.sairamkrishna.intelimall.adapters.ItemsCustomeAdpater;

public class CartItems extends AppCompatActivity {

    ImageView img_back;
    RecyclerView recyclerView;
    ItemsCustomeAdpater customeAdpater;

    String[] arraySpinner = new String[] {
            "1", "2", "3", "4", "5", "6", "7","8","9","10"
    };
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_items);
        img_back=findViewById(R.id.img_back);
        recyclerView=findViewById(R.id.recyclerview_carproducts);
        Button order=findViewById(R.id.order);
        order.setOnClickListener(v->{
            Toast.makeText(getBaseContext(),"Order Placed Now",Toast.LENGTH_SHORT).show();
        });
        img_back.setOnClickListener(v->{
            finish();
        });
        s= findViewById(R.id.spinner);
        if(Constant.globalArraylist.size()>0) {
            customeAdpater = new ItemsCustomeAdpater(getBaseContext());
            recyclerView.setAdapter(customeAdpater);
            order.setVisibility(View.VISIBLE);
        }else
        {
            Toast.makeText(getBaseContext(),"First Add items to Cart",Toast.LENGTH_SHORT).show();
        }

    }
}