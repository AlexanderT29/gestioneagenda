package com.example.gestioneagenda.repository.ruolo;


import com.example.gestioneagenda.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuoloRepository extends JpaRepository<Ruolo, Long> {

    Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
}
