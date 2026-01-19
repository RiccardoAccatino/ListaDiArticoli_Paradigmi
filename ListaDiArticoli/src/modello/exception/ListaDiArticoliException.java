package modello.exception;

/**
 * Eccezione specifica per la gestione degli errori relativi alla classe ListaDiArticoli.
 * Viene lanciata quando si verificano problemi nell'aggiunta, rimozione o ricerca di articoli in una lista.
 */
public class ListaDiArticoliException extends Exception {
    
    /**
     * Costruttore che accetta un messaggio di errore specifico.
     * @param message Il messaggio che descrive l'errore.
     */
    public ListaDiArticoliException(String message) {
        super(message);
    }
    
    /**
     * Costruttore vuoto per l'eccezione.
     */
    public ListaDiArticoliException() {
        super();
    }
}