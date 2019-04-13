package com.Amro.jobfinder.Controller.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.Amro.jobfinder.Controller.Fragments.MainFragment;


import com.Amro.jobfinder.R;

/**
 * Created by Amro 13/4/2019
 * Just the main activity of the app, to handle any activity specific actions, and move to main fragment.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movetoMainFragment();
    }
    //Method to move to the mainfragment of the app.
    private void movetoMainFragment() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        MainFragment mainFragment=new MainFragment();
        fragmentTransaction.replace(R.id.container,mainFragment);
        fragmentTransaction.commit();
    }





}


