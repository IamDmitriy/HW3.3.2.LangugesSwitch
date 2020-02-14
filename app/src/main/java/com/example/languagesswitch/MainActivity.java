package com.example.languagesswitch;

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
    private Spinner spnSwitchLang;
    private Button btnOk;
    private Locale curLocale = Locale.getDefault();
    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        config = new Configuration();
        btnOk = findViewById(R.id.btnOk);

        if (SaverCurLocale.curLocale != null) {
            curLocale = SaverCurLocale.curLocale;
        }

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

                if (selectedLocale.equals(curLocale)) {
                    btnOk.setEnabled(false);
                } else {
                    btnOk.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnOk.setEnabled(false);
            }
        });
    }

    private Locale getLocaleByItemSelected() {
        int position = spnSwitchLang.getSelectedItemPosition();
        String[] languages = getResources().getStringArray(R.array.languages);
        String selectedLanguages = languages[position];
        Locale selectedLocale = null;

        if (selectedLanguages.contains("Рус") || selectedLanguages.contains("Rus")) {
            selectedLocale = new Locale("ru", "RU");
        } else if (selectedLanguages.contains("Англ") || selectedLanguages.contains("Eng")) {
            selectedLocale = new Locale("en", "US");


        }

        return selectedLocale;
    }


    public void onClick(View view) {
        Locale selectedLocale = getLocaleByItemSelected();

        config.setLocale(selectedLocale);

        getResources().updateConfiguration(config, getBaseContext().getResources()
                .getDisplayMetrics());

        SaverCurLocale.curLocale = selectedLocale;

        recreate();
    }


}
