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
 * <p>
 * Aggiornata per verificare le nuove funzionalità:
 * <ul>
 * <li>Calcolo del prezzo totale.</li>
 * <li>Ricerca per prefisso.</li>
 * <li>Iterabilità della lista (Interfaccia Iterable).</li>
 * </ul>
 * </p>
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
     * Test ricerca articolo tramite prefisso.
     * Verifica che trovi articoli validi e cancellati usando startsWith.
     * @throws ArticoloException In caso di errore inatteso.
     * @throws ListaDiArticoliException In caso di errore inatteso.
     */
    @Test
    void testSearchArticoloPerPrefisso() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Nutella", 400);
        lista.addArticolo("Noci", 300);
        
        Articolo trovato = lista.searchArticolo("Nut");
        assertNotNull(trovato);
        assertEquals("Nutella", trovato.getNome());
        
        Articolo nonTrovato = lista.searchArticolo("ella");
        assertNull(nonTrovato, "La ricerca non deve trovare suffissi, solo prefissi.");

        lista.removeArticolo("Noci");
        Articolo trovatoCestino = lista.searchArticolo("No");
        assertNotNull(trovatoCestino);
        assertEquals("Noci", trovatoCestino.getNome());
    }
    
    /**
     * Test per il calcolo del prezzo totale.
     * Verifica che sommi solo gli articoli validi e non quelli nel cestino.
     * @throws ArticoloException In caso di errore.
     * @throws ListaDiArticoliException In caso di errore.
     */
    @Test
    void testCalcolaPrezzoTotale() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Pane", 100);
        lista.addArticolo("Latte", 150);
        lista.addArticolo("Vino", 5);
        assertEquals(750, lista.calcolaPrezzoTotale());
        
        lista.removeArticolo("Vino");
        
        assertEquals(250, lista.calcolaPrezzoTotale());
    }

    /**
     * Test per l'iterabilità della lista.
     * Verifica che il ciclo for-each scorra sia articoli validi che cancellati.
     * @throws ArticoloException In caso di errore.
     * @throws ListaDiArticoliException In caso di errore.
     */
    @Test
    void testIterabile() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("A", 10);
        lista.addArticolo("B", 20);
        lista.removeArticolo("B"); 
        
        int conteggio = 0;
        boolean trovatoA = false;
        boolean trovatoB = false;

        for (Articolo a : lista) {
            conteggio++;
            if (a.getNome().equals("A")) trovatoA = true;
            if (a.getNome().equals("B")) trovatoB = true;
        }
        
        assertEquals(2, conteggio, "L'iteratore deve contare sia validi che cancellati");
        assertTrue(trovatoA);
        assertTrue(trovatoB);
    }
}