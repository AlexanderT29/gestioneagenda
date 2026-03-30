package com.example.gestioneagenda.service.agenda;

import com.example.gestioneagenda.model.Agenda;
import com.example.gestioneagenda.repository.agenda.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService{

    @Autowired
    private AgendaRepository repository;

    @Override
    public List<Agenda> listAllElements(boolean eager) {
        return repository.findAll();
    }

    @Override
    public Agenda caricaSingoloElemento(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Agenda caricaSingoloElementoEager(Long id) {
        // TODO: Questo richiede di creare una @Query esplicita (o un @EntityGraph) in AgendaRepository
        // return repository.findByIdEager(id).orElse(null);
        return null;
    }


    @Override
    public List<Agenda> elencaImpegniPerUtente(String username) {
        return repository.findByUtente_Username(username);
    }

    @Override
    @Transactional
    public Agenda inserisciNuovo(Agenda agendaInstance) {
        return repository.save(agendaInstance);
    }

    @Override
    @Transactional
    public Agenda aggiorna(Agenda agendaInstance) {
        return repository.save(agendaInstance);
    }

    @Override
    @Transactional
    public void rimuovi(Long idToRemove) {
        repository.deleteById(idToRemove);
    }
}
