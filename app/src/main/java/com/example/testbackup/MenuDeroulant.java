package com.example.testbackup;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuDeroulant extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvDataset1;
    private TextView tvDataset2;
    private TextView tvDataset3;
    private NavigationMenuView mMenuRecyclerView;


    private void setNavigationViewItemsMarquee(NavigationView navigationView){

        //find the NavigationMenuView RecyclerView id
        int designNavigationViewId = getResources().getIdentifier("design_navigation_view", "id", getPackageName());
        if(designNavigationViewId!=0) {
            NavigationMenuView menuRecyclerView = navigationView.findViewById(designNavigationViewId);
            if(menuRecyclerView!=null) {
                //register ViewTreeObserver.OnGlobalLayoutListener to be informed for changes in the global layout state or the visibility of views within the view tree
                menuRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
                    @Override
                    public void onGlobalLayout() {
                        //remove the ViewTreeObserver.OnGlobalLayoutListener() to be called only once
                        menuRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        //set the initial Marquee state to false for all visible items
                        setNavigationMenuViewItemsMarquee(menuRecyclerView, false);
                    }
                });
                this.mMenuRecyclerView = menuRecyclerView;
            }
        }
    }

    private void setNavigationMenuViewItemsMarquee(NavigationMenuView menuRecyclerView, boolean startMarquee){

        if(menuRecyclerView!=null){
            //for every visible child in RecyclerView get its ViewHolder as NavigationMenuItemView and from there find the CheckedTextView using the design_menu_item_text id
            for (int i = 0; i < menuRecyclerView.getChildCount(); i++) {
                View child = menuRecyclerView.getChildAt(i);
                if (child!=null) {
                    RecyclerView.ViewHolder holder = menuRecyclerView.getChildViewHolder(child);
                    if(holder!=null && holder.itemView instanceof NavigationMenuItemView){
                        NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView)holder.itemView;
                        int designMenuItemTextId = getResources().getIdentifier("design_menu_item_text", "id", getPackageName());
                        if(designMenuItemTextId!=0){
                            //CheckedTextView found change it to MARQUEE
                            CheckedTextView textView = navigationMenuItemView.findViewById(designMenuItemTextId);
                            if(textView!=null) {
                                textView.setSingleLine(true);
                                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                textView.setHorizontalFadingEdgeEnabled(true);
                                textView.setMarqueeRepeatLimit(startMarquee ? -1 : 0);
                                textView.setSelected(startMarquee);
                            }
                        }
                    }
                }
            }
        }
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deroulant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dataset1, R.id.nav_dataset2, R.id.nav_dataset3)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //set NavigationView Items Marquee
        setNavigationViewItemsMarquee(navigationView);


        //add also the DrawerLayout.DrawerListener to start/stop the Marquee based on onDrawerOpened/onDrawerClosed callbacks
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {}

            @Override
            public void onDrawerStateChanged(int i) {}

            @Override
            public void onDrawerOpened(@NonNull View view) {
                //start Marquee effect for all visible items
                setNavigationMenuViewItemsMarquee(mMenuRecyclerView, true);
            }
            @Override
            public void onDrawerClosed(@NonNull View view) {
                //stop Marquee effect for all visible items
                setNavigationMenuViewItemsMarquee(mMenuRecyclerView, false);
            }
        });



        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deroulant, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    }

