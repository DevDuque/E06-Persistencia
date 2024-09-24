package br.team.persistencia;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private ImageView savedImg;
    private Bitmap capturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedImg = findViewById(R.id.saved_img);
        Button getImgButton = findViewById(R.id.get_img);
        Button saveImgButton = findViewById(R.id.save_img);

        // Verifica a permissão da câmera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        // Botão para tirar a foto
        getImgButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Botão para salvar a foto
        saveImgButton.setOnClickListener(v -> {
            if (capturedImage != null) {
                saveImageToExternalStorage(capturedImage);
            } else {
                Toast.makeText(this, "Nenhuma foto capturada para salvar", Toast.LENGTH_SHORT).show();
            }
        });

// -----------------------------------------------------------------------------------------------------------------------------------------

        // Recuperar as preferências
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        // Verificar a última data de acesso
        String lastAccess = sharedPreferences.getString("lastAccess", null);

        // Se não for o primeiro acesso (ou seja, se lastAccess não for null), exibir no Toast
        if (lastAccess != null) {
            Toast.makeText(this, "Último acesso: " + lastAccess, Toast.LENGTH_LONG).show();
        }

        // Registrar a data e hora do acesso atual
        String currentAccess = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault()).format(new Date());

        // Salvar a data e hora atual
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastAccess", currentAccess);
        editor.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            capturedImage = (Bitmap) extras.get("data");
            savedImg.setImageBitmap(capturedImage);  // Exibe preview da foto no ImageView
        }
    }

    private void saveImageToExternalStorage(Bitmap bitmap) {
        Uri imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        try {
            Uri imageUri = getContentResolver().insert(imageCollection, values);
            if (imageUri != null) {
                try (OutputStream outStream = getContentResolver().openOutputStream(imageUri)) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    Toast.makeText(this, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar a foto", Toast.LENGTH_SHORT).show();
        }
    }
}
