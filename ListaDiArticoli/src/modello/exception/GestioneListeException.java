package modello.exception;

/**
 * Eccezione specifica per la gestione degli errori nella classe GestioneListe.
 * Viene lanciata in caso di problemi con la creazione, rimozione o gestione delle liste e categorie.
 */
public class GestioneListeException extends Exception {
    
    /**
     * Costruttore che accetta un messaggio di errore specifico.
     * @param message Il messaggio che descrive l'errore.
     */
    public GestioneListeException(String message) {
        super(message);
    }

    /**
     * Costruttore vuoto per l'eccezione.
     */
    public GestioneListeException() {
        super();
    }
}