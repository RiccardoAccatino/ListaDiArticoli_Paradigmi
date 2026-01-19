# 🛒 Lista di Articoli - Progetto Paradigmi

Benvenuto nel progetto **Lista di Articoli**. Questa applicazione Java permette di gestire liste della spesa o inventari di prodotti, offrendo funzionalità avanzate come un catalogo globale, la categorizzazione degli articoli e un sistema di recupero dati (cestino).

Il progetto è sviluppato seguendo il pattern architetturale **MVC (Model-View-Controller)** e supporta il salvataggio persistente dei dati.

## ✨ Funzionalità Principali

Basandosi sull'analisi del codice sorgente, l'applicazione offre:

* **Gestione Multipla:** Creazione e gestione di diverse liste di articoli (es. "Spesa Casa", "Ufficio").
* **Catalogo Globale:** Un database centrale di prodotti (`GestioneListe`) da cui attingere per popolare le singole liste.
* **CRUD Articoli:** Creazione, lettura, modifica e cancellazione degli articoli con attributi come *Nome*, *Prezzo*, *Categoria* e *Nota*.
* **Cestino e Ripristino:** Gli articoli rimossi non vengono eliminati subito, ma spostati in un "Cestino", permettendo il ripristino in caso di errore.
* **Persistenza Dati:** Salvataggio e caricamento automatico dello stato dell'applicazione su file binario (`dati_save.bin`) tramite serializzazione.
* **Interfaccia Doppia:**
    * **GUI (Grafica):** Interfaccia Swing interattiva per un utilizzo user-friendly.
    * **CLI (Riga di Comando):** Interfaccia testuale avviabile dal Main.

## 📂 Struttura del Progetto

Il codice è organizzato nei seguenti package:

* `main`: Contiene il punto di ingresso (`Main.java`) dell'applicazione.
* `modello`: Contiene la logica di business e le strutture dati (es. `Articolo`, `GestioneListe`, `ListaDiArticoli`) e le eccezioni personalizzate.
* `gui.grafica`: Contiene le classi per l'interfaccia grafica (View) e la logica di controllo (`Controller.java`).
* `gui.riga_comando`: Contiene le classi per l'interfaccia testuale.

## 🚀 Istruzioni per l'Esecuzione

### Prerequisiti
* Java Development Kit (JDK) 8 o superiore.

### Compilazione ed Esecuzione

1.  **Compila il progetto:**
    Posizionati nella cartella `src` ed esegui:
    ```bash
    javac main/Main.java
    ```

2.  **Avvia l'applicazione:**
    ```bash
    java main.Main
    ```

*Nota: Il metodo `main` attuale avvia di default l'interfaccia a riga di comando (`RigaComandoInterfaccia`).*

## 🛠 Tecnologie Utilizzate

* **Linguaggio:** Java
* **GUI Framework:** Java Swing (`JOptionPane`, `JButton`, ecc.)
* **Persistenza:** Java IO (`ObjectOutputStream`, `Serializable`)
* **Architettura:** Model-View-Controller (MVC)

## 📄 Note sullo Sviluppo

* **Pattern Singleton (Simulato):** La classe `GestioneListe` utilizza metodi statici per agire come unico punto di accesso ai dati (Database in-memory).
* **Gestione Errori:** Sono state implementate eccezioni personalizzate (`ArticoloException`, `GestioneListeException`) per gestire casi come prezzi negativi o nomi duplicati.

## 👤 Autore

Progetto sviluppato da [Il Tuo Nome] per il corso di Paradigmi di Programmazione.
