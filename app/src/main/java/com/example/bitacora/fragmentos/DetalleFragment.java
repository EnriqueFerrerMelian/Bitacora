package com.example.bitacora.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bitacora.R;
import com.example.bitacora.adapters.DispositivoAdapter;
import com.example.bitacora.database.DatabaseClient;
import com.example.bitacora.database.Dispositivo;
import com.example.bitacora.databinding.FragmentDetalleBinding;
import com.example.bitacora.databinding.FragmentDispositivosBinding;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleFragment extends Fragment {
    private static final String ARG_ID = "id_dispositivo";
    FragmentDetalleBinding binding;

    public DetalleFragment() {
        // Required empty public constructor
    }

    public static DetalleFragment newInstance(int id) {
        DetalleFragment f = new DetalleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleBinding.inflate(inflater, container, false);
        assert getArguments() != null;
        int id = getArguments().getInt(ARG_ID);

        Executors.newSingleThreadExecutor().execute(() -> {
            Dispositivo dispositivo = DatabaseClient.getInstance(requireContext())
                    .getAppDatabase()
                    .dispositivoDao()
                    .getDispositivoById(id);
            requireActivity().runOnUiThread(() -> {
                binding.iconImage.setImageResource(dispositivo.getIconInt());
                binding.etDispositivo.setText(dispositivo.getDispositivo());
                binding.etModelo.setText(dispositivo.getModelo());
                binding.etFabricante.setText(dispositivo.getFabricante());
                binding.etUbicacion.setText(dispositivo.getUbicacion());
                binding.etSwitch.setText(dispositivo.getSwitche());
                binding.etPuertoSwitch.setText(dispositivo.getPuertoSwitch());
                binding.etIP.setText(dispositivo.getIp());
                binding.etGateway.setText(dispositivo.getGateway());
                binding.etMask.setText(dispositivo.getMask());
                binding.etVoltaje.setText(dispositivo.getVoltaje());
                binding.etAlimentacion.setText(dispositivo.getAlimentacion());
                binding.etNotas.setText(dispositivo.getNotas());
            });
        });

        return binding.getRoot();
    }
}