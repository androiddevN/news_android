package com.news.grab.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.news.grab.R;
import com.news.grab.databinding.ActivityMainLayoutBinding;
import com.news.grab.network.NewsApi;
import com.news.grab.ui.news.NewsFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainLayoutBinding binding;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_layout);
        initView();
        initListener();
    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.drawerLayout.setViewScale(Gravity.START, 0.9f); //set height scale for main view (0f to 1f)
        binding.drawerLayout.setViewElevation(Gravity.START, 10); //set main view elevation when drawer open (dimension)
        binding.drawerLayout.setRadius(Gravity.START, 35); //set end container's corner radius (dimension)
        fragmentManager.beginTransaction()
                .replace(R.id.view_container, NewsFragment.newInstance(NewsApi.Category.general))
                .commit();
        setTitle(NewsApi.Category.general);

    }

    private void initListener() {
        binding.navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START, true);
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_general:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.general));
                setTitle(NewsApi.Category.general);
                break;
            case R.id.nav_business:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.business));
                setTitle(NewsApi.Category.business);
                break;
            case R.id.nav_sports:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.sports));
                setTitle(NewsApi.Category.sports);
                break;
            case R.id.nav_health:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.health));
                setTitle(NewsApi.Category.health);
                break;
            case R.id.nav_entertainment:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.entertainment));
                setTitle(NewsApi.Category.entertainment);
                break;
            case R.id.nav_technology:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.technology));
                setTitle(NewsApi.Category.technology);
                break;
            case R.id.nav_science:
                setNewsFragmentBasedOnCategory(NewsFragment.newInstance(NewsApi.Category.science));
                setTitle(NewsApi.Category.science);
                break;
            default:
                Toast.makeText(this, "Oppss...wait for next release :)", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void setTitle(NewsApi.Category category) {
        binding.toolbar.setTitle(category == NewsApi.Category.general ? "Headlines".toUpperCase() : category.title.toUpperCase());
    }

    private void setNewsFragmentBasedOnCategory(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.view_container, fragment)
                .commit();
    }
}
