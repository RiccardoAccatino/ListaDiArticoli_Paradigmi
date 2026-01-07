package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.ListaDiArticoliException;

class ListaDiArticoliTest {

    private ListaDiArticoli lista;

    @BeforeEach
    void setUp() {
        lista = new ListaDiArticoli("Spesa Settimanale");
    }

    @Test
    void testAggiungiArticolo() throws ArticoloException {
        lista.addArticolo("Uova", 300);
        assertEquals(1, lista.getNumeroArticoliValidi());
        assertEquals(0, lista.getNumeroArticoliCancellati());
    }

    @Test
    void testRimuoviArticolo() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Latte", 150);
        lista.removeArticolo("Latte");

        assertEquals(0, lista.getNumeroArticoliValidi());
        assertEquals(1, lista.getNumeroArticoliCancellati());
    }

    @Test
    void testRimuoviArticoloInesistente() {
        assertThrows(ListaDiArticoliException.class, () -> lista.removeArticolo("NonEsiste"));
    }

    @Test
    void testRipristinaArticolo() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Pane", 200);
        lista.removeArticolo("Pane"); 
        lista.ripristina("Pane");     
        
        assertEquals(1, lista.getNumeroArticoliValidi());
        assertEquals(0, lista.getNumeroArticoliCancellati());
    }
    
    @Test
    void testSvuotaCestino() throws ArticoloException, ListaDiArticoliException {
        lista.addArticolo("Acqua", 100);
        lista.removeArticolo("Acqua");
        lista.svuotaCestino();
        
        assertEquals(0, lista.getNumeroArticoliCancellati());
        assertEquals(0, lista.getNumeroArticoliValidi());
    }

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
    
    @Test
    void testAggiungiArticoloSemplice() throws ArticoloException {
        
        lista.addArticolo("Caramelle", 50); 
        Articolo a = lista.searchArticolo("Caramelle");
        assertNotNull(a);
        assertEquals("Non categorizzato", a.getCategoria());
    }
    
    @Test
    void testRicercaMaiuscoleMinuscole() throws ArticoloException {
        lista.addArticolo("Latte", 100);
        Articolo risultato = lista.searchArticolo("latte");
        assertNull(risultato, "La ricerca dovrebbe essere case-sensitive con l'attuale implementazione");
    }
}