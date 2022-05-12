package com.sistemaasistenciaycomunicados;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sistemaasistenciaycomunicados.fragment.FragmentNotification;
import com.sistemaasistenciaycomunicados.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FloatingActionButton floatingActionButton = findViewById(R.id.main_fab_create);
        BottomNavigationView navigationView = findViewById(R.id.main_bottom_view);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame, new FragmentNotification())
                .commit();

        if (firebaseUser.getUid().equals("j1ZnJnh8DkbMKTiknOBa0Cb86Jz1")) {
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(this, CreateActivity.class)));

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_bottom_nav_notification) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new FragmentNotification()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                } else if (item.getItemId() == R.id.menu_bottom_nav_profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ProfileFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                }
                return true;
            }
        });

    }
}