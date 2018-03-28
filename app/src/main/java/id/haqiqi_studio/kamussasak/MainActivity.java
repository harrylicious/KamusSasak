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
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import id.haqiqi_studio.kamussasak.Anim.AnimationClasses;
import id.haqiqi_studio.kamussasak.Model.DataModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<DataModel> dataModels;
    AnimationClasses anim;
    AdUtils click = new AdUtils();
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


        AdView adView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

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

        if (mydb.getCount() <= 0) {
            Toast.makeText(getApplicationContext(), "I'm Running.", Toast.LENGTH_SHORT).show();
            mydb.truncateWord();
            saveAll();
        }

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
        i.putExtra(Intent.EXTRA_TEXT, "Yuks buruan download Kamus Sasak disini: "  + getString(R.string.share_link));
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

    //region Bulk Insert
    void saveAll() {
        mydb.bulkInsert("Alan", "Permisi", "");
        mydb.bulkInsert("Nurgehe", "Permisi", "");
        mydb.bulkInsert("Alas cokor", "Sendal", "");
        mydb.bulkInsert("Ampure", "Maaf", "");
        mydb.bulkInsert("Ampurayan", "Muda", "");
        mydb.bulkInsert("Anom", "Muda", "");
        mydb.bulkInsert("Antos", "Tunggu", "");
        mydb.bulkInsert("Antuk", "Oleh", "");
        mydb.bulkInsert("Arep", "Depan/mau", "");
        mydb.bulkInsert("Aturang", "Berikan", "");
        mydb.bulkInsert("Awinan", "Sebabnya", "");
        mydb.bulkInsert("Base krame", "Budibahasa", "");
        mydb.bulkInsert("Basen", "Perkataan", "");
        mydb.bulkInsert("Bebaos", "Sedang Bicara", "");
        mydb.bulkInsert("Bejangkep", "Menikah", "");
        mydb.bulkInsert("Bejeneng", "Berupa", "");
        mydb.bulkInsert("Bekarye", "Bekerja,pesta  (begawe)", "");
        mydb.bulkInsert("Bekesedi", "Buang air besar", "");
        mydb.bulkInsert("Belamak", "Bertikar", "");
        mydb.bulkInsert("Belemer", "Buang air kecil", "");
        mydb.bulkInsert("Beparas", "Bercukur", "");
        mydb.bulkInsert("Besermin", "Menangis", "");
        mydb.bulkInsert("Bijesanak", "Sanak Saudara", "");
        mydb.bulkInsert("Bije", "Anak", "");
        mydb.bulkInsert("Bini", "Perempuan", "");
        mydb.bulkInsert("Bosang", "Perut", "");
        mydb.bulkInsert("Calung", "Kacamata", "");
        mydb.bulkInsert("Cokor", "Kaki", "");
        mydb.bulkInsert("Dane", "Tuan", "");
        mydb.bulkInsert("Dastar", "Sapuk", "");
        mydb.bulkInsert("Dine", "Hari", "");
        mydb.bulkInsert("Doe", "Milik", "");
        mydb.bulkInsert("Doeang", "Yang memiliki", "");
        mydb.bulkInsert("Dumeteng", "Kepada", "");
        mydb.bulkInsert("Empu", "Ibu Jari", "");
        mydb.bulkInsert("Gading", "Tangan", "");
        mydb.bulkInsert("Gadingan", "Ambil", "");
        mydb.bulkInsert("Gedeng", "Rumah", "");
        mydb.bulkInsert("Hendaweganpisan", "Mohon Kiranya", "");
        mydb.bulkInsert("ican", "Beri", "");
        mydb.bulkInsert("ican", "Kasih", "");
        mydb.bulkInsert("Icanin", "Berikan", "");

        mydb.bulkInsert("Iling", "Ingat", "");
        mydb.bulkInsert("Imbuhan", "Tambahan", "");
        mydb.bulkInsert("Inggih", "Ya", "");
        mydb.bulkInsert("Iring", "Ikut", "");
        mydb.bulkInsert("Jangantade", "Lauk Pau", "");
        mydb.bulkInsert("Darang", "Lauk Pau", "");
        mydb.bulkInsert("Jate", "Rambut", "");
        mydb.bulkInsert("Jeneng", "Rupa", "");
        mydb.bulkInsert("Kainulung", "Kain Hitam", "");
        mydb.bulkInsert("Kakiq", "Kakek", "");
        mydb.bulkInsert("Kalihkayun", "Tersinggung", "");
        mydb.bulkInsert("Kampuh", "Selimut", "");
        mydb.bulkInsert("Kapetek", "Dikuburkan", "");
        mydb.bulkInsert("Karne", "Telinga", "");
        mydb.bulkInsert("Kayun", "Suka", "");
        mydb.bulkInsert("Suke", "Suka", "");
        mydb.bulkInsert("Kekirangan", "Kekurangan", "");
        mydb.bulkInsert("Kemos", "Tersenyum", "");
        mydb.bulkInsert("Kepanggih", "Bertemu", "");
        mydb.bulkInsert("Kepaten", "Kematian", "");
        mydb.bulkInsert("Kesengan", "Disuruh", "");
        mydb.bulkInsert("Kiat", "Tertawa", "");
        mydb.bulkInsert("Kinyam", "Sehat", "");
        mydb.bulkInsert("Kiwe", "Kiri", "");
        mydb.bulkInsert("Kuace", "Baju", "");
        mydb.bulkInsert("Kiwe", "Kiri", "");
        mydb.bulkInsert("Kiwe", "Kiri", "");
        mydb.bulkInsert("Kiwe", "Kiri", "");

        mydb.bulkInsert("Kuri", "Gerbang", "");
        mydb.bulkInsert("Laki", "Lelaki", "");
        mydb.bulkInsert("Lamak", "Tikar", "");
        mydb.bulkInsert("Lanjaran", "Rokok", "");
        mydb.bulkInsert("Lati", "Lidah", "");
        mydb.bulkInsert("Layah", "Lidah", "");
        mydb.bulkInsert("Layon", "Mayit", "");
        mydb.bulkInsert("Lingsir", "Tua", "");
        mydb.bulkInsert("Luaran", "Selesai", "");
        mydb.bulkInsert("Lumbar", "Pergi", "");
        mydb.bulkInsert("Margi", "Pergi", "");
        mydb.bulkInsert("Malih", "Lagi", "");
        mydb.bulkInsert("Mangkin", "Sekarang", "");
        mydb.bulkInsert("Mantuk", "Pulang", "");
        mydb.bulkInsert("Maring", "Kepada", "");
        mydb.bulkInsert("Maturpewikan", "Permakluman", "");
        mydb.bulkInsert("Matur", "Memberitahu", "");
        mydb.bulkInsert("Mawinan", "Olehsebab", "");
        mydb.bulkInsert("Mecacap", "Bekeja", "");
        mydb.bulkInsert("Mecunduk", "Bertemu", "");
        mydb.bulkInsert("Medahar", "Makan", "");
        mydb.bulkInsert("Majengan", "Makan", "");
        mydb.bulkInsert("Nade", "Makan", "");
        mydb.bulkInsert("Medahar", "Makan", "");


        mydb.bulkInsert("Meke", "Becermin", "");
        mydb.bulkInsert("Melinggih", "Duduk", "");
        mydb.bulkInsert("Melungguh", "Duduk", "");
        mydb.bulkInsert("Memaos", "Membicarakan", "");
        mydb.bulkInsert("Menawi", "Barangkali", "");
        mydb.bulkInsert("Meneng", "Diam", "");
        mydb.bulkInsert("Mengedengin", "Mendengarkan", "");
        mydb.bulkInsert("Menggah", "Marah", "");
        mydb.bulkInsert("Duka", "Marah", "");
        mydb.bulkInsert("Mensare", "Tidur", "");
        mydb.bulkInsert("Mentangi", "Bangun Tidur", "");
        mydb.bulkInsert("Merangkat", "Menikah", "");
        mydb.bulkInsert("Mejangkep", "Menikah", "");
        mydb.bulkInsert("Merengu", "Mendengarkan", "");
        mydb.bulkInsert("Mesingit", "Bersembunyi", "");
        mydb.bulkInsert("Mesiram", "Mandi", "");
        mydb.bulkInsert("Metaken", "Bertanya", "");
        mydb.bulkInsert("Mulut", "Sungap", "");
        mydb.bulkInsert("Munapaat", "Manfaat", "");
        mydb.bulkInsert("Munggah", "Naik keatas berugak/sholat", "");
        mydb.bulkInsert("Napi", "Apa", "");
        mydb.bulkInsert("Nenten man", "Belum", "");
        mydb.bulkInsert("Nenten/Boten", "Tidak", "");
        mydb.bulkInsert("Boten", "Tidak", "");
        mydb.bulkInsert("Ngadeg", "Berdiri", "");
        mydb.bulkInsert("Ngandike", "Mengatakan", "");
        mydb.bulkInsert("Ngantos", "Menunggu", "");
        mydb.bulkInsert("Ngaturang", "Memberikan", "");
        mydb.bulkInsert("Ngayahin", "Meladeni", "");
        mydb.bulkInsert("Ngelanjar", "Merokok", "");
        mydb.bulkInsert("Ngelunsur", "Meminta", "");
        mydb.bulkInsert("Ngemban", "Pembawa Amanat", "");
        mydb.bulkInsert("Ngeranjing", "Masuk", "");
        mydb.bulkInsert("Ngerencanin", "Merepotkan", "");
        mydb.bulkInsert("Ngewedang", "Merepotkan", "");
        mydb.bulkInsert("Ngerencanin", "Minum kopi", "");
        mydb.bulkInsert("Ngimbuh", "Tambah", "");
        mydb.bulkInsert("Ngiring", "Mengikuti", "");
        mydb.bulkInsert("Nike", "Itu", "");
        mydb.bulkInsert("Niki", "Ini", "");
        mydb.bulkInsert("Ninik", "Nenek", "");
        mydb.bulkInsert("Nyaluq", "Nyusul", "");
        mydb.bulkInsert("Nyandang", "Cukup", "");
        mydb.bulkInsert("Nyedah", "Makan Daun Sirih", "");
        mydb.bulkInsert("Nyupne", "Bermimpi", "");
        mydb.bulkInsert("Oleman", "Undangan", "");


        mydb.bulkInsert("Onang", "Berwenang", "");
        mydb.bulkInsert("Pacetan", "Teman Ngopi", "");
        mydb.bulkInsert("Pageran", "Gigi", "");
        mydb.bulkInsert("Pamit", "Mohondiri", "");
        mydb.bulkInsert("Pamitang", "Meminta", "");
        mydb.bulkInsert("Pangartike", "Pengertian", "");
        mydb.bulkInsert("Pangendike", "Ucapan", "");
        mydb.bulkInsert("Paosan", "Bacaan", "");
        mydb.bulkInsert("Parek", "Menemui", "");
        mydb.bulkInsert("Pasengan", "Nama", "");
        mydb.bulkInsert("Pecandangan", "Penginang", "");
        mydb.bulkInsert("Pelabuan", "Tempat Daun Sirih", "");
        mydb.bulkInsert("Pecawisan", "Pelocok Daun Sirih", "");
        mydb.bulkInsert("Pejarupan", "Muka", "");
        mydb.bulkInsert("Pekayunan", "Kemauan", "");
        mydb.bulkInsert("Pelinggih Senamian", "Kalian semua", "");
        mydb.bulkInsert("Pelinggih/pelungguh", "Anda", "");
        mydb.bulkInsert("Pemaos", "Pembaca", "");
        mydb.bulkInsert("Pendikayang", "Suruh", "");
        mydb.bulkInsert("Penjenengan", "Saselepan (Keris)", "");
        mydb.bulkInsert("Penyerminan", "Mata", "");
        mydb.bulkInsert("Penyingakin", "Mata", "");
        mydb.bulkInsert("Pepaosan", "Tempat Membaca", "");
        mydb.bulkInsert("Peragayan", "Kemauan", "");
        mydb.bulkInsert("Rage", "Tubuh", "");
        mydb.bulkInsert("Pesarean", "Tempat Tidur", "");
        mydb.bulkInsert("Petitis", "Kending", "");
        mydb.bulkInsert("Puad", "Pusar", "");
        mydb.bulkInsert("Pulihtuturan", "Dapatcerita", "");
        mydb.bulkInsert("Pulih", "Dapat", "");
        mydb.bulkInsert("Punggalan", "Leher", "");
        mydb.bulkInsert("Jongge", "Leher", "");
        mydb.bulkInsert("Pedek", "Leher", "");
        mydb.bulkInsert("Pungkur", "Belakang/Dekat", "");
        mydb.bulkInsert("Pungkuran", "Belakangan", "");
        mydb.bulkInsert("Puput", "Selesai/Tamat", "");
        mydb.bulkInsert("Puri", "Istana", "");
        mydb.bulkInsert("Raris", "Lanjutkan", "");
        mydb.bulkInsert("Gelis", "Cepat", "");
        mydb.bulkInsert("Rawis", "Kumis/Jenggot", "");
        mydb.bulkInsert("Rawuh", "Datang", "");
        mydb.bulkInsert("Ring", "Di", "");
        mydb.bulkInsert("Sakinghendi", "Darimana", "");
        mydb.bulkInsert("Sampunnapi", "Bagaimana", "");
        mydb.bulkInsert("Sampun", "Sudah/telah", "");
        mydb.bulkInsert("Sampunang", "Tidakperlu/jangan", "");
        mydb.bulkInsert("Sanak", "Saudara", "");
        mydb.bulkInsert("Sareng(bareng)", "Dengan (bersama)", "");


        mydb.bulkInsert("Sasih", "Bulan", "");
        mydb.bulkInsert("Sebiniq/rabi", "Istri", "");
        mydb.bulkInsert("Sedah/kinang", "Daunsirih", "");
        mydb.bulkInsert("Sede/ninggal", "Meninggal", "");
        mydb.bulkInsert("Selakiq", "Suami", "");
        mydb.bulkInsert("Semugik", "Susu", "");
        mydb.bulkInsert("Serminang", "Melihat", "");
        mydb.bulkInsert("Silaq/daweg", "Ayo,mari", "");
        mydb.bulkInsert("Simpang", "Mampir", "");
        mydb.bulkInsert("Simpuh", "Bersila", "");
        mydb.bulkInsert("Singit", "Sembunyi", "");
        mydb.bulkInsert("Sipaq", "Pundak", "");
        mydb.bulkInsert("Siratmaye", "Alis", "");
        mydb.bulkInsert("Suar", "Lapar", "");
        mydb.bulkInsert("Suargi/melekat", "Almarhum", "");
        mydb.bulkInsert("Sumuran", "Hidung", "");
        mydb.bulkInsert("Sungkan/serdeng", "Sakit", "");
        mydb.bulkInsert("Tampek", "Kain", "");
        mydb.bulkInsert("Tampi", "Menerima", "");
        mydb.bulkInsert("Teantosin", "Ditungguin", "");
        mydb.bulkInsert("Tebaosang", "Sedang dibicarakan", "");
        mydb.bulkInsert("Tebaosin", "Dibicarakan", "");
        mydb.bulkInsert("Tegamel/tegading", "Dipegang (dikuasai)", "");
        mydb.bulkInsert("Temargiang", "Diberlakukan/ dilaksanakan", "");
        mydb.bulkInsert("Tendes", "Kepala", "");
        mydb.bulkInsert("Tendikayang", "Disuruh", "");
        mydb.bulkInsert("Tengen", "Kanan", "");
        mydb.bulkInsert("Teparekin", "Ditemui", "");
        mydb.bulkInsert("Tepejeneng", "Dirupakan (dibentuk)", "");
        mydb.bulkInsert("Tepetek", "Tekubur", "");
        mydb.bulkInsert("Tertiptapsile", "Sopan Santun", "");
        mydb.bulkInsert("Tiang/dewek", "Saya, aku", "");
        mydb.bulkInsert("Tititate", "Aturan", "");
        mydb.bulkInsert("Tunas", "Minta", "");
        mydb.bulkInsert("Ulung", "Hitam", "");
        mydb.bulkInsert("Upakcara", "Upacara", "");
        mydb.bulkInsert("Urip", "Hidup", "");
        mydb.bulkInsert("Utawi", "Atau", "");
        mydb.bulkInsert("Wareg", "Kenyang", "");
        mydb.bulkInsert("Warsa", "Warsa", "");
        mydb.bulkInsert("Wenten", "Ada", "");
        mydb.bulkInsert("Wonten", "Ada", "");
        mydb.bulkInsert("Wikan", "Tahu", "");
        mydb.bulkInsert("Wikanang", "Mengetahui", "");

    }
    //endregion

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

    //region Filter Data
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

                    //Intent intent = new Intent(getApplicationContext(), *.class);

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

                    //Intent intent = new Intent(getApplicationContext(), *.class);

                    //intent.putExtras(dataBundle);
                    //startActivity(intent);
                }
            });
        }
    }
    //endregion

    //region Methods
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ImageView img = (ImageView) menu.findItem(R.id.action_convert).getActionView();
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
            txtSearch.setText("");
            if (txt.matches(getString(R.string.indo))) {
                anim = new AnimationClasses();
                anim.setAnimationHyperToObject(txtSearch, getApplicationContext());
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
            click.loadInterstitial(this, getString(R.string.interstitial_ad_unit_id));
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
