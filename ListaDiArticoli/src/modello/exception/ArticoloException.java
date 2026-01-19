package modello.exception;

/**
 * Eccezione specifica per la gestione degli errori relativi alla classe Articolo.
 * Viene lanciata quando si tenta di creare o modificare un articolo con dati non validi.
 */
public class ArticoloException extends Exception {
    
    /**
     * Costruttore vuoto per l'eccezione.
     */
    public ArticoloException() {
        super();
    }

    /**
     * Costruttore che accetta un messaggio di errore specifico.
     * @param message Il messaggio che descrive l'errore.
     */
    public ArticoloException(String message) {
        super(message);
    }
}