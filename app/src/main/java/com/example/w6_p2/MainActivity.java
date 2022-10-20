package com.example.w6_p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView image;
    private EditText editText;
    private int lastRand = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        image = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        if (sharedPreferences.contains("imagePreference")) {
            String imageString = sharedPreferences.getString("imagePreference", "");
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            image.setImageBitmap(decodedImage);
        }
        if (sharedPreferences.contains("editText")) {
            String str = sharedPreferences.getString("editText", "");
            editText.setText(str);
        }

        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int rand;
                do {
                    rand = random.nextInt(5);
                } while (rand == lastRand);
                lastRand = rand;

                if (rand == 0) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.bladerunner2049));
                } else if (rand == 1) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.children_of_men));
                } else if (rand == 2) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.incendies));
                } else if (rand == 3) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.oldboy));
                } else if (rand == 4) {
                    image.setImageDrawable(getResources().getDrawable(R.drawable.star_trek));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        String imageString = Base64.encodeToString(bb,Base64.DEFAULT);

        myEdit.putString("imagePreference", imageString);
        myEdit.putString("editText", editText.getText().toString());
        myEdit.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        String imageString = Base64.encodeToString(bb,Base64.DEFAULT);

        myEdit.putString("imagePreference", imageString);
        myEdit.putString("editText", editText.getText().toString());
        myEdit.apply();
    }
}

