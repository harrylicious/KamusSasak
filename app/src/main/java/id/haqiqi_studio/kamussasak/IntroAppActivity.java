package id.haqiqi_studio.kamussasak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class IntroAppActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String INTRO_PREF = "intro.preference";
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;

    public ViewPager vPager;
    private int[] layouts = {R.layout.intro_slide1, R.layout.intro_slide2, R.layout.intro_slide3, R.layout.intro_slide4};
    private IntroViewPageAdapter pagerAdapter;
    private LinearLayout dots_layout;
    private ImageView[] dots;
    private Button btnNext, btnSkip, btnStarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_app);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        try {
            btnNext = (Button) findViewById(R.id.btnNext);
            btnSkip = (Button) findViewById(R.id.btnSkip);
            btnStarted = (Button) findViewById(R.id.getStarted);

            vPager = (ViewPager) findViewById(R.id.viewPager);
            pagerAdapter = new IntroViewPageAdapter(layouts, this);

            setting = getApplicationContext().getSharedPreferences(INTRO_PREF, MODE_PRIVATE);

            int val = getStateIntro();
            if (cekValIntro(val) == true) {
                loadHome();
            }

            //Set Animation on Activity Exit
            //Explode explode = new Explode();
            //Slide slide = new Slide(Gravity.RIGHT);

            //getWindow().setEnterTransition(slide);
            //getWindow().setExitTransition(explode);

            //updown = AnimationUtils.loadAnimation(this, R.anim.updown);
            //vPager.setAnimation(updown);

            if (new PreferenceManager(this).checkPreference()) {
                loadHome();
            }

            if (Build.VERSION.SDK_INT >= 19) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }


            vPager.setAdapter(pagerAdapter);

            dots_layout = (LinearLayout) findViewById(R.id.nav_dots);
            btnNext.setOnClickListener(this);
            btnSkip.setOnClickListener(this);

            btnStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setStateIntro();
                    loadHome();
                }
            });


            //Timer timer = new Timer();
            //timer.scheduleAtFixedRate(new AutoSlide(), 2000, 4000);

            createDots(0);

            vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    createDots(position);
                    if (position == layouts.length-1) {
                        btnNext.setText("Start");
                        btnSkip.setVisibility(View.INVISIBLE);
                    }
                    else {
                        btnNext.setText("Next");
                        btnSkip.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    private void setStateIntro() {
        editor = setting.edit();
        editor.putInt("state", 1);
        editor.commit();
    }

    private int getStateIntro() {
        int val = setting.getInt("state", 0);
        return val;
    }

    private boolean cekValIntro(int val) {
        if (val == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    private void createDots(int curPos) {
        if (dots_layout != null) {
            dots_layout.removeAllViews();

            dots = new ImageView[layouts.length];

            for (int i = 0; i < layouts.length; i++) {
                dots[i] = new ImageView(this);

                if (i==curPos) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selected_nav));
                }
                else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_nav));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 0, 4, 0);

                dots_layout.addView(dots[i], params);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                loadNextSlide();
                break;
            case R.id.btnSkip:
                loadHome();
                new PreferenceManager(this).writePreference();
                break;
        }
    }

    private void loadHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void loadNextSlide() {
        int next_slide = vPager.getCurrentItem()+1;

        try {
            if (next_slide < layouts.length) {
                vPager.setCurrentItem(next_slide);
            }
            else {
                loadHome();
                new PreferenceManager(this).writePreference();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}

