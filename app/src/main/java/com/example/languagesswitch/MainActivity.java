package com.example.languagesswitch;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final String SP_FILE = "sharedPref";
    private final String CUR_LANG_KEY = "curLang";

    private Spinner spnSwitchLang;
    private Button btnOk;
    private Locale curLocale;
    private Configuration config;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getSharedPreferences(SP_FILE, MODE_PRIVATE);
        config = new Configuration();

        curLocale = new Locale(sharedPref.getString(CUR_LANG_KEY, Locale.getDefault().getLanguage()));

        config.setLocale(curLocale);
        getResources().updateConfiguration(config, getBaseContext().getResources()
                .getDisplayMetrics());

        setContentView(R.layout.activity_main);

        btnOk = findViewById(R.id.btnOk);

        initSpinner();
    }

    private void initSpinner() {
        spnSwitchLang = findViewById(R.id.spnSwitchLang);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSwitchLang.setAdapter(adapter);

        spnSwitchLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Locale selectedLocale = getLocaleByItemSelected();

                if (selectedLocale.getLanguage().equals(curLocale.getLanguage())) {
                    btnOk.setEnabled(false);
                } else {
                    btnOk.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Locale getLocaleByItemSelected() {
        int position = spnSwitchLang.getSelectedItemPosition();
        String[] languages = getResources().getStringArray(R.array.languages);
        String selectedLanguages = languages[position];
        Locale selectedLocale = null;

        if (selectedLanguages.contains("Рус") || selectedLanguages.contains("Rus")) {
            selectedLocale = new Locale("ru");
        } else if (selectedLanguages.contains("Англ") || selectedLanguages.contains("Eng")) {
            selectedLocale = new Locale("en");
        }

        return selectedLocale;
    }


    public void onClick(View view) {
        Locale selectedLocale = getLocaleByItemSelected();

        config.setLocale(selectedLocale);

        getResources().updateConfiguration(config, getBaseContext().getResources()
                .getDisplayMetrics());

        curLocale = selectedLocale;

        recreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPref.edit().putString(CUR_LANG_KEY, curLocale.getLanguage()).commit();
    }


}
