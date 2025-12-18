package com.example.restclientapp;

import android.content.SharedPreferences; // <--- Importante
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclientapp.api.AuthService;
import com.example.restclientapp.api.RetrofitClient;
import com.example.restclientapp.model.Evento;
import com.example.restclientapp.model.EventoParticipacion; // <--- Importante

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


        adapter.setListener(new EventosAdapter.OnEventoClickListener() {
            @Override
            public void onApuntarseClick(String idEvento) {
                apuntarseAEvento(idEvento);
            }
        });

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


    private void apuntarseAEvento(String idEvento) {

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int userIdInt = prefs.getInt("userId", -1);

        if (userIdInt == -1) {
            Toast.makeText(this, "Error: No estás logueado correctamente", Toast.LENGTH_SHORT).show();
            return;
        }


        String idUsuario = String.valueOf(userIdInt);


        EventoParticipacion request = new EventoParticipacion(idUsuario, idEvento);


        AuthService service = RetrofitClient.getApiService();
        Call<Void> call = service.participarEvento(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EventosActivity.this, "¡Te has apuntado exitosamente!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EventosActivity.this, "Error al apuntarse: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EventosActivity.this, "Error de red: No se pudo conectar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}