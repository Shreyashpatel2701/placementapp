package com.abc.mydemoapp.CompanyActivity;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.abc.mydemoapp.R;
import com.google.android.material.navigation.NavigationView;

public class ProfileCompanyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_company);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //set toolbar appearance


        //for crate home button

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar();

        drawer = findViewById(R.id.drawer_company_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.abc.mydemoapp.CompanyActivity.HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.abc.mydemoapp.CompanyActivity.HomeFragment()).commit();
                break;

            case R.id.nav_myprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.abc.mydemoapp.CompanyActivity.ProfileFragment()).commit();
                break;

            case R.id.nav_studentlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ViewStudentsFragment()).commit();
                break;

//            case R.id.nav_feedback:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FeedbackFragment()).commit();
//                break;

            case R.id.nav_logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.abc.mydemoapp.CompanyActivity.LogoutFragment()).commit();
                break;
            case R.id.nav_selectedstudentlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new com.abc.mydemoapp.CompanyActivity.SelectedStudentsFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
