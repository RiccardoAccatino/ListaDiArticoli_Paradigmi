package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.exception.ArticoloException;

class ArticoloTest {

    @Test
    void testCostruttoreCompletoValido() throws ArticoloException {
        Articolo a = new Articolo("Latte", "Alimentari", 150, "Fresco");
        assertEquals("Latte", a.getNome());
        assertEquals("Alimentari", a.getCategoria());
        assertEquals(150, a.getPrezzo());
        assertEquals("Fresco", a.getNota());
    }

    @Test
    void testCostruttoreNomePrezzo() throws ArticoloException {
        Articolo a = new Articolo("Pane", 200);
        assertEquals("Pane", a.getNome());
        assertEquals(200, a.getPrezzo());
        assertEquals("Non categorizzato", a.getCategoria());
    }

    @Test
    void testSetNomeInvalido() {
        assertThrows(ArticoloException.class, () -> new Articolo(""));
        assertThrows(ArticoloException.class, () -> new Articolo(null));
    }

    @Test
    void testSetPrezzoInvalido() {
        assertThrows(ArticoloException.class, () -> new Articolo("Pasta", -10));
    }
    
    @Test
    void testSetPrezzoValido() throws ArticoloException {
        Articolo a = new Articolo("Pasta", 100);
        a.setPrezzo(120);
        assertEquals(120, a.getPrezzo());
    }
    
    @Test
    void testSetCategoriaNull() throws ArticoloException {
        Articolo a = new Articolo("Mela", 50);
        a.setCategoria(null);
        assertEquals("Non categorizzato", a.getCategoria());
    }
    @Test
    void testNomeSoloSpazi() {
        assertThrows(ArticoloException.class, () -> new Articolo("   ", 100));
    }
}