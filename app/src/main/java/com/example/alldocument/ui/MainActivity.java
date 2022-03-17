package com.example.alldocument.ui;

import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.alldocument.R;
import com.example.alldocument.ui.document.DocumentFragment;
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
    private DocumentFragment documentFragment;
    private ActionBarDrawerToggle mToggle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        documentFragment = DocumentFragment.newInstance();
        initView();
        setupDrawerLayout();
    }

    private void initView() {
        setupToolbar();
        replaceFragment();
    }

    private  void replaceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fr_container, documentFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.ID_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //   PdfConfig.setUp(this);
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

//        MenuItem menuItem = mNavigationView.getMenu().findItem(R.id.dark_theme); // This is the menu item that contains your switch
//        drawerSwitch = menuItem.getActionView().findViewById(R.id.switch_dark_mode);
        //drawerSwitch.setChecked(mPref.getBoolean(DARK_MODE, false) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
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

    @Override
    protected void onPause() {
        super.onPause();

    }
}