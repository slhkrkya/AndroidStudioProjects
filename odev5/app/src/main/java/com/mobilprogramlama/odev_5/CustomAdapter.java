package com.mobilprogramlama.odev_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

// CustomAdapter, ListView'e özel görünümlü öğeler sağlamak için ArrayAdapter sınıfından türetilmiştir
public class CustomAdapter extends ArrayAdapter<String> {

    // Constructor - Verileri ve bağlamı (Context) alır
    public CustomAdapter(Context context, List<String> bilgiler) {
        // Varsayılan layout değeri yerine 0 veriyoruz çünkü kendi layout'umuzu kullanacağız
        super(context, 0, bilgiler);
    }

    // Her bir öğe (satır) ListView'de nasıl görünecek, burada tanımlanır
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Belirtilen konumdaki veriyi al
        String bilgi = getItem(position);

        // Eğer daha önce oluşturulmuş bir görünüm yoksa, yeni bir tane şişir (inflate et)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // list_item.xml'de yer alan TextView'i bul
        TextView textViewBilgi = convertView.findViewById(R.id.textViewBilgi);

        // TextView'e alınan veriyi yazdır
        textViewBilgi.setText(bilgi);

        // Oluşturulan veya yeniden kullanılan görünümü döndür
        return convertView;
    }
}


