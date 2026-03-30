package com.example.gestioneagenda.web.api;

import com.example.gestioneagenda.dto.AgendaDTO;
import com.example.gestioneagenda.model.Agenda;
import com.example.gestioneagenda.model.Ruolo;
import com.example.gestioneagenda.service.agenda.AgendaService;
import com.example.gestioneagenda.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private UtenteService utenteService;


    @GetMapping
    public ResponseEntity<List<AgendaDTO>> listAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Ruolo.ROLE_ADMIN));

        List<Agenda> modelList;
        if (isAdmin) {
            modelList = agendaService.listAllElements(false);
        } else {
            modelList = agendaService.elencaImpegniPerUtente(username);
        }


        List<AgendaDTO> dtoList = modelList.stream()
                .map(agenda -> AgendaDTO.buildAgendaDTOFromModel(agenda, isAdmin))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
}
