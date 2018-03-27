package id.haqiqi_studio.kamussasak;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class DevelopmentActivity extends AppCompatActivity {
    DBHelper mydb;
    EditText word, meaning, form;
    String stat = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        word = findViewById(R.id.txtWord);
        meaning = findViewById(R.id.txtMeaning);
        form = findViewById(R.id.txtForm);

        mydb = new DBHelper(getApplicationContext());

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
