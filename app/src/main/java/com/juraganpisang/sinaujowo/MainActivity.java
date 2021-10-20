package com.juraganpisang.sinaujowo;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.juraganpisang.sinaujowo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private TextView drawerProfileName, drawerProfileText;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home_bottom:
                            setFragement(new CategoryFragment());
                            return true;
                        case R.id.nav_leaderboard_bottom:
                            setFragement(new LeaderBoardFragment());
                            return true;
                        case R.id.nav_account_bottom:
                            setFragement(new AccountFragment());
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        setSupportActionBar(binding.appBarMain.toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        main_frame = findViewById(R.id.main_frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

//        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_leaderboard, R.id.nav_account, R.id.nav_about, R.id.nav_share)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        drawerProfileName = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        drawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_text_img);

        String name = DBQuery.myProfile.getName();
        drawerProfileName.setText(name);

        drawerProfileText.setText(name.toUpperCase().substring(0,1));

        setFragement(new CategoryFragment());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.main_frame);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setFragement(Fragment fragement){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(), fragement);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
