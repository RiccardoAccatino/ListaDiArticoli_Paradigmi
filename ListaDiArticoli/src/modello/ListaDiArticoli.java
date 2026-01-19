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
 * </p>
 */
public class ListaDiArticoli implements Serializable {
    private static final long serialVersionUID = 1L; 

    /**
     * Restituisce una stringa descrittiva della lista.
     * @return La stringa con i dettagli della lista.
     */
    @Override
	public String toString() {
		return "ListaDiArticoli [nome=" + nome + ", listaValidi=" + listaValidi + ", listaCancellati=" + listaCancellati
				+ "]";
	}

    /**
     * Calcola l'hash code della lista.
     * @return L'hash code.
     */
	@Override
	public int hashCode() {
		return Objects.hash(listaCancellati, listaValidi, nome);
	}

    /**
     * Verifica l'uguaglianza con un altro oggetto.
     * @param obj L'oggetto da confrontare.
     * @return true se uguali, false altrimenti.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListaDiArticoli other = (ListaDiArticoli) obj;
		return Objects.equals(listaCancellati, other.listaCancellati) && Objects.equals(listaValidi, other.listaValidi)
				&& Objects.equals(nome, other.nome);
	}

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
     * Aggiunge un nuovo articolo completo alla lista.
     * @param nome Nome dell'articolo.
     * @param categoria Categoria dell'articolo.
     * @param prezzo Prezzo dell'articolo.
     * @param nota Nota aggiuntiva.
     * @throws ArticoloException Se i dati dell'articolo non sono validi.
     */
    public void addArticolo(String nome, String categoria, int prezzo, String nota) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome, categoria, prezzo, nota);
        listaValidi.add(nuovoArticolo);
    }

    /**
     * Aggiunge un articolo con nome, prezzo e categoria.
     * @param nome Nome dell'articolo.
     * @param prezzo Prezzo dell'articolo.
     * @param categoria Categoria dell'articolo.
     * @throws ArticoloException Se i dati dell'articolo non sono validi.
     */
    public void addArticolo(String nome, int prezzo, String categoria) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome, prezzo, categoria);
        listaValidi.add(nuovoArticolo);
    }

    /**
     * Aggiunge un articolo con nome e prezzo.
     * @param nome Nome dell'articolo.
     * @param prezzo Prezzo dell'articolo.
     * @throws ArticoloException Se i dati dell'articolo non sono validi.
     */
    public void addArticolo(String nome, int prezzo) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome, prezzo);
        listaValidi.add(nuovoArticolo);
    }

    /**
     * Aggiunge un articolo con solo il nome.
     * @param nome Nome dell'articolo.
     * @throws ArticoloException Se il nome non è valido.
     */
    public void addArticolo(String nome) throws ArticoloException {
        Articolo nuovoArticolo = new Articolo(nome);
        listaValidi.add(nuovoArticolo);
    }

    /**
     * Rimuove un articolo dalla lista attiva e lo sposta nel cestino (lista cancellati).
     * @param nome Il nome dell'articolo da rimuovere.
     * @throws ListaDiArticoliException Se l'articolo non viene trovato nella lista attiva.
     */
    public void removeArticolo(String nome) throws ListaDiArticoliException {
        boolean trovato = false;
        
       
        Iterator<Articolo> iter = listaValidi.iterator();
        while (iter.hasNext()) {
            Articolo sc = iter.next();
           
            if (sc.getNome().equals(nome)) {
                listaCancellati.add(sc);
                iter.remove(); 
                trovato = true;
                break; 
            }
        }

        if (trovato==false) {
            throw new ListaDiArticoliException("Impossibile rimuovere: Articolo non trovato nella lista (" + nome + ")");
        }
    }

    /**
     * Ripristina un articolo precedentemente spostato nel cestino, riportandolo nella lista attiva.
     * @param nome Il nome dell'articolo da recuperare.
     * @throws ListaDiArticoliException Se l'articolo non è presente nel cestino.
     */
    public void ripristina(String nome) throws ListaDiArticoliException {
        boolean trovato = false;
        
        Iterator<Articolo> iter = listaCancellati.iterator();
        while (iter.hasNext()) {
            Articolo sc = iter.next();
            if (sc.getNome().equals(nome)) {
                listaValidi.add(sc);
                iter.remove();
                trovato = true;
                break;
            }
        }

        if (trovato==false) {
            throw new ListaDiArticoliException("Impossibile ripristinare: Articolo non presente nel cestino (" + nome + ")");
        }
    }

    /**
     * Svuota definitivamente il cestino degli articoli cancellati.
     */
    public void svuotaCestino() {
        listaCancellati.clear();
    }
    
    /**
     * Cerca un articolo sia nella lista attiva che nel cestino tramite una stringa parziale (prefisso).
     * @param prefisso La parte iniziale o il nome completo dell'articolo da cercare.
     * @return L'oggetto {@link Articolo} se trovato, altrimenti null.
     */
    public Articolo searchArticolo(String prefisso) {
        Articolo target = null;

        for (Articolo sc : listaValidi) {
            if (sc.getNome().contains(prefisso)) {
                target = sc;
            }
        }
        
        if (target == null) {
            for (Articolo sc : listaCancellati) {
                if (sc.getNome().contains(prefisso)) {
                    target = sc;
                }
            }
        }
        return target;
    }
    
    /**
     * Modifica i dettagli di un articolo esistente nella lista.
     * @param nomeArticolo Il nome dell'articolo da modificare.
     * @param nuovoPrezzo Il nuovo prezzo da impostare (se negativo, viene ignorato).
     * @param nuovaCategoria La nuova categoria da impostare (se null o vuota, viene ignorata).
     * @param nuovaNota La nuova nota da impostare (se null, viene ignorata).
     * @throws ListaDiArticoliException Se l'articolo non viene trovato o è nel cestino.
     * @throws ArticoloException Se i nuovi valori non sono validi.
     */
    public void modificaArticolo(String nomeArticolo, int nuovoPrezzo, String nuovaCategoria, String nuovaNota) 
            throws ListaDiArticoliException, ArticoloException {
        
        Articolo articolo = searchArticolo(nomeArticolo);
        
        if (articolo == null || listaCancellati.contains(articolo)) {
            throw new ListaDiArticoliException("Impossibile modificare: Articolo non trovato o nel cestino (" + nomeArticolo + ")");
        }

        if (nuovoPrezzo >= 0) {
            articolo.setPrezzo(nuovoPrezzo);
        }
        if (nuovaCategoria != null && !nuovaCategoria.isBlank()) {
            articolo.setCategoria(nuovaCategoria);
        }
        if (nuovaNota != null) {
            articolo.setNota(nuovaNota);
        }
    }
    
    /**
     * Cerca tutti gli articoli che appartengono a una determinata categoria.
     * @param categoria La categoria da cercare.
     * @return Una lista di articoli che corrispondono alla categoria.
     */
    public ArrayList<Articolo> cercaPerCategoria(String categoria) {
        ArrayList<Articolo> risultati = new ArrayList<>();
        for (Articolo a : listaValidi) {
            if (a.getCategoria().equalsIgnoreCase(categoria)) {
                risultati.add(a);
            }
        }
        return risultati;
    }
    
    /**
     * Restituisce il nome della lista.
     * @return Il nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome della lista.
     * @param nome Il nuovo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Restituisce il numero di articoli validi presenti nella lista.
     * @return Il conteggio degli articoli validi.
     */
    public int getNumeroArticoliValidi() {
        return listaValidi.size();
    }
    
    /**
     * Restituisce il numero di articoli presenti nel cestino.
     * @return Il conteggio degli articoli cancellati.
     */
    public int getNumeroArticoliCancellati() {
        return listaCancellati.size();
    }
    
    /**
     * Restituisce l'elenco degli articoli validi.
     * @return ArrayList contenente gli articoli validi.
     */
    public ArrayList<Articolo> getArticoliValidi() {
        return listaValidi;
    }

    /**
     * Restituisce l'elenco degli articoli nel cestino.
     * @return ArrayList contenente gli articoli cancellati.
     */
    public ArrayList<Articolo> getArticoliCancellati() {
        return listaCancellati;
    }
}