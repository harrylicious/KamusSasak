package id.haqiqi_studio.kamussasak;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import id.haqiqi_studio.kamussasak.Model.DataModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<DataModel> dataModels;
    ListView list;
    ImageView imageView;
    DBHelper mydb;
    EditText txtSearch;
    TextView txtNotFound;
    FloatingActionButton fab;
    LinearLayout main;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.indo_sasak);
        setSupportActionBar(toolbar);

        init();
        setVisiblityList();

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setVisiblityList();
                filter();
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               share();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText(null);
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        dataModels = new ArrayList<>();

        mydb = new DBHelper(getApplicationContext());

        list.setAdapter(mydb.getAllWords(getApplicationContext()));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mydb.getPosition(position, getApplicationContext(), view);

            }
        });


    }

    //region Inisialisasi Variabel
    void init() {
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        main = findViewById(R.id.main_card);
        list = findViewById(R.id.list);
        txtSearch = findViewById(R.id.txtSearch);
        txtNotFound = findViewById(R.id.txtNotFound);
        main.setVisibility(View.VISIBLE);
        clear = findViewById(R.id.imgClear);
    }
    //endregion

    //region Sharing
    void share() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, "Yuks buruan download Kamus Sasak disini: " + "https://play.google.com/store/apps/details?id=haqiqi_studio.kancantastreamingapp");
        i.setType("text/plain");
        startActivity(Intent.createChooser(i, "Bagikan Ke:"));
    }
    //endregion

    void saveData() {
        if (mydb.insertWord("Test", "Tes", "Kata Kerja", "1")) {
            Toast.makeText(getApplicationContext(), "done",
                    Toast.LENGTH_SHORT).show();
            showData();
        } else {
            Toast.makeText(getApplicationContext(), "not done",
                    Toast.LENGTH_SHORT).show();
        }
    }

    void setVisiblityList() {
        if (txtSearch.getText().toString().matches("")) {
            clear.setVisibility(View.INVISIBLE);
            main.setVisibility(View.VISIBLE);
            list.setVisibility(View.INVISIBLE);
        } else {
            clear.setVisibility(View.VISIBLE);
            main.setVisibility(View.INVISIBLE);
            list.setVisibility(View.VISIBLE);
        }
    }

    //region Show Data
    void showData() {
        list.setAdapter(mydb.getAllWords(getApplicationContext()));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2 + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);

                //Intent intent = new Intent(getApplicationContext(), DisplayContact.class);

                //intent.putExtras(dataBundle);
                //startActivity(intent);
            }
        });
    }
    //endregion

    void filter() {
        if (txtSearch.getHint().toString().matches(getString(R.string.indo))) {
            list.setAdapter(mydb.filterDataIndoSasak(txtSearch.getText().toString(), getApplicationContext(), txtNotFound));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    int id_To_Search = arg2 + 1;

                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);

                    //Intent intent = new Intent(getApplicationContext(), DisplayContact.class);

                    //intent.putExtras(dataBundle);
                    //startActivity(intent);
                }
            });
        } else {
            list.setAdapter(mydb.filterDataSasakIndo(txtSearch.getText().toString(), getApplicationContext(), txtNotFound));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    int id_To_Search = arg2 + 1;

                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", id_To_Search);

                    //Intent intent = new Intent(getApplicationContext(), DisplayContact.class);

                    //intent.putExtras(dataBundle);
                    //startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String txt = txtSearch.getHint().toString();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_convert) {
            if (txt.matches(getString(R.string.indo))) {
                txtSearch.setHint(getString(R.string.sasak));
                toolbar.setTitle(getString(R.string.sasak_indo));
            } else {
                txtSearch.setHint(getString(R.string.indo));
                toolbar.setTitle(getString(R.string.indo_sasak));
            }
        } else if (id == R.id.action_indo_sasak) {
            txtSearch.setHint(getString(R.string.indo));
            item.setIcon(getResources().getDrawable(R.drawable.ic_apply));
        } else {
            txtSearch.setHint(getString(R.string.sasak));
            item.setIcon(getResources().getDrawable(R.drawable.ic_apply));
        }

        return super.onOptionsItemSelected(item);
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

    void Shape() {
        RoundRectShape roundRectShape = new RoundRectShape(new float[]{
                10, 10, 10, 10,
                10, 10, 10, 10}, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.parseColor("#FFFFFF"));
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setBackground(shapeDrawable);
// or you can use myImageView.setImageDrawable(shapeDrawable);
    }
}
