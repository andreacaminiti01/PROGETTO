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

## 🛠️ Tecnologie e Pattern Implementati

### 1. Singleton & Gestione della Connessione
* **Pattern Singleton**: La classe `DBConnection` assicura l'esistenza di un'unica istanza del gestore del database, ottimizzando la lettura dei file di configurazione e il consumo di risorse.
* **Configurazione Esterna**: Le credenziali (URL, user, password) sono caricate dal file `db.properties`. Per sicurezza, questo file è escluso dal versionamento tramite `.gitignore`.

### 2. Robustezza e Transazioni SQL (Core Logic)
* **Transazioni ACID**: Il metodo `salvaPrenotazione` nel `PrenotazioneDAO` gestisce operazioni atomiche. Utilizza `setAutoCommit(false)`, `commit()` e `rollback()` per garantire che la prenotazione e i relativi servizi (tabella ponte) vengano salvati correttamente insieme, evitando dati inconsistenti in caso di errore.
* **Prevenzione SQL Injection**: Tutte le interazioni con il DB avvengono tramite `PreparedStatement` per sanificare l'input.
* **Check Disponibilità**: Il sistema verifica se il barbiere è libero nella fascia oraria selezionata tramite `isBarbiereDisponibile` prima di confermare l'appuntamento.

### 3. Gestione delle Eccezioni (Business Rules)
Il backend utilizza eccezioni di tipo `RuntimeException` per far rispettare le regole del dominio in modo pulito:
* **`PostiEsauritiException`**: Lanciata se il barbiere è già occupato.
* **`ScortaInsufficienteException`**: Impedisce la riduzione del magazzino se il prodotto è esaurito.
* **`CredenzialiErrateException`**: Gestisce i fallimenti del login in modo sicuro.
* **`InvalidFormatException` & `EmptyFieldException`**: Validano email, password e campi obbligatori durante l'istanziazione degli oggetti.

## 🚀 Istruzioni per il Setup (Collaboratori)

Per rendere il progetto operativo sulla propria macchina locale:

1.  **Database**: Eseguire lo script `init_database.sql` in MySQL Workbench per generare lo schema `barbershop` e tutte le tabelle (incluse Foreign Keys e vincoli `ON DELETE CASCADE`).
2.  **Configurazione**: Creare un file denominato `db.properties` nella cartella root del progetto con il seguente schema:
    ```properties
    db.url=jdbc:mysql://localhost:3306/barbershop
    db.user=IL_TUO_USERNAME
    db.password=LA_TUA_PASSWORD
    ```
3.  **Librerie**: Assicurarsi che il driver JDBC (`mysql-connector-j`) sia incluso nel Build Path del progetto.

---
*Backend sviluppato seguendo gli standard di Ingegneria del Software - Università di Pavia.*