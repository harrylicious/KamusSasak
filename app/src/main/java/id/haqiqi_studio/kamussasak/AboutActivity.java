package id.haqiqi_studio.kamussasak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout main;
    DrawerLayout drawer;
    NavigationView navigationView;
    FloatingActionButton fab;
    TextView tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               share();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle
        );
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

    }

    void init() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tentang = findViewById(R.id.txtAbout);
    }

    //region Sharing
    void share() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, tentang.getText().toString() + "\n\n dan Yuks buruan download Kamus Sasak disini: " + getString(R.string.share_link));
        i.setType("text/plain");
        startActivity(Intent.createChooser(i, "Bagikan Ke:"));
    }
    //endregion


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_develop) {
            startActivity(new Intent(getApplicationContext(), DevelopmentActivity.class));
        } else if (id == R.id.nav_list) {
            startActivity(new Intent(getApplicationContext(), DaftarKataActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        } else if (id == R.id.nav_share) {
            share();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }


}
