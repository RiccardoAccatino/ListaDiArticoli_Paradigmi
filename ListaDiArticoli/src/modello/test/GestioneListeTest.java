package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;

/**
 * Classe di test JUnit per la classe {@link GestioneListe}.
 * Verifica le operazioni globali su liste, categorie e catalogo.
 * Adattata per rispettare il nuovo incapsulamento (assenza di setter statici).
 */
class GestioneListeTest {

    /**
     * Prepara l'ambiente di test svuotando le liste.
     * Poiché i setter statici sono stati rimossi per sicurezza, 
     * usiamo i metodi di rimozione per pulire lo stato.
     */
    @BeforeEach
    void setUp() throws GestioneListeException {
        
        for (ListaDiArticoli l : GestioneListe.getListediarticoli()) {
            GestioneListe.removeListaDiArticoli(l.getNome());
        }
        
        
        for (String c : GestioneListe.getCategorie()) {
            GestioneListe.removeCategoria(c);
        }
        
       
        for (Articolo a : GestioneListe.getArticoli()) {
            GestioneListe.removeArticolo(a.getNome());
        }
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
    
    /**
     * Test che verifica l'incapsulamento: modificare la lista ottenuta col getter
     * non deve modificare la lista interna di GestioneListe.
     */
    @Test
    void testIncapsulamento() {
      
        var categorie = GestioneListe.getCategorie();
        categorie.add("Categoria Fake");
        
        
        assertEquals(0, GestioneListe.getCategorie().size());
    }
}