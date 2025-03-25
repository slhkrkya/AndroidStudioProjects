package com.example.mobilprogramlamahafta1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private double toplamFiyat = 0.0;

    private Map<Integer, Double> ekmekFiyatlari;
    private Map<Integer, Double> kofteFiyatlari;
    private Map<Integer, Double> malzemeFiyatlari;
    private Map<Integer, Double> icecekFiyatlari;

    private TextView txtFiyat;
    private int seciliMalzemeSayisi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFiyat = findViewById(R.id.txt_toplam_fiyat);
        Button btnKaydet = findViewById(R.id.btn_kaydet);

        // Fiyatları tanımla
        ekmekFiyatlari = new HashMap<>();
        ekmekFiyatlari.put(R.id.radio_beyaz_ekmek, 10.0);
        ekmekFiyatlari.put(R.id.radio_kepekli_ekmek, 12.0);
        ekmekFiyatlari.put(R.id.radio_cavdar_ekmek, 14.0);

        kofteFiyatlari = new HashMap<>();
        kofteFiyatlari.put(R.id.radio_dana_kofte, 20.0);
        kofteFiyatlari.put(R.id.radio_tavuk_kofte, 18.0);

        malzemeFiyatlari = new HashMap<>();
        malzemeFiyatlari.put(R.id.check_marul, 3.0);
        malzemeFiyatlari.put(R.id.check_domates, 3.0);
        malzemeFiyatlari.put(R.id.check_tursu, 4.0);
        malzemeFiyatlari.put(R.id.check_peynir, 5.0);
        malzemeFiyatlari.put(R.id.check_sogan, 2.0);

        icecekFiyatlari = new HashMap<>();
        icecekFiyatlari.put(R.id.radio_kola, 15.0);
        icecekFiyatlari.put(R.id.radio_su, 5.0);
        icecekFiyatlari.put(R.id.radio_meyve_suyu, 10.0);

        // Ekmek seçimi
        ((RadioGroup) findViewById(R.id.radioGroupEkmek)).setOnCheckedChangeListener((group, checkedId) -> {
            toplamFiyat = ekmekFiyatlari.getOrDefault(checkedId, 0.0);
            guncelleFiyat();
            Toast.makeText(MainActivity.this, "Ekmek seçildi!", Toast.LENGTH_SHORT).show();
        });

        // Köfte seçimi
        ((RadioGroup) findViewById(R.id.radioGroupKofte)).setOnCheckedChangeListener((group, checkedId) -> {
            toplamFiyat += kofteFiyatlari.getOrDefault(checkedId, 0.0);
            guncelleFiyat();
            Toast.makeText(MainActivity.this, "Köfte seçildi!", Toast.LENGTH_SHORT).show();
        });

        // İç malzeme seçimi (En fazla 3 adet seçilebilir)
        CheckBox[] checkBoxlar = {
                findViewById(R.id.check_marul),
                findViewById(R.id.check_domates),
                findViewById(R.id.check_tursu),
                findViewById(R.id.check_peynir),
                findViewById(R.id.check_sogan)
        };

        for (CheckBox checkBox : checkBoxlar) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (seciliMalzemeSayisi < 3) {
                        toplamFiyat += malzemeFiyatlari.getOrDefault(buttonView.getId(), 0.0);
                        seciliMalzemeSayisi++;
                        Toast.makeText(MainActivity.this, checkBox.getText() + " seçildi!", Toast.LENGTH_SHORT).show();
                    } else {
                        buttonView.setChecked(false);
                        Toast.makeText(MainActivity.this, "En fazla 3 malzeme seçebilirsiniz!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    toplamFiyat -= malzemeFiyatlari.getOrDefault(buttonView.getId(), 0.0);
                    seciliMalzemeSayisi--;
                }
                guncelleFiyat();
            });
        }

        // İçecek seçimi
        ((RadioGroup) findViewById(R.id.radioGroupIcecek)).setOnCheckedChangeListener((group, checkedId) -> {
            toplamFiyat += icecekFiyatlari.getOrDefault(checkedId, 0.0);
            guncelleFiyat();
            Toast.makeText(MainActivity.this, "İçecek seçildi!", Toast.LENGTH_SHORT).show();
        });

        // Kaydet butonu
        btnKaydet.setOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Toplam Fiyat: " + toplamFiyat + " TL", Toast.LENGTH_LONG).show()
        );
    }

    private void guncelleFiyat() {
        txtFiyat.setText("Toplam Fiyat: " + toplamFiyat + " TL");
    }
}
