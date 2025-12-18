package com.example.restclientapp.model;

public class EventoParticipacion {
    String idUsuario;
    String idEvento;

    public EventoParticipacion(String idUsuario, String idEvento) {
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
    }
}