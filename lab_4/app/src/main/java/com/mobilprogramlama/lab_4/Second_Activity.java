package com.mobilprogramlama.lab_4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Second_Activity extends AppCompatActivity {
    // UI bileşenleri: Kayıtlı verileri göstermek için TextView'ler
    private TextView tvUsername, tvLocation, tvAge;
    // Kullanıcının silmek istediği veri anahtarını girmek için EditText
    private EditText etDeleteField;
    // Veri silme ve geri dönüş işlemleri için butonlar
    private Button btnDelete, btnBack;
    // Uygulama genelinde verileri saklamak için SharedPreferences nesnesi
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_information layout dosyasını kullanıcı arayüzü olarak ayarla
        setContentView(R.layout.activity_information);

        // Layout dosyasındaki UI elemanlarının tanımlanması ve ilişkilendirilmesi
        tvUsername = findViewById(R.id.tvUsername);       // Kullanıcı adını gösterecek TextView
        tvLocation = findViewById(R.id.tvLocation);       // Konumu gösterecek TextView
        tvAge = findViewById(R.id.tvAge);                 // Yaşı gösterecek TextView
        etDeleteField = findViewById(R.id.etDeleteField); // Silinecek veri anahtarını girmek için EditText
        btnDelete = findViewById(R.id.btnDelete);         // Veriyi silmek için buton
        btnBack = findViewById(R.id.btnBack);             // İlk ekrana geri dönüş için buton

        // "UserData" adlı SharedPreferences dosyasını oluştur veya aç
        // MODE_PRIVATE, dosyanın sadece bu uygulama tarafından okunabileceğini belirtir
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        // SharedPreferences üzerinden kayıtlı verileri alıp ilgili TextView'lere yazdırma
        tvUsername.setText("Username: " + sharedPreferences.getString("username", ""));
        tvLocation.setText("Location: " + sharedPreferences.getString("location", ""));
        tvAge.setText("Age: " + sharedPreferences.getString("age", ""));

        // Silme işlemi: btnDelete'e tıklanıldığında çalışacak kod bloğu
        btnDelete.setOnClickListener(v -> {
            // Kullanıcının EditText'e girdiği silme anahtarını al
            String deleteKey = etDeleteField.getText().toString();
            // SharedPreferences üzerinde düzenleme yapmak için editor nesnesi oluştur
            SharedPreferences.Editor editor = sharedPreferences.edit();

            // Girilen anahtarın "username", "location" veya "age" olup olmadığını kontrol et
            if (deleteKey.equalsIgnoreCase("username") || deleteKey.equalsIgnoreCase("location") || deleteKey.equalsIgnoreCase("age")) {
                // Geçerli anahtar ise, ilgili veriyi sil
                editor.remove(deleteKey);
                // Değişikliklerin kaydedilmesi (asenkron olarak)
                editor.apply();
                // Kullanıcıya verinin silindiğini bildiren Toast mesajı göster
                Toast.makeText(this, "Veri silindi!", Toast.LENGTH_SHORT).show();
                // Arayüzdeki verileri güncelle
                refreshData();
            } else {
                // Girilen anahtar geçersizse, hata mesajı göster
                Toast.makeText(this, "Geçersiz giriş!", Toast.LENGTH_SHORT).show();
            }
        });

        // Geri butonuna tıklanıldığında, Main_Activity'ye dönüş işlemini gerçekleştir
        btnBack.setOnClickListener(v -> {
            // Main_Activity'ye geçiş yapmak için Intent oluştur
            Intent intent = new Intent(Second_Activity.this, Main_Activity.class);
            startActivity(intent);
            // Second_Activity'yi kapat (finish) ederek geri dönüş sağla
            finish();
        });
    }

    // refreshData metodu: SharedPreferences'teki güncel verileri çekerek UI'ı yeniler
    private void refreshData() {
        tvUsername.setText("Kullanıcı Adı: " + sharedPreferences.getString("username", ""));
        tvLocation.setText("Konum: " + sharedPreferences.getString("location", ""));
        tvAge.setText("Yaş: " + sharedPreferences.getString("age", ""));
    }
}
