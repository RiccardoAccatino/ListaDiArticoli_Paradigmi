package modello;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import modello.exception.ArticoloException;
import modello.exception.ListaDiArticoliException;

/**
 * Rappresenta una lista della spesa o un elenco di articoli specifico (es. "Lista Vacanze").
 * <p>
 * Questa classe gestisce due collezioni interne:
 * <ul>
 * <li><b>Lista Validi:</b> gli articoli attualmente presenti nella lista.</li>
 * <li><b>Lista Cancellati:</b> un "cestino" temporaneo per gli articoli rimossi, permettendo il ripristino.</li>
 * </ul>
 * Implementa {@link Iterable} per consentire l'iterazione sequenziale su tutti gli articoli (validi e cancellati).
 * </p>
 */
public class ListaDiArticoli implements Serializable, Iterable<Articolo> {
    private static final long serialVersionUID = 1L; 

    private String nome = "";
    private ArrayList<Articolo> listaValidi;
    private ArrayList<Articolo> listaCancellati;

    /**
     * Costruttore per creare una nuova lista di articoli.
     * @param nome Il nome della lista.
     */
    public ListaDiArticoli(String nome) {
        setNome(nome);
        this.listaValidi = new ArrayList<>();
        this.listaCancellati = new ArrayList<>();
    }

    /**
     * Restituisce un iteratore che scorre sia gli articoli validi che quelli cancellati.
     * <p>Questo permette di usare la lista all'interno di un ciclo for-each.</p>
     * @return Un iteratore su tutti gli articoli della lista.
     */
    @Override
    public Iterator<Articolo> iterator() {
        ArrayList<Articolo> listaCompleta = new ArrayList<>(listaValidi);
        listaCompleta.addAll(listaCancellati);
        return listaCompleta.iterator();
    }

    /**
     * Calcola il prezzo totale degli articoli validi presenti nella lista.
     * @return La somma dei prezzi degli articoli validi.
     */
    public int calcolaPrezzoTotale() {
        int totale = 0;
        for (Articolo a : listaValidi) {
            totale += a.getPrezzo();
        }
        return totale;
    }

    /**
     * Cerca un articolo sia nella lista attiva che nel cestino tramite prefisso.
     * @param prefisso La parte iniziale del nome dell'articolo da cercare.
     * @return L'oggetto {@link Articolo} se trovato, altrimenti null.
     */
    public Articolo searchArticolo(String prefisso) {
        for (Articolo sc : listaValidi) {
            if (sc.getNome().startsWith(prefisso)) {
                return sc;
            }
        }
        for (Articolo sc : listaCancellati) {
            if (sc.getNome().startsWith(prefisso)) {
                return sc;
            }
        }
        return null;
    }


    public void addArticolo(String nome, String categoria, int prezzo, String nota) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome, categoria, prezzo, nota);
        listaValidi.add(nuovoArticolo);
    }

    public void addArticolo(String nome, int prezzo, String categoria) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome, prezzo, categoria);
        listaValidi.add(nuovoArticolo);
    }

    public void addArticolo(String nome, int prezzo) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome, prezzo);
        listaValidi.add(nuovoArticolo);
    }

    public void addArticolo(String nome) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome);
        listaValidi.add(nuovoArticolo);
    }

    public void removeArticolo(String nome) throws ListaDiArticoliException {
        Iterator<Articolo> iter = listaValidi.iterator();
        while (iter.hasNext()) {
            Articolo sc = iter.next();
            if (sc.getNome().equals(nome)) {
                listaCancellati.add(sc);
                iter.remove(); 
                return;
            }
        }
        throw new ListaDiArticoliException("Impossibile rimuovere: Articolo non trovato nella lista (" + nome + ")");
    }

    public void ripristina(String nome) throws ListaDiArticoliException {
        Iterator<Articolo> iter = listaCancellati.iterator();
        while (iter.hasNext()) {
            Articolo sc = iter.next();
            if (sc.getNome().equals(nome)) {
                listaValidi.add(sc);
                iter.remove();
                return;
            }
        }
        throw new ListaDiArticoliException("Impossibile ripristinare: Articolo non presente nel cestino (" + nome + ")");
    }

    public void svuotaCestino() {
        listaCancellati.clear();
    }
    
    public void modificaArticolo(String nomeArticolo, int nuovoPrezzo, String nuovaCategoria, String nuovaNota) 
            throws ListaDiArticoliException, ArticoloException {
        
        Articolo articolo = searchArticolo(nomeArticolo);
        
        if (articolo == null || listaCancellati.contains(articolo)) {
            throw new ListaDiArticoliException("Impossibile modificare: Articolo non trovato o nel cestino (" + nomeArticolo + ")");
        }

        if (nuovoPrezzo >= 0) articolo.setPrezzo(nuovoPrezzo);
        if (nuovaCategoria != null && !nuovaCategoria.isBlank()) articolo.setCategoria(nuovaCategoria);
        if (nuovaNota != null) articolo.setNota(nuovaNota);
    }
    
    public ArrayList<Articolo> cercaPerCategoria(String categoria) {
        ArrayList<Articolo> risultati = new ArrayList<>();
        for (Articolo a : listaValidi) {
            if (a.getCategoria().equalsIgnoreCase(categoria)) risultati.add(a);
        }
        return risultati;
    }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getNumeroArticoliValidi() { return listaValidi.size(); }
    public int getNumeroArticoliCancellati() { return listaCancellati.size(); }
    public ArrayList<Articolo> getArticoliValidi() { return listaValidi; }
    public ArrayList<Articolo> getArticoliCancellati() { return listaCancellati; }

    @Override
	public String toString() {
		return "ListaDiArticoli [nome=" + nome + ", listaValidi=" + listaValidi + ", listaCancellati=" + listaCancellati + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(listaCancellati, listaValidi, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ListaDiArticoli other = (ListaDiArticoli) obj;
		return Objects.equals(listaCancellati, other.listaCancellati) && Objects.equals(listaValidi, other.listaValidi)
				&& Objects.equals(nome, other.nome);
	}
}