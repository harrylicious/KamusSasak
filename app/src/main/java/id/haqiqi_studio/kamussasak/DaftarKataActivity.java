package id.haqiqi_studio.kamussasak;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import id.haqiqi_studio.kamussasak.*;

public class DaftarKataActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView list;
    DBHelper mydb;
    Toolbar toolbar;
    TextView translation;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kata);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Daftar Kata");
        setSupportActionBar(toolbar);

        mydb = new DBHelper(getApplicationContext());

        init();
        showData();

        AdView adView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    void showData() {
        list.setAdapter(mydb.getAllWordsForList(getApplicationContext()));
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    void init() {
        list = findViewById(R.id.listWords);
        translation = findViewById(R.id.listTranslator);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        super.onBackPressed();
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
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
