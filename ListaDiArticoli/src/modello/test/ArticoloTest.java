package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.exception.ArticoloException;

/**
 * Classe di test JUnit per la classe {@link Articolo}.
 * Verifica la corretta creazione e gestione degli articoli.
 */
class ArticoloTest {

    /**
     * Test del costruttore completo con parametri validi.
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testCostruttoreCompletoValido() throws ArticoloException {
        Articolo a = new Articolo("Latte", "Alimentari", 150, "Fresco");
        assertEquals("Latte", a.getNome());
        assertEquals("Alimentari", a.getCategoria());
        assertEquals(150, a.getPrezzo());
        assertEquals("Fresco", a.getNota());
    }

    /**
     * Test del costruttore parziale (Nome e Prezzo).
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testCostruttoreNomePrezzo() throws ArticoloException {
        Articolo a = new Articolo("Pane", 200);
        assertEquals("Pane", a.getNome());
        assertEquals(200, a.getPrezzo());
        assertEquals("Non categorizzato", a.getCategoria());
    }

    /**
     * Test che verifica il lancio dell'eccezione con nomi non validi.
     */
    @Test
    void testSetNomeInvalido() {
        assertThrows(ArticoloException.class, () -> new Articolo(""));
        assertThrows(ArticoloException.class, () -> new Articolo(null));
    }

    /**
     * Test che verifica il lancio dell'eccezione con prezzi negativi.
     */
    @Test
    void testSetPrezzoInvalido() {
        assertThrows(ArticoloException.class, () -> new Articolo("Pasta", -10));
    }
    
    /**
     * Test per la modifica valida del prezzo.
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testSetPrezzoValido() throws ArticoloException {
        Articolo a = new Articolo("Pasta", 100);
        a.setPrezzo(120);
        assertEquals(120, a.getPrezzo());
    }
    
    /**
     * Test che verifica che settare la categoria a null imposti il valore di default.
     * @throws ArticoloException In caso di errore inatteso.
     */
    @Test
    void testSetCategoriaNull() throws ArticoloException {
        Articolo a = new Articolo("Mela", 50);
        a.setCategoria(null);
        assertEquals("Non categorizzato", a.getCategoria());
    }
    
    /**
     * Test che verifica che un nome composto solo da spazi lanci eccezione.
     */
    @Test
    void testNomeSoloSpazi() {
        assertThrows(ArticoloException.class, () -> new Articolo("   ", 100));
    }
}