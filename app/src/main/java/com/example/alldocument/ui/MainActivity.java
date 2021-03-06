package com.example.alldocument.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.alldocument.R;
import com.example.alldocument.ui.home.HomeFragment;
import com.example.alldocument.utils.PermissionUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navView)
    NavigationView mNavigationView;
    private HomeFragment homeFragment;
    private ActionBarDrawerToggle mToggle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeFragment = HomeFragment.newInstance();
        initView();
        setupDrawerLayout();
    }

    private void initView() {
        setupToolbar();
        if (!PermissionUtils.checkPermission(this)) {
            PermissionUtils.requestPermission(this);
        } else {
            replaceFragment();
        }
    }

    private  void replaceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fr_container, homeFragment);
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.ID_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                replaceFragment();
            } else {
                finish();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupDrawerLayout() {
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main, menu);
        // searchView = menu.findItem(R.id.actionSearch);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        /*switch (item.getItemId()) {
            case R.id.search:
                startRevealActivity(findViewById(R.id.main_layout));
                return true;
            case R.id.view:
                documentFragment.changeLayout();
                documentFragment.changeIcon(item);
                fragmentFavorite.changeLayout();
                fragmentRecent.changeLayout();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_rating:
                rateApp();
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_about:
                break;
        }
        return true;
    }

    public void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            } catch (Exception localException) {

                Toast toast = Toast.makeText(this, "unable to find market app", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowTitleEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void updateToolbar(String title, int drawable, boolean isHome){
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(drawable);
        updateIconToolbar(isHome);
    }

    private void updateIconToolbar(boolean isHome) {
        if(!isHome) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mToggle.setDrawerIndicatorEnabled(true);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToggle.setDrawerIndicatorEnabled(true);
            mToggle.setToolbarNavigationClickListener(null);
            //mToolBarNavigationListenerIsRegistered = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}