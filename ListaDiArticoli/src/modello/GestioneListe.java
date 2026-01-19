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


	public static ArrayList<ListaDiArticoli> getListediarticoli() {
        return listediarticoli;
    }

    public static void setListediarticoli(ArrayList<ListaDiArticoli> listediarticoli) {
        GestioneListe.listediarticoli = listediarticoli;
    }

    public static ArrayList<Articolo> getArticoli() {
        return articoli;
    }

    public static void setArticoli(ArrayList<Articolo> articoli) {
        GestioneListe.articoli = articoli;
    }

    public static ArrayList<String> getCategorie() {
        return categorie;
    }

    public static void setCategorie(ArrayList<String> categorie) {
        GestioneListe.categorie = categorie;
    }

    /**
     * Crea una nuova lista di articoli vuota assicurandosi che il nome sia univoco.
     * * @param nome Il nome della nuova lista.
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
        ListaDiArticoli temp = new ListaDiArticoli(nome);
        listediarticoli.add(temp);
    }

    public static void removeListaDiArticoli(String nome) throws GestioneListeException {
        boolean trovato = false;
        Iterator<ListaDiArticoli> iterator = listediarticoli.iterator();
        
        while (iterator.hasNext()) {
            ListaDiArticoli lista = iterator.next();
            if (lista.getNome().equals(nome)) {
                iterator.remove();
                trovato = true;
                break;
            }
        }

        if (trovato==false) {
            throw new GestioneListeException("Impossibile rimuovere: Lista non trovata (" + nome + ")");
        }
    }


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

    public static void removeCategoria(String nome) throws GestioneListeException {
        boolean trovato = false;
        Iterator<String> iterator = categorie.iterator();
        
        while (iterator.hasNext()) {
            String ctg = iterator.next();
            if (ctg.equals(nome)) {
                iterator.remove();
                trovato = true;
                break;
            }
        }

        if (trovato==false) {
            throw new GestioneListeException("Impossibile rimuovere: Categoria non trovata (" + nome + ")");
        }
    }


    public static void createArticolo(String nome) throws ArticoloException, GestioneListeException {
        for(Articolo a : articoli) {
            if(a.getNome().equals(nome)) {
                throw new GestioneListeException("Esiste già un articolo nel catalogo con questo nome: " + nome);
            }
        }
        
        Articolo art = new Articolo(nome);
        articoli.add(art);
    }

    public static void removeArticolo(String nome) throws GestioneListeException {
        boolean trovato = false;
        Iterator<Articolo> iterator = articoli.iterator();

        while (iterator.hasNext()) {
            Articolo art = iterator.next();
            if (art.getNome().equals(nome)) {
                iterator.remove();
                trovato = true;
                break;
            }
        }

        if (trovato==false) {
            throw new GestioneListeException("Impossibile rimuovere: Articolo non trovato nel catalogo (" + nome + ")");
        }
    }
    /**
     * Salva l'intero stato dell'applicazione (liste, catalogo, categorie) su un file.
     * * @param percorsoFile Il percorso del file di destinazione (es. "dati.bin").
     * @throws IOException Se si verifica un errore durante la scrittura del file.
     */
    public static void salvaDati(String percorsoFile) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(percorsoFile))) {
            out.writeObject(listediarticoli);
            out.writeObject(articoli);
            out.writeObject(categorie);
        }
    }

    /**
     * Carica lo stato dell'applicazione da un file precedentemente salvato.
     * * @param percorsoFile Il percorso del file da leggere.
     * @throws IOException Se c'è un errore di lettura.
     * @throws ClassNotFoundException Se la classe degli oggetti serializzati non viene trovata.
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