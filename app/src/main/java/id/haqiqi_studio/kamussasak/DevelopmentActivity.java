package id.haqiqi_studio.kamussasak;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DevelopmentActivity extends AppCompatActivity {
    DBHelper mydb;
    AdUtils click = new AdUtils();
    EditText word, meaning, form;
    String stat = "1";
    AdView ad3, ad4, ad5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pengembangan");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        word = findViewById(R.id.txtWord);
        meaning = findViewById(R.id.txtMeaning);
        form = findViewById(R.id.txtForm);

        mydb = new DBHelper(getApplicationContext());

        ad3 = (AdView) findViewById(R.id.adView3);
        ad4 = (AdView) findViewById(R.id.adView4);
        ad5 = (AdView) findViewById(R.id.adView5);

        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        ad3.loadAd(adRequest);
        ad4.loadAd(adRequest);
        ad5.loadAd(adRequest);

        click.loadInterstitial(this, getString(R.string.interstitial_ad_unit_id));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (word.getText().toString().matches("") || meaning.getText().toString().matches("") || form.getText().toString().matches("")) {
                   Snackbar.make(view, "Lengkapi data terlebih dahulu", Snackbar.LENGTH_SHORT).show();
               }
               else {
                   if (mydb.insertWord(word.getText().toString(), meaning.getText().toString(), form.getText().toString(), stat)) {
                       Snackbar.make(view, "Berhasil ditambahkan", Snackbar.LENGTH_SHORT).show();
                   }
                   else {
                       Snackbar.make(view, "Gagal!", Snackbar.LENGTH_SHORT).show();
                   }
               }
            }
        });
    }

}
