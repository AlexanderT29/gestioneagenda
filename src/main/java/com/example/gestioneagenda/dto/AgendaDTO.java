package com.example.gestioneagenda.dto;

import com.example.gestioneagenda.model.Agenda;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgendaDTO {

    private Long id;

    @NotBlank(message = "{descrizione.notblank}")
    private String descrizione;

    @NotNull(message = "{dataOraInizio.notnull}")
    private LocalDateTime dataOraInizio;

    @NotNull(message = "{dataOraFine.notnull}")
    private LocalDateTime dataOraFine;

    private Long utenteId;

    private UtenteDTO utente;

    public AgendaDTO() {
    }

    public AgendaDTO(Long id, String descrizione, LocalDateTime dataOraInizio, LocalDateTime dataOraFine) {
        this.id = id;
        this.descrizione = descrizione;
        this.dataOraInizio = dataOraInizio;
        this.dataOraFine = dataOraFine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getDataOraInizio() {
        return dataOraInizio;
    }

    public void setDataOraInizio(LocalDateTime dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public LocalDateTime getDataOraFine() {
        return dataOraFine;
    }

    public void setDataOraFine(LocalDateTime dataOraFine) {
        this.dataOraFine = dataOraFine;
    }

    public Long getUtenteId() {
        return utenteId;
    }

    public void setUtenteId(Long utenteId) {
        this.utenteId = utenteId;
    }

    public UtenteDTO getUtente() {
        return utente;
    }

    public void setUtente(UtenteDTO utente) {
        this.utente = utente;
    }

    public Agenda buildAgendaModel(boolean includeIdUtente) {
        Agenda result = new Agenda(this.id, this.descrizione, this.dataOraInizio, this.dataOraFine, null);
        if (includeIdUtente && this.utenteId != null) {
            com.example.gestioneagenda.model.Utente u = new com.example.gestioneagenda.model.Utente();
            u.setId(this.utenteId);
            result.setUtente(u);
        }
        return result;
    }

    public static AgendaDTO buildAgendaDTOFromModel(Agenda agendaModel, boolean includeUtente) {
        AgendaDTO result = new AgendaDTO(agendaModel.getId(), agendaModel.getDescrizione(),
                agendaModel.getDataOraInizio(), agendaModel.getDataOraFine());

        if (includeUtente && agendaModel.getUtente() != null) {
            result.setUtente(UtenteDTO.buildUtenteDTOFromModel(agendaModel.getUtente(), false));
            result.setUtenteId(agendaModel.getUtente().getId());
        }

        return result;
    }

    public static Set<AgendaDTO> createAgendaDTOSetFromModelSet(Set<Agenda> modelListInput, boolean includeUtente) {
        return modelListInput.stream()
                .map(agendaEntity -> AgendaDTO.buildAgendaDTOFromModel(agendaEntity, includeUtente))
                .collect(Collectors.toSet());
    }
}
