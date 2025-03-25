package com.mobilprogramlama.lab_4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Main_Activity extends AppCompatActivity {
    // UI bileşenleri: kullanıcı adını, konumu ve yaşı girmek için EditText alanları
    private EditText etUsername, etLocation, etAge;
    // Verileri kaydetmek için buton
    private Button btnSave;
    // Uygulama genelinde verileri saklamak için SharedPreferences nesnesi
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_main layout dosyasını kullanıcı arayüzü olarak ayarla
        setContentView(R.layout.activity_main);

        // UI Elemanlarının tanımlanması ve layout dosyasındaki bileşenlerle ilişkilendirilmesi
        ImageView imgLogo = findViewById(R.id.imgLogo); // Uygulama logosunu göstermek için ImageView
        etUsername = findViewById(R.id.etUsername);       // Kullanıcı adı girişi
        etLocation = findViewById(R.id.etLocation);       // Konum girişi
        etAge = findViewById(R.id.etAge);                 // Yaş girişi
        btnSave = findViewById(R.id.btnSave);             // Verileri kaydetmek için buton

        // "UserData" adında bir SharedPreferences dosyası oluşturulur veya mevcutsa açılır
        // MODE_PRIVATE, dosyanın sadece bu uygulama tarafından okunabileceğini belirtir
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        // Kaydet butonuna tıklama olayını dinleyen (listener) ekleniyor
        btnSave.setOnClickListener(v -> {
            // Kullanıcının girdiği metinler alınır
            String username = etUsername.getText().toString();
            String location = etLocation.getText().toString();
            String age = etAge.getText().toString();

            // SharedPreferences üzerinden düzenleme (edit) işlemi başlatılır
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Kullanıcının girdiği veriler, ilgili anahtarlarla kaydedilir
            editor.putString("username", username);
            editor.putString("location", location);
            editor.putString("age", age);
            // Değişikliklerin kaydedilmesi asenkron olarak yapılır
            editor.apply();

            // İkinci aktiviteye geçiş yapmak için Intent oluşturulur
            Intent intent = new Intent(Main_Activity.this, Second_Activity.class);
            // Yeni aktivite başlatılır
            startActivity(intent);
        });
    }
}
