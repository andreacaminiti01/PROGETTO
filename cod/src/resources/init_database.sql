-- 2. CREAZIONE DELLE TABELLE

-- =============================================
-- SCRIPT DI INIZIALIZZAZIONE DATABASE BARBERSHOP
-- =============================================

-- 1. UTENTI (Gestisce sia Clienti che Amministratori/Barbieri)
-- Come visto in UtenteDAO, il campo 'tipo' distingue il ruolo
CREATE TABLE utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    ruolo VARCHAR(20) DEFAULT NULL, -- Dettaglio specifico (es. 'Proprietario')
    tipo VARCHAR(20) NOT NULL       -- 'CLIENTE' o 'AMMINISTRATORE'
);

-- 2. SERVIZI (Il listino prezzi: Taglio, Barba, ecc.)
CREATE TABLE servizi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    prezzo DOUBLE NOT NULL,
    durataMinuti INT NOT NULL
);

-- 3. PRODOTTI (Gestione magazzino)
CREATE TABLE prodotti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    quantitaInScorta INT NOT NULL
);

-- 4. PRENOTAZIONI (Gli appuntamenti)
-- Entrambi gli ID puntano alla tabella utenti per coerenza con il DAO
CREATE TABLE prenotazioni (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_barbiere INT NOT NULL,  
    data_ora DATETIME NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES utenti(id) ON DELETE CASCADE,
    FOREIGN KEY (id_barbiere) REFERENCES utenti(id) ON DELETE CASCADE 
);

-- 5. PRENOTAZIONI_SERVIZI (Tabella ponte per la relazione molti-a-molti)
-- Permette di associare più servizi a una singola prenotazione
CREATE TABLE prenotazioni_servizi (
    id_prenotazione INT NOT NULL,
    id_servizio INT NOT NULL,
    PRIMARY KEY (id_prenotazione, id_servizio),
    FOREIGN KEY (id_prenotazione) REFERENCES prenotazioni(id) ON DELETE CASCADE,
    FOREIGN KEY (id_servizio) REFERENCES servizi(id) ON DELETE CASCADE
);

-- 6. FEEDBACK (Le recensioni degli appuntamenti completati)
-- Collegato sia all'utente che alla specifica prenotazione
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_prenotazione INT NOT NULL UNIQUE, -- Un feedback per ogni prenotazione
    voto INT NOT NULL CHECK (voto BETWEEN 1 AND 5),
    commento TEXT,
    FOREIGN KEY (id_cliente) REFERENCES utenti(id) ON DELETE CASCADE,
    FOREIGN KEY (id_prenotazione) REFERENCES prenotazioni(id) ON DELETE CASCADE
);
