package com.example.restclientapp; // <--- AJUSTA TU PAQUETE

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclientapp.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    // --- CLASE INTERNA PARA MOSTRAR DATOS ---
    public static class ItemDisplay {
        public int id;
        public String nombre;
        public int valor; // Puede ser precio o cantidad
        public boolean esInventario; // Para saber si mostrar botón "Comprar" o "Cantidad"

        // Constructor para TIENDA (Producto)
        public ItemDisplay(Producto p) {
            this.id = p.getId();
            this.nombre = p.getNombreproducto();
            this.valor = p.getPrecio();
            this.esInventario = false;
        }

        // Constructor para INVENTARIO (Nombre y Cantidad)
        public ItemDisplay(String nombre, int cantidad) {
            this.id = -1; // No se usa para comprar
            this.nombre = nombre;
            this.valor = cantidad;
            this.esInventario = true;
        }
    }

    private List<ItemDisplay> items = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onComprarClick(int idProducto);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<ItemDisplay> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Asegúrate de tener un layout llamado 'item_producto.xml'
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemDisplay item = items.get(position);

        holder.tvNombre.setText(item.nombre);

        // 🖼️ LA MAGIA DE LAS IMÁGENES ESTÁ AQUÍ
        int imageResId = getImageResource(item.nombre);
        holder.ivIcono.setImageResource(imageResId);

        if (item.esInventario) {
            // MODO INVENTARIO: Mostramos cantidad
            holder.tvPrecio.setText("x" + item.valor);
            holder.btnComprar.setVisibility(View.GONE); // Ocultar botón comprar
        } else {
            // MODO TIENDA: Mostramos precio y botón
            holder.tvPrecio.setText(item.valor + " CR");
            holder.btnComprar.setVisibility(View.VISIBLE);
            holder.btnComprar.setOnClickListener(v -> {
                if (listener != null) listener.onComprarClick(item.id);
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // -------------------------------------------------------------
    // 🎨 FUNCIÓN TRADUCTORA: DE TEXTO A IMAGEN (DRAWABLE)
    // -------------------------------------------------------------
    private int getImageResource(String nombreProducto) {
        if (nombreProducto == null) return R.drawable.katana;

        // Convertimos a minúsculas para evitar errores (Ej: "Katana" vs "katana")
        switch (nombreProducto.toLowerCase()) {
            case "katana":
                return R.drawable.katana;
            case "jeringuilla":
                return R.drawable.jeringuilla;
            case "chaleco antibalas":
            case "chaleco": // Por si acaso cambias el nombre
                return R.drawable.chaleco; // Asegúrate de tener chaleco.png
            case "bloque de energia":
            case "bloque":
                return R.drawable.energia; // Asegúrate de tener energia.png
            default:
                // Si no coincide, devolvemos una imagen por defecto
                return R.drawable.katana;
        }
    }

    // --- VIEWHOLDER ---
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio;
        ImageView ivIcono;
        Button btnComprar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asegúrate de que estos ID coinciden con tu 'item_producto.xml'
            tvNombre = itemView.findViewById(R.id.tvItemName);
            tvPrecio = itemView.findViewById(R.id.tvItemPrice);
            ivIcono = itemView.findViewById(R.id.ivItemImage);
            btnComprar = itemView.findViewById(R.id.btnItemAction);
        }
    }
}