package com.example.restclientapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restclientapp.model.Evento;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {

    private List<Evento> eventos = new ArrayList<>();

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.tvNombre.setText(evento.getNombre());
        holder.tvFechas.setText(evento.getFechaInicio() + " - " + evento.getFechaFin());
        holder.tvDescripcion.setText(evento.getDescripcion());

        // Cargar imagen con Picasso si existe URL, si no, poner placeholder
        if (evento.getImageUrl() != null && !evento.getImageUrl().isEmpty()) {
            Picasso.get().load(evento.getImageUrl()).into(holder.imgEvento);
        }
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvFechas, tvDescripcion;
        ImageView imgEvento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreEvento);
            tvFechas = itemView.findViewById(R.id.tvFechasEvento);
            tvDescripcion = itemView.findViewById(R.id.tvDescEvento);
            imgEvento = itemView.findViewById(R.id.imgEvento);
        }
    }
}