package com.example.mysalud.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysalud.R;

import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder> {

    private List<Servicio> servicioList;

    public ServicioAdapter(List<Servicio> servicioList) {
        this.servicioList = servicioList;
    }

    @NonNull
    @Override
    public ServicioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioViewHolder holder, int position) {
        Servicio servicio = servicioList.get(position);
        holder.serviceName.setText(servicio.getNombre_servicio());
        holder.serviceDescription.setText(servicio.getDescripcion());
        // Si quieres agregar imágenes o más datos, lo puedes hacer aquí
    }

    @Override
    public int getItemCount() {
        return servicioList.size();
    }

    public static class ServicioViewHolder extends RecyclerView.ViewHolder {
        TextView serviceName;
        TextView serviceDescription;
        ImageView serviceImage;

        public ServicioViewHolder(View itemView) {
            super(itemView);
            serviceName = itemView.findViewById(R.id.serviceName);
            serviceDescription = itemView.findViewById(R.id.serviceDescription);
            serviceImage = itemView.findViewById(R.id.serviceImage);
        }
    }
}