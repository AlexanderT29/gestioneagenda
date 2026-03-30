package com.example.gestioneagenda.dto;

import com.example.gestioneagenda.model.Agenda;
import com.example.gestioneagenda.model.Ruolo;
import com.example.gestioneagenda.model.StatoUtente;
import com.example.gestioneagenda.model.Utente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UtenteDTO {

    private Long id;

    @NotBlank(message = "{username.notblank}")
    @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
    private String username;

    @NotBlank(message = "{password.notblank}")
    @Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
    private String password;

    private String confermaPassword;

    @NotBlank(message = "{nome.notblank}")
    private String nome;

    @NotBlank(message = "{cognome.notblank}")
    private String cognome;

    @NotBlank(message = "{email.notblank}")
    private String email;

    private LocalDate dateCreated;

    private StatoUtente stato;

    private Long[] ruoliIds;

    private Set<AgendaDTO> impegni = new HashSet<>();

    public UtenteDTO() {
    }

    public UtenteDTO(Long id, String username, String nome, String cognome, StatoUtente stato) {
        super();
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.stato = stato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public StatoUtente getStato() {
        return stato;
    }

    public void setStato(StatoUtente stato) {
        this.stato = stato;
    }

    public String getConfermaPassword() {
        return confermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long[] getRuoliIds() {
        return ruoliIds;
    }

    public void setRuoliIds(Long[] ruoliIds) {
        this.ruoliIds = ruoliIds;
    }

    public Set<AgendaDTO> getImpegni() {
        return impegni;
    }

    public void setImpegni(Set<AgendaDTO> impegni) {
        this.impegni = impegni;
    }

    public Utente buildUtenteModel(boolean includeIdRoles, boolean includeImpegni) {
        Utente result = new Utente(this.id, this.username, this.password, this.nome, this.cognome, this.email,
                this.dateCreated, this.stato);
        if (includeIdRoles && ruoliIds != null)
            result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));

        if (includeImpegni && this.impegni != null && !this.impegni.isEmpty()) {
            Set<Agenda> impegniModel = this.impegni.stream()
                    .map(agendaDTO -> {
                        Agenda agendaModel = agendaDTO.buildAgendaModel(false);
                        agendaModel.setUtente(result);
                        return agendaModel;
                    }).collect(Collectors.toSet());

            result.setImpegni(impegniModel);
        }

        return result;
    }

    public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel, Boolean includeImpegni) {
        UtenteDTO result = new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getNome(),
                utenteModel.getCognome(), utenteModel.getStato());

        if (!utenteModel.getRuoli().isEmpty())
            result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
                    .toArray(new Long[] {});

        if(includeImpegni && utenteModel.getImpegni() != null && !utenteModel.getImpegni().isEmpty())
            result.impegni = AgendaDTO.createAgendaDTOSetFromModelSet(utenteModel.getImpegni(), false);

        return result;
    }



}
