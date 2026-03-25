-- 2. CREAZIONE DELLE TABELLE

-- Tabella per UtenteDAO (Clienti e Amministratori)
CREATE TABLE utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    ruolo VARCHAR(20)DEFAULT NULL,
   tipo VARCHAR(20) NOT NULL,
);

-- Tabella Barbieri
CREATE TABLE barbieri (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    specializzazione VARCHAR(100)
);

-- Tabella per ProdottoDAO (Il magazzino)
CREATE TABLE prodotti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    quantitaInScorta INT NOT NULL
);

-- Tabella per ServizioDAO (Il listino prezzi di tagli e barbe)
CREATE TABLE servizi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    prezzo DOUBLE NOT NULL,
    durataMinuti INT NOT NULL
);

-- Tabella per PrenotazioneDAO (Gli appuntamenti)
CREATE TABLE prenotazioni (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
      id_barbiere INT NOT NULL,  
    data_ora DATETIME NOT NULL,
    
    -- Collegamenti (Foreign Keys): colleghiamo l'appuntamento al cliente e al servizio
    FOREIGN KEY (id_cliente) REFERENCES utenti(id) ON DELETE CASCADE,
    FOREIGN KEY (id_barbiere) REFERENCES barbieri(id) ON DELETE CASCADE
);

-- Tabella per FeedbackDAO (Le recensioni)
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    voto INT NOT NULL, -- Da 1 a 5 stelle
    commento TEXT,
    
    -- Collegamento: ogni recensione appartiene a un cliente
    FOREIGN KEY (id_cliente) REFERENCES utenti(id) ON DELETE CASCADE
);
-- organizza più servizi per una prenotazione
CREATE TABLE IF NOT EXISTS prenotazioni_servizi (
    id_prenotazione INT NOT NULL,
    id_servizio INT NOT NULL,
    
    PRIMARY KEY (id_prenotazione, id_servizio),
    FOREIGN KEY (id_prenotazione) REFERENCES prenotazioni(id) ON DELETE CASCADE,
    FOREIGN KEY (id_servizio) REFERENCES servizi(id) ON DELETE CASCADE
);
