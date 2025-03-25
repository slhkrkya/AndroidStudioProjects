package com.example.mobil_odev_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private double toplamFiyat = 0.0;
    private final Map<Integer, Double> fiyatlar = new HashMap<>();
    private int seciliMalzemeSayisi = 0;
    private TextView txtFiyat;

    // Önceki seçimleri saklamak için değişkenler
    private final Map<Integer, Integer> oncekiSecimler = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFiyat = findViewById(R.id.txt_toplam_fiyat);
        Button btnKaydet = findViewById(R.id.btn_kaydet);

        // Fiyatları ekleme
        fiyatlar.put(R.id.radio_beyaz_ekmek, 10.0);
        fiyatlar.put(R.id.radio_kepekli_ekmek, 12.0);
        fiyatlar.put(R.id.radio_cavdar_ekmek, 14.0);
        fiyatlar.put(R.id.radio_dana_kofte, 20.0);
        fiyatlar.put(R.id.radio_tavuk_kofte, 18.0);
        fiyatlar.put(R.id.check_marul, 3.0);
        fiyatlar.put(R.id.check_domates, 3.0);
        fiyatlar.put(R.id.check_tursu, 4.0);
        fiyatlar.put(R.id.check_peynir, 5.0);
        fiyatlar.put(R.id.check_sogan, 2.0);
        fiyatlar.put(R.id.radio_kola, 15.0);
        fiyatlar.put(R.id.radio_su, 5.0);
        fiyatlar.put(R.id.radio_meyve_suyu, 10.0);

        // RadioGroup seçimleri için düzeltme eklenmiş metod
        setRadioGroupListener(R.id.radioGroupEkmek);
        setRadioGroupListener(R.id.radioGroupKofte);
        setRadioGroupListener(R.id.radioGroupIcecek);

        // CheckBox seçimleri için işlem yap
        setCheckBoxListeners();

        // Kaydet butonu işlemi
        btnKaydet.setOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Toplam Fiyat: " + toplamFiyat + " TL", Toast.LENGTH_LONG).show()
        );
    }

    // Güncellenmiş RadioGroup seçim metodu
    private void setRadioGroupListener(int radioGroupId) {
        ((RadioGroup) findViewById(radioGroupId)).setOnCheckedChangeListener((group, checkedId) -> {
            // Önceki seçim varsa, eski fiyatı çıkar
            if (oncekiSecimler.containsKey(radioGroupId)) {
                int oncekiId = oncekiSecimler.get(radioGroupId);
                toplamFiyat -= fiyatlar.getOrDefault(oncekiId, 0.0);
            }

            // Yeni seçimi ekle
            toplamFiyat += fiyatlar.getOrDefault(checkedId, 0.0);
            oncekiSecimler.put(radioGroupId, checkedId); // Önceki seçimi güncelle

            // Fiyatı güncelle
            guncelleFiyat();
            Toast.makeText(MainActivity.this, "Seçim güncellendi!", Toast.LENGTH_SHORT).show();
        });
    }

    // CheckBox seçimleri için metot
    private void setCheckBoxListeners() {
        int[] checkBoxIds = {R.id.check_marul, R.id.check_domates, R.id.check_tursu, R.id.check_peynir, R.id.check_sogan};

        for (int checkBoxId : checkBoxIds) {
            CheckBox checkBox = findViewById(checkBoxId);
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (seciliMalzemeSayisi < 3) {
                        toplamFiyat += fiyatlar.getOrDefault(buttonView.getId(), 0.0);
                        seciliMalzemeSayisi++;
                    } else {
                        buttonView.setChecked(false);
                        Toast.makeText(MainActivity.this, "En fazla 3 malzeme seçebilirsiniz!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    toplamFiyat -= fiyatlar.getOrDefault(buttonView.getId(), 0.0);
                    seciliMalzemeSayisi--;
                }
                guncelleFiyat();
            });
        }
    }

    // Fiyat bilgisini güncelleyen metot
    private void guncelleFiyat() {
        txtFiyat.setText("Toplam Fiyat: " + toplamFiyat + " TL");
    }
}

