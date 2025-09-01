package com.example.bitacora;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bitacora.adapters.SpinnerAdapter;
import com.example.bitacora.database.DatabaseClient;
import com.example.bitacora.database.Dispositivo;
import com.example.bitacora.databinding.ActivityMainBinding;
import com.example.bitacora.fragmentos.Dispositivos;
import com.example.bitacora.fragmentos.NewDevice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final int REQUEST_CODE_IMPORT = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        replaceFragment(new Dispositivos());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.lista) {
                replaceFragment(new Dispositivos());
            }
            if (id == R.id.guardar) {
                replaceFragment(new NewDevice());
            }
            if (id == R.id.compartir) {
                exportarImportar();
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragmento){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        /*if(fragmentoDesechable!=null){
            fragmentTransaction.remove(fragmentoDesechable);
        }*/
        fragmentTransaction.replace(R.id.fragmentContainerView, fragmento);
        //fragmentoDesechable = fragmento;
        fragmentTransaction.commit();
    }
    private void exportar(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // 1. Obtener datos de la base de datos
                List<Dispositivo> dispositivos = DatabaseClient.getInstance(context)
                        .getAppDatabase()
                        .dispositivoDao()
                        .getAllDispositivos();

                // 2. Convertir a JSON
                Gson gson = new Gson();
                String json = gson.toJson(dispositivos);

                // 3. Guardar en archivo temporal
                File file = new File(context.getExternalFilesDir(null), "dispositivos.json");
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(json.getBytes(StandardCharsets.UTF_8));
                }

                // 4. Obtener URI segura con FileProvider
                Uri uri = FileProvider.getUriForFile(context,
                        context.getPackageName() + ".provider", file);

                // 5. Crear Intent de compartir
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("application/json");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // 6. Lanzar chooser en la UI principal
                new Handler(Looper.getMainLooper()).post(() ->
                        context.startActivity(Intent.createChooser(shareIntent, "Compartir backup"))
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void importar(Context context) {
        abrirSelectorDeArchivo();
    }

    private void abrirSelectorDeArchivo() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/json");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_IMPORT);
    }
    private void exportarImportar(){
        // Crear contenedor
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10); // Ajusta el padding si hace falta
        // crear contenido
        RadioGroup grupo = new RadioGroup(this);
        RadioButton exportar = new RadioButton(this);
        exportar.setText("Exportar");
        RadioButton importar = new RadioButton(this);
        importar.setText("Importar");
        grupo.addView(exportar);
        grupo.addView(importar);

        layout.addView(grupo);

        // Construir el diálogo
        new AlertDialog.Builder(this)
                .setTitle("Exportar / Importar")
                .setView(layout)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    if (exportar.isChecked()){
                        exportar(this);
                        return;
                    }
                    importar(this);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMPORT && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                importarJsonDesdeUri(uri);
            }
        }
    }
    private void importarJsonDesdeUri(Uri uri) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Leer archivo completo como String
                InputStream inputStream = this.getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();

                String json = stringBuilder.toString();

                // Parsear JSON a lista de objetos
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Dispositivo>>() {}.getType();
                List<Dispositivo> dispositivos = gson.fromJson(json, listType);

                // Guardar en Room
                DatabaseClient.getInstance(this)
                        .getAppDatabase()
                        .dispositivoDao()
                        .insertAll(dispositivos);

                // Avisar en la UI
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(this, "Importación completada", Toast.LENGTH_SHORT).show()
                );

            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(this, "Error importando JSON", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}