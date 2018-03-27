package id.haqiqi_studio.kamussasak;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {


    private Context context;
    private SharedPreferences setting ;
    private SharedPreferences.Editor editor;


    public PreferenceManager(Context context) {
        this.context = context;
        getSharedPreference();
    }



    private void getSharedPreference() {
        //setting = context.getSharedPreferences(context.getString(R.string.my_preference), Context.MODE_PRIVATE);

    }

    public void writePreference() {
        //SharedPreferences.Editor editor = setting.edit();
        //editor.putString(context.getString(R.string.my_preference_key), "INIT_OK");
    }

    public boolean checkPreference() {
        boolean status = false;

        /*if (setting.getString(context.getString(R.string.my_preference_key), "null").equals("null")) {
            status = false;
        }
        else {
            status = true;
        }*/
        return false;
    }

    public  void clearPreference() {
        setting.edit().clear().commit();
    }


}
