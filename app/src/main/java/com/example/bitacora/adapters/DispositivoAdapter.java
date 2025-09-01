package com.example.bitacora.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitacora.R;
import com.example.bitacora.database.DatabaseClient;
import com.example.bitacora.database.Dispositivo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class DispositivoAdapter extends RecyclerView.Adapter<DispositivoAdapter.ViewHolder> {
    List<Dispositivo> dispositivoList;
    private final Context context;
    private final RecyclerViewInterface listener;


    public DispositivoAdapter(Context context, List<Dispositivo> dispositivoList, RecyclerViewInterface listener) {
        this.context = context;
        this.dispositivoList = dispositivoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dispositivo, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dispositivo dispositivo = dispositivoList.get(position);
        holder.nombre.setText(dispositivo.getDispositivo());
        holder.modelo.setText(dispositivo.getModelo());
        holder.ubicacion.setText(dispositivo.getUbicacion());
        holder.fabricante.setText(dispositivo.getFabricante());
        holder.ip.setText(dispositivo.getIp());
        holder.puertoSwitch.setText(dispositivo.getPuertoSwitch());
        holder.switche.setText(dispositivo.getSwitche());
        holder.icon.setImageResource(dispositivo.getIconInt());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onDispositivoClick(dispositivo);
        });
        holder.btnDelete.setOnClickListener(v -> {
            Dispositivo item = dispositivoList.get(holder.getAdapterPosition());

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 40, 50, 10);

            // Construir el diálogo
            new AlertDialog.Builder(context)
                    .setTitle("Editar Dispositivo")
                    .setView(layout)
                    .setPositiveButton("Eliminar", (dialog, which) -> Executors.newSingleThreadExecutor().execute(() -> {
                        DatabaseClient.getInstance(context)
                                .getAppDatabase()
                                .dispositivoDao()
                                .delete(item);

                        // Quitar de la lista y actualizar UI
                        new Handler(Looper.getMainLooper()).post(() -> {
                            dispositivoList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        });
                    }))
                    .setNegativeButton("Cancelar", null)
                    .show();

        });
        holder.btnEdit.setOnClickListener(v -> {
            Dispositivo item = dispositivoList.get(holder.getAdapterPosition());

            // Crear contenedor
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 40, 50, 10); // Ajusta el padding si hace falta

            EditText inputDispositivo = new EditText(context);
            inputDispositivo.setHint("Dispositivo");
            inputDispositivo.setText(item.getDispositivo());
            layout.addView(inputDispositivo);

            EditText inputModelo = new EditText(context);
            inputModelo.setHint("Modelo");
            inputModelo.setText(item.getModelo());
            layout.addView(inputModelo);

            EditText inputFabricante = new EditText(context);
            inputFabricante.setHint("Fabricante");
            inputFabricante.setText(item.getFabricante());
            layout.addView(inputFabricante);

            EditText inputUbicacion = new EditText(context);
            inputUbicacion.setHint("Ubicacion");
            inputUbicacion.setText(item.getUbicacion());
            layout.addView(inputUbicacion);

            EditText inputSwitch = new EditText(context);
            inputSwitch.setHint("Switch");
            inputSwitch.setText(item.getSwitche());
            layout.addView(inputSwitch);

            EditText inputPuertoSwitch = new EditText(context);
            inputPuertoSwitch.setHint("Puerto Switch");
            inputPuertoSwitch.setText(item.getPuertoSwitch());
            layout.addView(inputPuertoSwitch);

            EditText inputIP = new EditText(context);
            inputIP.setHint("IP");
            inputIP.setText(item.getIp());
            layout.addView(inputIP);

            EditText inputGateway = new EditText(context);
            inputGateway.setHint("Gateway");
            inputGateway.setText(item.getGateway());
            layout.addView(inputGateway);

            EditText inputMask = new EditText(context);
            inputMask.setHint("Mascara de Red");
            inputMask.setText(item.getMask());
            layout.addView(inputMask);

            EditText inputVoltaje = new EditText(context);
            inputVoltaje.setHint("Voltaje");
            inputVoltaje.setText(item.getVoltaje());
            layout.addView(inputVoltaje);

            EditText inputAlimentacion = new EditText(context);
            inputAlimentacion.setHint("Alimentación");
            inputAlimentacion.setText(item.getAlimentacion());
            layout.addView(inputAlimentacion);

            EditText inputNotas = new EditText(context);
            inputNotas.setHint("Notas");
            inputNotas.setText(item.getNotas());
            layout.addView(inputNotas);

            Spinner iconSpinner = new Spinner(context);
            List<Integer> iconList = Arrays.asList(
                    R.drawable.ic_barrera,
                    R.drawable.ic_camara,
                    R.drawable.ic_conversor,
                    R.drawable.ic_pc,
                    R.drawable.ic_platform,
                    R.drawable.ic_server
            );
            Integer[] miArray = iconList.toArray(new Integer[0]);
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context, miArray);
            iconSpinner.setAdapter(spinnerAdapter);

            // Preseleccionar el icono actual
            int selectedIndex = iconList.indexOf(item.getIconInt());
            if (selectedIndex != -1) {
                iconSpinner.setSelection(selectedIndex);
            }

            layout.addView(iconSpinner);

            // Construir el diálogo
            new AlertDialog.Builder(context)
                    .setTitle("Editar Dispositivo")
                    .setView(layout)
                    .setPositiveButton("Guardar", (dialog, which) -> {
                        String newDispositivo = inputDispositivo.getText().toString().trim();
                        String newModelo = inputModelo.getText().toString().trim();
                        String newFabricante = inputFabricante.getText().toString().trim();
                        String newUbicacion = inputUbicacion.getText().toString().trim();
                        String newSwitch = inputSwitch.getText().toString().trim();
                        String newPuertoSwitch = inputPuertoSwitch.getText().toString().trim();
                        String newIP = inputIP.getText().toString().trim();
                        String newGateway = inputGateway.getText().toString().trim();
                        String newMask = inputMask.getText().toString().trim();
                        String newVoltaje = inputVoltaje.getText().toString().trim();
                        String newAlimentacion = inputAlimentacion.getText().toString().trim();
                        String newNotas = inputNotas.getText().toString().trim();
                        int selectedIconResId = (Integer) iconSpinner.getSelectedItem();

                        if (!newDispositivo.isEmpty()) {
                            item.setDispositivo(newDispositivo);
                            item.setModelo(newModelo);
                            item.setFabricante(newFabricante);
                            item.setUbicacion(newUbicacion);
                            item.setSwitche(newSwitch);
                            item.setPuertoSwitch(newPuertoSwitch);
                            item.setIp(newIP);
                            item.setGateway(newGateway);
                            item.setMask(newMask);
                            item.setVoltaje(newVoltaje);
                            item.setAlimentacion(newAlimentacion);
                            item.setNotas(newNotas);
                            item.setIconInt(selectedIconResId);

                            Executors.newSingleThreadExecutor().execute(() -> {
                                DatabaseClient.getInstance(context)
                                        .getAppDatabase()
                                        .dispositivoDao()
                                        .update(item);

                                new Handler(Looper.getMainLooper()).post(() -> notifyItemChanged(holder.getAdapterPosition()));
                            });
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return dispositivoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, modelo, ubicacion, fabricante, ip, puertoSwitch, switche;
        ImageView icon;
        ImageButton btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombre);
            modelo = itemView.findViewById(R.id.tvModelo);
            ubicacion = itemView.findViewById(R.id.tvUbicacion);
            fabricante = itemView.findViewById(R.id.tvFabricante);
            ip = itemView.findViewById(R.id.tvIp);
            puertoSwitch = itemView.findViewById(R.id.tvPuertoSwitch);
            switche = itemView.findViewById(R.id.tvSwitch);
            icon = itemView.findViewById(R.id.iconImage);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Dispositivo> nuevaLista) {
        this.dispositivoList = nuevaLista;
        notifyDataSetChanged();
    }
}
