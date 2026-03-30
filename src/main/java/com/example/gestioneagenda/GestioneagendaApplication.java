package com.example.gestioneagenda;

import com.example.gestioneagenda.model.Agenda;
import com.example.gestioneagenda.model.Ruolo;
import com.example.gestioneagenda.model.Utente;
import com.example.gestioneagenda.service.agenda.AgendaService;
import com.example.gestioneagenda.service.ruolo.RuoloService;
import com.example.gestioneagenda.service.utente.UtenteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootApplication
public class GestioneagendaApplication implements CommandLineRunner {

	final private RuoloService ruoloServiceInstance;
	final private UtenteService utenteServiceInstance;
	final private AgendaService agendaServiceInstance;

	public GestioneagendaApplication(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance, AgendaService agendaServiceInstance){
		this.ruoloServiceInstance = ruoloServiceInstance;
		this.utenteServiceInstance = utenteServiceInstance;
		this.agendaServiceInstance = agendaServiceInstance;
	}



	public static void main(String[] args) {
		SpringApplication.run(GestioneagendaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", Ruolo.ROLE_CLASSIC_USER));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della
		// password non lo
		// faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", LocalDate.now());
			admin.setEmail("a.admin@prova.it");
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("user") == null) {
			Utente classicUser = new Utente("user", "user", "Antonio", "Verdi", LocalDate.now());
			classicUser.setEmail("u.user@prova.it");
			classicUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser.getId());
		}

		if (utenteServiceInstance.findByUsername("user1") == null) {
			Utente classicUser1 = new Utente("user1", "user1", "Antonioo", "Verdii", LocalDate.now());
			classicUser1.setEmail("u.user1@prova.it");
			classicUser1.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser1);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser1.getId());
		}

		if (utenteServiceInstance.findByUsername("user2") == null) {
			Utente classicUser2 = new Utente("user2", "user2", "Antoniooo", "Verdiii", LocalDate.now());
			classicUser2.setEmail("u.user2@prova.it");
			classicUser2.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser2);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser2.getId());
		}

		Utente user = utenteServiceInstance.findByUsername("user");

		if (user != null && agendaServiceInstance.elencaImpegniPerUtente(user.getUsername()).isEmpty()) {
			Agenda appuntamentoDentista = new Agenda("Appuntamento Dentista",
					LocalDateTime.of(2026, Month.APRIL, 12, 11, 30),
					LocalDateTime.of(2026, Month.APRIL, 12, 13, 00),
					user);
			agendaServiceInstance.inserisciNuovo(appuntamentoDentista);
		}
		Utente user1 = utenteServiceInstance.findByUsername("user1");
		System.out.println("user1 id: " + user1.getId());
		if (user1 != null && agendaServiceInstance.elencaImpegniPerUtente(user1.getUsername()).isEmpty()) {
			Agenda appuntamentoPoste = new Agenda("Appuntamento Poste",
					LocalDateTime.of(2026, Month.JUNE, 14, 9, 30),
					LocalDateTime.of(2026, Month.JUNE, 14, 12, 30),
					user1);
			agendaServiceInstance.inserisciNuovo(appuntamentoPoste);
		}
	}

}
