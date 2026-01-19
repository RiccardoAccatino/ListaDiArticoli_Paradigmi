package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modello.GestioneListe;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;

/**
 * Classe di test JUnit per la classe {@link GestioneListe}.
 * Verifica le operazioni globali su liste, categorie e catalogo.
 */
class GestioneListeTest {

    /**
     * Prepara l'ambiente di test resettando le liste statiche.
     */
    @BeforeEach
    void setUp() {
        GestioneListe.setListediarticoli(new ArrayList<>());
        GestioneListe.setArticoli(new ArrayList<>());
        GestioneListe.setCategorie(new ArrayList<>());
    }

    /**
     * Test per la creazione di una nuova lista.
     * @throws GestioneListeException In caso di errore inatteso.
     */
    @Test
    void testCreateListaDiArticoli() throws GestioneListeException {
        GestioneListe.createListaDiArticoli("Lista Vacanze");
        assertEquals(1, GestioneListe.getListediarticoli().size());
        assertEquals("Lista Vacanze", GestioneListe.getListediarticoli().get(0).getNome());
    }

    /**
     * Test che verifica l'impossibilità di creare liste duplicate.
     * @throws GestioneListeException In caso di errore inatteso (primo create).
     */
    @Test
    void testCreateListaDuplicata() throws GestioneListeException {
        GestioneListe.createListaDiArticoli("Lista Spesa");
     
        assertThrows(GestioneListeException.class, () -> GestioneListe.createListaDiArticoli("Lista Spesa"));
    }

    /**
     * Test per la rimozione di una lista.
     * @throws GestioneListeException In caso di errore inatteso.
     */
    @Test
    void testRemoveListaDiArticoli() throws GestioneListeException {
        GestioneListe.createListaDiArticoli("Da Rimuovere");
        GestioneListe.removeListaDiArticoli("Da Rimuovere");
        assertEquals(0, GestioneListe.getListediarticoli().size());
    }
    
    /**
     * Test che verifica l'errore se si rimuove una lista inesistente.
     */
    @Test
    void testRemoveListaNonEsistente() {
        assertThrows(GestioneListeException.class, () -> GestioneListe.removeListaDiArticoli("Fantasma"));
    }

    /**
     * Test per la creazione di una categoria.
     * @throws GestioneListeException In caso di errore inatteso.
     */
    @Test
    void testCreateCategoria() throws GestioneListeException {
        GestioneListe.createCategoria("Elettronica");
        assertEquals(1, GestioneListe.getCategorie().size());
        assertTrue(GestioneListe.getCategorie().contains("Elettronica"));
    }

    /**
     * Test per la creazione di un articolo nel catalogo.
     * @throws ArticoloException In caso di errore dell'articolo.
     * @throws GestioneListeException In caso di errore del gestore.
     */
    @Test
    void testCreateArticoloCatalogo() throws ArticoloException, GestioneListeException {
        GestioneListe.createArticolo("Computer");
        assertEquals(1, GestioneListe.getArticoli().size());
        assertEquals("Computer", GestioneListe.getArticoli().get(0).getNome());
    }
    
    /**
     * Test che verifica l'impossibilità di creare articoli duplicati nel catalogo.
     * @throws ArticoloException In caso di errore dell'articolo.
     * @throws GestioneListeException In caso di errore del gestore (primo create).
     */
    @Test
    void testCreateArticoloCatalogoDuplicato() throws ArticoloException, GestioneListeException {
        GestioneListe.createArticolo("Mouse");
        assertThrows(GestioneListeException.class, () -> GestioneListe.createArticolo("Mouse"));
    }
}