package com.example.restclientapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restclientapp.api.AuthService;
import com.example.restclientapp.api.RetrofitClient;
import com.example.restclientapp.model.Evento;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        recyclerView = findViewById(R.id.recyclerEventos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventosAdapter();
        recyclerView.setAdapter(adapter);

        Button btnVolver = findViewById(R.id.btnVolverEventos);
        btnVolver.setOnClickListener(v -> finish());

        cargarEventos();
    }

    private void cargarEventos() {
        AuthService service = RetrofitClient.getApiService();
        Call<List<Evento>> call = service.getEventos();

        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setEventos(response.body());
                } else {
                    Toast.makeText(EventosActivity.this, "Error al cargar eventos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(EventosActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
