package com.example.gestioneagenda.service.agenda;

import com.example.gestioneagenda.model.Agenda;

import java.util.List;

public interface AgendaService {
    List<Agenda> listAllElements(boolean eager);

    Agenda caricaSingoloElemento(Long id);

    Agenda caricaSingoloElementoEager(Long id);

    Agenda aggiorna(Agenda agendaInstance);

    Agenda inserisciNuovo(Agenda agendaInstance);

    void rimuovi(Long idToRemove);
}
