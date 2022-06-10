package com.example.testbackup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.testbackup.ui.dataset1.Dataset1Fragment;
import com.example.testbackup.ui.dataset2.Dataset2Fragment;
import com.example.testbackup.ui.dataset3.Dataset3Fragment;
import com.example.testbackup.ui.welcome.WelcomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /**
         * Lorsqu'on ouvre pour la premiere fois l'application l'affichage sera celui du DatasetFragment
        */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WelcomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_welcome);
        }


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    /**
     * méthode qui affiche le résultat des bouton de notre menu
    */
     public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_welcome:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WelcomeFragment()).commit();
                break;
            case R.id.nav_dataset1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dataset1Fragment()).commit();

                break;
            case R.id.nav_dataset2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dataset2Fragment()).commit();
                break;
            case R.id.nav_dataset3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dataset3Fragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        /**
         * On ferme le menu après avoir pression sur le bouton
        */
         return true;
    }
}
