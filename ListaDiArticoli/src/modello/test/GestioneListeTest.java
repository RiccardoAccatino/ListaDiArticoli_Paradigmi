package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modello.GestioneListe;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;

class GestioneListeTest {

    @BeforeEach
    void setUp() {
        GestioneListe.setListediarticoli(new ArrayList<>());
        GestioneListe.setArticoli(new ArrayList<>());
        GestioneListe.setCategorie(new ArrayList<>());
    }

    @Test
    void testCreateListaDiArticoli() throws GestioneListeException {
        GestioneListe.createListaDiArticoli("Lista Vacanze");
        assertEquals(1, GestioneListe.getListediarticoli().size());
        assertEquals("Lista Vacanze", GestioneListe.getListediarticoli().get(0).getNome());
    }

    @Test
    void testCreateListaDuplicata() throws GestioneListeException {
        GestioneListe.createListaDiArticoli("Lista Spesa");
     
        assertThrows(GestioneListeException.class, () -> GestioneListe.createListaDiArticoli("Lista Spesa"));
    }

    @Test
    void testRemoveListaDiArticoli() throws GestioneListeException {
        GestioneListe.createListaDiArticoli("Da Rimuovere");
        GestioneListe.removeListaDiArticoli("Da Rimuovere");
        assertEquals(0, GestioneListe.getListediarticoli().size());
    }
    
    @Test
    void testRemoveListaNonEsistente() {
        assertThrows(GestioneListeException.class, () -> GestioneListe.removeListaDiArticoli("Fantasma"));
    }

    @Test
    void testCreateCategoria() throws GestioneListeException {
        GestioneListe.createCategoria("Elettronica");
        assertEquals(1, GestioneListe.getCategorie().size());
        assertTrue(GestioneListe.getCategorie().contains("Elettronica"));
    }

    @Test
    void testCreateArticoloCatalogo() throws ArticoloException, GestioneListeException {
        GestioneListe.createArticolo("Computer");
        assertEquals(1, GestioneListe.getArticoli().size());
        assertEquals("Computer", GestioneListe.getArticoli().get(0).getNome());
    }
    
    @Test
    void testCreateArticoloCatalogoDuplicato() throws ArticoloException, GestioneListeException {
        GestioneListe.createArticolo("Mouse");
        assertThrows(GestioneListeException.class, () -> GestioneListe.createArticolo("Mouse"));
    }
}