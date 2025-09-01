package com.example.bitacora.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.bitacora.R;
import com.example.bitacora.adapters.SpinnerAdapter;
import com.example.bitacora.database.DatabaseClient;
import com.example.bitacora.database.Dispositivo;
import com.example.bitacora.databinding.FragmentNewDeviceBinding;

import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewDevice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewDevice extends Fragment {
    FragmentNewDeviceBinding binding;

    Integer[] iconResIds;
    Integer selectedIconResId;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NewDevice() {
        // Required empty public constructor
    }

    public static NewDevice newInstance(String param1, String param2) {
        NewDevice fragment = new NewDevice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewDeviceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Integer[] iconResIds = {
                R.drawable.ic_camara,
                R.drawable.ic_server,
                R.drawable.ic_platform,
                R.drawable.ic_conversor,
                R.drawable.ic_dorlet_cpu,
                R.drawable.ic_barrera,
                R.drawable.ic_pc
        };
        SpinnerAdapter adapter = new SpinnerAdapter(requireContext(), iconResIds);
        binding.iconSpinner.setAdapter(adapter);
        selectedIconResId = iconResIds[0];
        binding.iconSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIconResId = iconResIds[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        binding.btnGuardar.setOnClickListener(v-> guardarDispositivo());
    }

    private void guardarDispositivo() {
        String nombre = binding.etDispositivo.getText().toString();
        String modelo = binding.etModelo.getText().toString();
        String ubicacion = binding.etUbicacion.getText().toString();
        String alimentacion = binding.etAlimentacion.getText().toString();
        Dispositivo dispositivo = new Dispositivo(
                nombre, modelo, ubicacion, alimentacion
        );
        dispositivo.setIconInt(selectedIconResId);
        if (!binding.etFabricante.getText().isEmpty()) {dispositivo.setFabricante(binding.etFabricante.getText().toString());}
        if (!binding.etSwitch.getText().isEmpty()) {dispositivo.setSwitche(binding.etSwitch.getText().toString());}
        if (!binding.etPuertoSwitch.getText().isEmpty()) {dispositivo.setPuertoSwitch(binding.etPuertoSwitch.getText().toString());}
        if (!binding.etIP.getText().isEmpty()) {dispositivo.setIp(binding.etIP.getText().toString());}
        if (!binding.etGateway.getText().isEmpty()) {dispositivo.setGateway(binding.etGateway.getText().toString());}
        if (!binding.etMask.getText().isEmpty()) {dispositivo.setMask(binding.etMask.getText().toString());}
        if (!binding.etVoltaje.getText().isEmpty()) {dispositivo.setVoltaje(binding.etVoltaje.getText().toString());}
        if (!binding.etNotas.getText().isEmpty()) {dispositivo.setNotas(binding.etNotas.getText().toString());}


        // Aquí ya puedes llamar a tu DAO de Room para guardarlo
        saveDispositivo(dispositivo);
    }

    private void saveDispositivo(Dispositivo dispositivo) {
        Executors.newSingleThreadExecutor().execute(() -> {
            DatabaseClient.getInstance(requireContext()).getAppDatabase()
                    .dispositivoDao().insert(dispositivo);
            requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Guardado!", Toast.LENGTH_SHORT).show());
        });
        binding.etDispositivo.setText("");
        binding.etModelo.setText("");
        binding.etFabricante.setText("");
        binding.etUbicacion.setText("");
        binding.etSwitch.setText("");
        binding.etPuertoSwitch.setText("");
        binding.etIP.setText("");
        binding.etGateway.setText("");
        binding.etMask.setText("");
        binding.etVoltaje.setText("");
        binding.etAlimentacion.setText("");
        binding.etNotas.setText("");
    }
}