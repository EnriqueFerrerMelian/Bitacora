package com.example.bitacora.fragmentos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.bitacora.R;
import com.example.bitacora.adapters.DispositivoAdapter;
import com.example.bitacora.database.DatabaseClient;
import com.example.bitacora.database.Dispositivo;
import com.example.bitacora.databinding.FragmentDispositivosBinding;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;

public class Dispositivos extends Fragment{
    private List<Dispositivo> dispositivosLista;
    DispositivoAdapter dispositivosAdapter;
    FragmentDispositivosBinding binding;


    public Dispositivos() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDispositivosBinding.inflate(inflater, container, false);

        // spinner management
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sortSpinner.setAdapter(adapter);
        binding.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        // Order by default
                        loadSortedData("default");
                        break;
                    case 1:
                        // Order by plataforma
                        loadSortedData("platform");
                        break;
                    case 2:
                        // Order by entidad
                        loadSortedData("account");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //getAll();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.btBuscar.setOnClickListener(v-> {
            String query = binding.etBuscar.getText().toString().toLowerCase();
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Dispositivo> filtrado = DatabaseClient.getInstance(requireContext())
                        .getAppDatabase()
                        .dispositivoDao()
                        .getAllDispositivosFromQuery(query);

                requireActivity().runOnUiThread(() -> {
                    dispositivosAdapter.updateList(filtrado);
                });
            });
        });

        return binding.getRoot();
    }
        @SuppressLint("NotifyDataSetChanged")
        private void loadSortedData(String orderBy) {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Dispositivo> dispositivosLista;

                // Check the database by criteria
                switch (orderBy) {
                    case "Dispositivo":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByDispositivo();
                        break;
                    case "Modelo":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByModelo();
                        break;
                    case "Fabricante":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByFabricante();
                        break;
                    case "Ubicacion":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByUbicacion();
                        break;
                    case "IP":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByIp();
                        break;
                    case "Gateway":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByGateway();
                        break;
                    case "Voltaje":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByVoltaje();
                        break;
                    case "Alimentacion":
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivosByAlimentacion();
                        break;
                    default:
                        dispositivosLista = DatabaseClient.getInstance(requireContext())
                                .getAppDatabase()
                                .dispositivoDao()
                                .getAllDispositivos(); // default value
                        break;
                }
                requireActivity().runOnUiThread(() -> {
                    if (!isAdded()) return;
                    dispositivosAdapter = new DispositivoAdapter(requireContext(), dispositivosLista, dispositivo -> {
                        Fragment detalle = DetalleFragment.newInstance(dispositivo.getId());
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainerView, detalle)
                                .addToBackStack(null)
                                .commit();
                    });
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerView.setAdapter(dispositivosAdapter);
                    dispositivosAdapter.notifyDataSetChanged();
                });
            });
        }
}