package com.mobilprogramlama.odev_5;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Main_Activity extends AppCompatActivity {

    // Kullanıcıdan alınacak bilgiler için alanlar
    TextInputEditText editTextAd, editTextSoyad, editTextDogum, editTextHobi, editTextIletisim;
    RadioGroup radioGroupCinsiyet;
    Button buttonKaydet;
    ListView listViewBilgiler;

    // SharedPreferences: kalıcı veri saklama
    SharedPreferences sharedPreferences;

    // ListView için adapter ve liste
    ArrayAdapter<String> adapter;
    ArrayList<String> kullaniciListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml ile arayüz bağlanır

        // XML ile Java arasında bağlantı kuruluyor
        editTextAd = findViewById(R.id.editTextAd);
        editTextSoyad = findViewById(R.id.editTextSoyad);
        editTextDogum = findViewById(R.id.editTextDogum);
        editTextHobi = findViewById(R.id.editTextHobi);
        editTextIletisim = findViewById(R.id.editTextIletisim);
        radioGroupCinsiyet = findViewById(R.id.radioGroupCinsiyet);
        buttonKaydet = findViewById(R.id.buttonKaydet);
        listViewBilgiler = findViewById(R.id.listViewBilgiler);

        // SharedPreferences başlatılıyor
        sharedPreferences = getSharedPreferences("KullaniciBilgileri", MODE_PRIVATE);
        kullaniciListesi = new ArrayList<>();

        // Uygulama açıldığında daha önce kaydedilmiş verileri SharedPreferences'tan yükler
        Set<String> set = sharedPreferences.getStringSet("veriler", new HashSet<>());
        kullaniciListesi.addAll(set);

        // Özel tasarımlı custom adapter kullanılıyor
        adapter = new CustomAdapter(this, kullaniciListesi);
        listViewBilgiler.setAdapter(adapter);

        // ListView'e tıklanınca detay gösterme (AlertDialog ile)
        listViewBilgiler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String secilenBilgi = kullaniciListesi.get(position);

                new android.app.AlertDialog.Builder(Main_Activity.this)
                        .setTitle("Kullanıcı Detayı")
                        .setMessage(secilenBilgi)
                        .setPositiveButton("Tamam", null)
                        .show();
            }
        });

        // Uzun basıldığında kullanıcıyı silme işlemi
        listViewBilgiler.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String silinecek = kullaniciListesi.get(position);

                new android.app.AlertDialog.Builder(Main_Activity.this)
                        .setTitle("Silmek istediğine emin misin?")
                        .setMessage(silinecek)
                        .setPositiveButton("Sil", (dialog, which) -> {
                            // Listeden sil
                            kullaniciListesi.remove(position);
                            adapter.notifyDataSetChanged();

                            // SharedPreferences'tan güncelleme
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Set<String> yeniSet = new HashSet<>(kullaniciListesi);
                            editor.putStringSet("veriler", yeniSet);
                            editor.apply();

                            Toast.makeText(Main_Activity.this, "Silindi!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("İptal", null)
                        .show();

                return true; // Uzun tıklama işlemi tamamlandı
            }
        });

        // Kaydet butonuna tıklanınca yapılacak işlemler
        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kullanıcıdan alınan veriler okunur
                String ad = editTextAd.getText().toString().trim();
                String soyad = editTextSoyad.getText().toString().trim();
                String dogum = editTextDogum.getText().toString().trim();
                String hobi = editTextHobi.getText().toString().trim();
                String iletisim = editTextIletisim.getText().toString().trim();

                // Cinsiyet seçim kontrolü yapılır
                int selectedId = radioGroupCinsiyet.getCheckedRadioButtonId();
                RadioButton selectedRadio = findViewById(selectedId);
                String cinsiyet = (selectedRadio != null) ? selectedRadio.getText().toString() : "Belirtilmedi";

                // Tek bir metin haline getirilir
                String bilgi = "Ad: " + ad + "\nSoyad: " + soyad + "\nCinsiyet: " + cinsiyet +
                        "\nDoğum: " + dogum + "\nHobiler: " + hobi + "\nİletişim: " + iletisim;

                // Listeye eklenir ve güncellenir
                kullaniciListesi.add(bilgi);
                adapter.notifyDataSetChanged();

                // SharedPreferences'a kalıcı olarak kaydedilir
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> set = new HashSet<>(kullaniciListesi);
                editor.putStringSet("veriler", set);
                editor.apply();

                // Giriş alanları temizlenir
                editTextAd.setText("");
                editTextSoyad.setText("");
                editTextDogum.setText("");
                editTextHobi.setText("");
                editTextIletisim.setText("");
                radioGroupCinsiyet.clearCheck();

                Toast.makeText(Main_Activity.this, "Bilgiler kaydedildi!", Toast.LENGTH_SHORT).show();
            }
        });

        editTextDogum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mevcut tarih alınır
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // DatePicker gösterilir
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Activity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            String tarih = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                            editTextDogum.setText(tarih);
                        }, year, month, day);

                // Maksimum tarih bugünün tarihi olsun (gelecek tarih seçilemesin)
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }
}
