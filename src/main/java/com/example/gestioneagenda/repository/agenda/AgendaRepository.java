package com.example.gestioneagenda.repository.agenda;

import com.example.gestioneagenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query("from Agenda a  join fetch a.utente where a.id = :id")
    Agenda findByIdEager(@Param("id") Long id);

    List<Agenda> findByUtente_Username(String username);
}
