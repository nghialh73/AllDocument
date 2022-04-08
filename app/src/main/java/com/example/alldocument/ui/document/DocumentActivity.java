package com.example.alldocument.ui.document;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.alldocument.R;
import com.example.alldocument.data.model.HomeItemType;
import com.example.alldocument.ui.home.HomeFragment;
import com.example.alldocument.utils.PermissionUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentActivity extends AppCompatActivity{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private DocumentFragment documentFragment;

    public final static String HOME_TYPE_ITEM_KEY = "HOME_TYPE_ITEM_KEY";
    public final static String NAME_ITEM_KEY = "NAME_ITEM_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        ButterKnife.bind(this);
        initView();
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
        HomeItemType type = (HomeItemType) getIntent().getExtras().getSerializable(HOME_TYPE_ITEM_KEY);
        documentFragment = DocumentFragment.newInstance(type);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fr_document_container, documentFragment);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    private void setupToolbar() {
        String name = getIntent().getExtras().getString(NAME_ITEM_KEY);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}