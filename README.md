# PROGETTO
# 💈 Barber Shop Management System

Un'applicazione Java progettata per gestire a 360 gradi le operazioni di un salone da barbiere. Il sistema permette di amministrare le prenotazioni dei clienti, lo staff, il magazzino dei prodotti e i feedback sui servizi.

## 🎯 Obiettivi del Progetto

Lo scopo principale di questo progetto è sviluppare un sistema informatico completo per digitalizzare e ottimizzare i processi di un salone. Gli obiettivi chiave includono:

* **Efficienza Operativa:** Semplificare la gestione degli appuntamenti, assegnando correttamente le risorse (barbieri e servizi) per evitare sovrapposizioni.
* **Controllo delle Risorse:** Monitorare in tempo reale il magazzino e il consumo dei prodotti, automatizzando la segnalazione delle scorte esaurite.
* **Qualità del Servizio:** Fornire un sistema di feedback per tracciare la soddisfazione dei clienti e valutare le prestazioni del salone.
* **Solidità Architetturale:** Applicare rigorosamente i paradigmi della programmazione Object-Oriented (OOP) e i design pattern (come MVC e DAO) per garantire un codice pulito, modulare e facilmente scalabile in futuro.

---


## 🚀 Funzionalità Principali

Attualmente, il sistema modella le seguenti aree di business:

* **Gestione Utenti:**
    * **Amministratore:** Gestione globale del sistema.
    * **Barbiere:** Staff del salone con le proprie specializzazioni.
    * **Cliente:** Utenti che possono registrarsi, prenotare servizi e lasciare recensioni.
* **Gestione Prenotazioni:**
    * Creazione di appuntamenti associando un Cliente, un Barbiere e uno o più Servizi (es. Taglio + Barba).
    * Calcolo automatico della durata totale e del prezzo complessivo.
* **Gestione Magazzino (Inventory):**
    * Tracciamento dei Prodotti utilizzati nel salone.
    * Monitoraggio delle quantità in scorta e segnalazione di prodotti esauriti.
* **Sistema di Feedback:**
    * Possibilità per i clienti di lasciare un voto e un commento legati a una specifica prenotazione.

## 🛠️ Stack Tecnologico

* **Linguaggio:** Java
* **Architettura:** MVC (Model-View-Controller) / DAO Pattern (in fase di implementazione)
* **Database:** (Inserisci qui il database che useremo, es. MySQL o PostgreSQL)

## 📂 Struttura del Progetto

Il codice è organizzato in package tematici per garantire modularità e pulizia:

* `unipv.barbershop.model.user`: Classi astratte e concrete per gli attori del sistema (`Utente`, `Cliente`, `Amministratore`).
* `unipv.barbershop.model.staff`: Classi relative ai dipendenti (`Barbiere`).
* `unipv.barbershop.model.booking`: Logica legata agli appuntamenti (`Prenotazione`, `Servizio`).
* `unipv.barbershop.model.inventory`: Gestione risorse fisiche (`Prodotto`).
* `unipv.barbershop.model.feedback`: Gestione recensioni (`Feedback`).
* `unipv.barbershop.dao`: (*Work in progress*) Interfacce e implementazioni per l'accesso ai dati.

## 🚧 Stato dello Sviluppo

- [x] Progettazione e implementazione dei Model (Domain Objects).
- [x] Implementazione del Data Access Object (DAO) pattern.
- [x] Configurazione connessione al Database e relative eccezioni.
- [x] Sviluppo logica di business e View.