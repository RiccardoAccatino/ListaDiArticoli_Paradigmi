package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.ListaDiArticoliException;

/**
 * Classe di test JUnit per la classe {@link ListaDiArticoli}.
 * Verifica le operazioni di aggiunta, rimozione, ripristino e ricerca.
 */
class ListaDiArticoliTest {

    private ListaDiArticoli lista;

    /**
     * Inizializza una lista vuota prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        lista = new ListaDiArticoli("Spesa Settimanale");
    }

    /**
     * Test aggiunta articolo.
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testAggiungiArticolo() throws ArticoloException {
        lista.addArticolo("Uova", 300);
        assertEquals(1, lista.getNumeroArticoliValidi());
        assertEquals(0, lista.getNumeroArticoliCancellati());
    }

    /**
     * Test rimozione articolo (spostamento nel cestino).
     * @throws ArticoloException In caso di errore inatteso.
     * @throws ListaDiArticoliException In caso di errore inatteso.
     */
    @Test
    void testRimuoviArticolo() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Latte", 150);
        lista.removeArticolo("Latte");

        assertEquals(0, lista.getNumeroArticoliValidi());
        assertEquals(1, lista.getNumeroArticoliCancellati());
    }

    /**
     * Test rimozione di un articolo inesistente.
     */
    @Test
    void testRimuoviArticoloInesistente() {
        assertThrows(ListaDiArticoliException.class, () -> lista.removeArticolo("NonEsiste"));
    }

    /**
     * Test ripristino articolo dal cestino.
     * @throws ArticoloException In caso di errore inatteso.
     * @throws ListaDiArticoliException In caso di errore inatteso.
     */
    @Test
    void testRipristinaArticolo() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Pane", 200);
        lista.removeArticolo("Pane"); 
        lista.ripristina("Pane");     
        
        assertEquals(1, lista.getNumeroArticoliValidi());
        assertEquals(0, lista.getNumeroArticoliCancellati());
    }
    
    /**
     * Test svuotamento cestino.
     * @throws ArticoloException In caso di errore inatteso.
     * @throws ListaDiArticoliException In caso di errore inatteso.
     */
    @Test
    void testSvuotaCestino() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Acqua", 100);
        lista.removeArticolo("Acqua");
        lista.svuotaCestino();
        
        assertEquals(0, lista.getNumeroArticoliCancellati());
        assertEquals(0, lista.getNumeroArticoliValidi());
    }

    /**
     * Test ricerca articolo (validi e cestinati).
     * @throws ArticoloException In caso di errore inatteso.
     * @throws ListaDiArticoliException In caso di errore inatteso.
     */
    @Test
    void testSearchArticolo() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Biscotti", 250);
        lista.addArticolo("Birra", 300);
        
        Articolo trovato = lista.searchArticolo("Bisc");
        assertNotNull(trovato);
        assertEquals("Biscotti", trovato.getNome());
        
        lista.removeArticolo("Birra");
        Articolo trovatoCestino = lista.searchArticolo("Bir");
        assertNotNull(trovatoCestino);
        assertEquals("Birra", trovatoCestino.getNome());
        
        assertNull(lista.searchArticolo("Vino"));
    }
    
    /**
     * Test aggiunta articolo semplice.
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testAggiungiArticoloSemplice() throws ArticoloException {
        
        lista.addArticolo("Caramelle", 50); 
        Articolo a = lista.searchArticolo("Caramelle");
        assertNotNull(a);
        assertEquals("Non categorizzato", a.getCategoria());
    }
    
    /**
     * Test case-sensitivity della ricerca.
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testRicercaMaiuscoleMinuscole() throws ArticoloException {
        lista.addArticolo("Latte", 100);
        Articolo risultato = lista.searchArticolo("latte");
        assertNull(risultato, "La ricerca dovrebbe essere case-sensitive con l'attuale implementazione");
    }
}