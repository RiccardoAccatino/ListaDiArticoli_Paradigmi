package modello;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;

/**
 * Classe statica che funge da gestore centrale (Database) per l'applicazione.
 * <p>
 * Mantiene in memoria:
 * <ul>
 * <li>Tutte le liste di articoli create.</li>
 * <li>Il catalogo globale degli articoli disponibili.</li>
 * <li>L'elenco delle categorie disponibili.</li>
 * </ul>
 * Gestisce inoltre la persistenza dei dati (salvataggio e caricamento) su file binario.
 * </p>
 */
public class GestioneListe {
    private static ArrayList<ListaDiArticoli> listediarticoli = new ArrayList<>();
    private static ArrayList<Articolo> articoli = new ArrayList<>();
    private static ArrayList<String> categorie = new ArrayList<>();

    /**
     * Restituisce una copia dell'elenco di tutte le liste di articoli gestite.
     * @return ArrayList di ListaDiArticoli (copia difensiva).
     */
	public static ArrayList<ListaDiArticoli> getListediarticoli() {
        return new ArrayList<>(listediarticoli);
    }

    /**
     * Restituisce una copia del catalogo globale degli articoli.
     * @return ArrayList di Articolo (copia difensiva).
     */
    public static ArrayList<Articolo> getArticoli() {
        return new ArrayList<>(articoli);
    }

    /**
     * Restituisce una copia dell'elenco delle categorie disponibili.
     * @return ArrayList di Stringhe rappresentanti le categorie (copia difensiva).
     */
    public static ArrayList<String> getCategorie() {
        return new ArrayList<>(categorie);
    }

    /**
     * Crea una nuova lista di articoli vuota assicurandosi che il nome sia univoco.
     * @param nome Il nome della nuova lista.
     * @throws GestioneListeException Se il nome è vuoto o esiste già una lista con lo stesso nome.
     */
    public static void createListaDiArticoli(String nome) throws GestioneListeException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new GestioneListeException("Il nome della lista non può essere vuoto.");
        }
        for (ListaDiArticoli lista : listediarticoli) {
            if (lista.getNome().equals(nome)) {
                throw new GestioneListeException("Esiste già una lista con il nome: " + nome);
            }
        }
        listediarticoli.add(new ListaDiArticoli(nome));
    }

    /**
     * Rimuove una lista di articoli dal sistema.
     * @param nome Il nome della lista da rimuovere.
     * @throws GestioneListeException Se la lista non viene trovata.
     */
    public static void removeListaDiArticoli(String nome) throws GestioneListeException {
        boolean rimosso = listediarticoli.removeIf(lista -> lista.getNome().equals(nome));
        if (!rimosso) {
            throw new GestioneListeException("Impossibile rimuovere: Lista non trovata (" + nome + ")");
        }
    }

    /**
     * Crea una nuova categoria nel sistema.
     * @param nome Il nome della categoria.
     * @throws GestioneListeException Se il nome è vuoto o la categoria esiste già.
     */
    public static void createCategoria(String nome) throws GestioneListeException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new GestioneListeException("Il nome della categoria non può essere vuoto.");
        }
        for (String ctg : categorie) {
            if (ctg.equalsIgnoreCase(nome)) {
                throw new GestioneListeException("Esiste già una categoria con il nome: " + nome);
            }
        }
        categorie.add(nome);
    }

    /**
     * Rimuove una categoria dal sistema.
     * @param nome Il nome della categoria da rimuovere.
     * @throws GestioneListeException Se la categoria non viene trovata.
     */
    public static void removeCategoria(String nome) throws GestioneListeException {
        if (!categorie.remove(nome)) {
            throw new GestioneListeException("Impossibile rimuovere: Categoria non trovata (" + nome + ")");
        }
    }

    /**
     * Crea un nuovo articolo nel catalogo globale.
     * @param nome Il nome del nuovo articolo.
     * @throws ArticoloException Se i dati dell'articolo non sono validi.
     * @throws GestioneListeException Se esiste già un articolo con lo stesso nome.
     */
    public static void createArticolo(String nome) throws ArticoloException, GestioneListeException {
        for(Articolo a : articoli) {
            if(a.getNome().equals(nome)) {
                throw new GestioneListeException("Esiste già un articolo nel catalogo con questo nome: " + nome);
            }
        }
        articoli.add(new Articolo(nome));
    }

    /**
     * Rimuove un articolo dal catalogo globale.
     * @param nome Il nome dell'articolo da rimuovere.
     * @throws GestioneListeException Se l'articolo non viene trovata.
     */
    public static void removeArticolo(String nome) throws GestioneListeException {
        boolean rimosso = articoli.removeIf(art -> art.getNome().equals(nome));
        if (!rimosso) {
            throw new GestioneListeException("Impossibile rimuovere: Articolo non trovato nel catalogo (" + nome + ")");
        }
    }
    
    /**
     * Salva l'intero stato dell'applicazione su file.
     */
    public static void salvaDati(String percorsoFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(percorsoFile))) {
            out.writeObject(listediarticoli);
            out.writeObject(articoli);
            out.writeObject(categorie);
        }
    }

    /**
     * Carica lo stato dell'applicazione da file.
     */
    @SuppressWarnings("unchecked")
    public static void caricaDati(String percorsoFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(percorsoFile))) {
            listediarticoli = (ArrayList<ListaDiArticoli>) in.readObject();
            articoli = (ArrayList<Articolo>) in.readObject();
            categorie = (ArrayList<String>) in.readObject();
        }
    }
}