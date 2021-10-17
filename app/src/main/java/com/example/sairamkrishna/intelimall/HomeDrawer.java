package com.example.sairamkrishna.intelimall;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sairamkrishna.intelimall.ui.gallery.GalleryFragment;
import com.example.sairamkrishna.intelimall.ui.home.HomeFragment;
import com.example.sairamkrishna.intelimall.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private int HOME = 0, PROFILE = 1, ORDER = 2, currentFragment = -1;
    ImageView imgView_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgView_cart=findViewById(R.id.imgView_cart);
        imgView_cart.setOnClickListener(v->{
            Intent intent=new Intent(HomeDrawer.this,CartItems.class);
            startActivity(intent);
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setBackgroundColor(getResources().getColor(R.color.white));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.signout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        updateNavHeader();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        showFragment(fragment, HOME);
//                        FragmentManager fm = getSupportFragmentManager();
//                        FragmentTransaction ft = fm.beginTransaction();
//                        ft.replace(R.id.nav_host_fragment, new HomeFragment());
//                        ft.commit();
                        break;
                    case R.id.nav_gallery:

                        fragment = new GalleryFragment();
                        showFragment(fragment, PROFILE);
//                        Toast.makeText( HomeDrawer.this, "profile Info", Toast.LENGTH_SHORT).show();
//                        FragmentManager fm1 = getSupportFragmentManager();
//                        FragmentTransaction ft1 = fm1.beginTransaction();
//                        ft1.replace(R.id.nav_host_fragment, new GalleryFragment());
//                        ft1.commit();
                        break;
                    case R.id.nav_slideshow:

                        fragment = new SlideshowFragment();
                        showFragment(fragment, ORDER);
//                        FragmentManager fm2 = getSupportFragmentManager();
//                        FragmentTransaction ft2 = fm2.beginTransaction();
//                        ft2.replace(R.id.nav_host_fragment, new SlideshowFragment());
//                        ft2.commit();
                        break;
                    case R.id.signout:
                       logout();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent it = new Intent(HomeDrawer.this, MainActivity.class);
        startActivity(it);
    }

    private void showFragment(Fragment fragment, int fragmentNo) {
        if (fragment != null && currentFragment != fragmentNo) {
            currentFragment = fragmentNo;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.parent_layout2, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (currentFragment != HOME) {
           // bottomNavigationView.setSelectedItemId(R.id.home_menu);
            showFragment(new HomeFragment(), HOME);
        } else {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit From App")
                    .setMessage("Are you sure you want to exit ?")
                    .setPositiveButton("Yes", (dialog, which) -> logout())
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNavHeader() {


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navName);
        TextView navUserMail = headerView.findViewById(R.id.navEmail);

        navUserMail.setText(user.getEmail());
        navUsername.setText(user.getDisplayName());

    }

}