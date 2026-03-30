package com.example.gestioneagenda.service.utente;

import com.example.gestioneagenda.model.Utente;

import java.util.List;

public interface UtenteService {

    public List<Utente> listAllUtenti();

    public Utente caricaSingoloUtente(Long id);

    public Utente caricaSingoloUtenteConRuoli(Long id);

    public void aggiorna(Utente utenteInstance);

    public void inserisciNuovo(Utente utenteInstance);

    public void rimuovi(Long idToRemove);
}
