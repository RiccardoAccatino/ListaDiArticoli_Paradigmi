package modello;

import java.io.Serializable;
import java.util.Objects;
import modello.exception.ArticoloException;

/**
 * Rappresenta un singolo articolo (prodotto) all'interno del sistema.
 * <p>
 * Ogni articolo è caratterizzato da un nome, un prezzo, una categoria e una nota opzionale.
 * La classe implementa {@link Serializable} per permettere il salvataggio su file.
 * </p>
 */
public class Articolo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto Articolo.
     * @return Una stringa contenente i dettagli dell'articolo.
     */
	@Override
	public String toString() {
		return "Articolo [nome=" + nome + ", prezzo=" + prezzo + ", categoria=" + categoria + ", nota=" + nota + "]";
	}

    /**
     * Calcola l'hash code per l'articolo basandosi sui suoi attributi.
     * @return L'hash code calcolato.
     */
	@Override
	public int hashCode() {
		return Objects.hash(categoria, nome, nota, prezzo);
	}

    /**
     * Confronta questo articolo con un altro oggetto per verificarne l'uguaglianza.
     * @param obj L'oggetto da confrontare.
     * @return true se gli oggetti sono uguali, false altrimenti.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Articolo other = (Articolo) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(nome, other.nome)
				&& Objects.equals(nota, other.nota) && prezzo == other.prezzo;
	}

	private String nome = "";
	private int prezzo = 0;
	private String categoria = "Non categorizzato";
	private String nota = "";

	/**
     * Costruttore completo per creare un nuovo articolo.
     * @param nome      Il nome dell'articolo (non può essere vuoto).
     * @param categoria La categoria di appartenenza (es. "Alimentari").
     * @param prezzo    Il prezzo dell'articolo in centesimi/interi (non può essere negativo).
     * @param nota      Una nota aggiuntiva descrittiva.
     * @throws ArticoloException Se il nome è vuoto o il prezzo è negativo.
     */
	public Articolo(String nome, String categoria, int prezzo, String nota) throws ArticoloException {
		setNome(nome);
		setPrezzo(prezzo);
		setCategoria(categoria);
		setNota(nota);
	}

    /**
     * Costruttore con nome, prezzo e categoria.
     * @param nome      Il nome dell'articolo.
     * @param prezzo    Il prezzo dell'articolo.
     * @param categoria La categoria dell'articolo.
     * @throws ArticoloException Se i dati non sono validi.
     */
	public Articolo(String nome, int prezzo, String categoria) throws ArticoloException {
		setNome(nome);
		setPrezzo(prezzo);
		setCategoria(categoria);
	}

    /**
     * Costruttore con nome e prezzo.
     * @param nome   Il nome dell'articolo.
     * @param prezzo Il prezzo dell'articolo.
     * @throws ArticoloException Se i dati non sono validi.
     */
	public Articolo(String nome, int prezzo) throws ArticoloException {
		setNome(nome);
		setPrezzo(prezzo);
	}

    /**
     * Costruttore con solo il nome.
     * @param nome Il nome dell'articolo.
     * @throws ArticoloException Se il nome è vuoto o nullo.
     */
	public Articolo(String nome) throws ArticoloException {
		setNome(nome);
	}

    /**
     * Restituisce il nome dell'articolo.
     * @return Il nome.
     */
	public String getNome() {
		return nome;
	}

    /**
     * Imposta il nome dell'articolo.
     * @param nome Il nuovo nome.
     * @throws ArticoloException Se il nome è nullo o vuoto.
     */
	public void setNome(String nome) throws ArticoloException {
		if (nome == null || nome.trim().isEmpty()) {
			throw new ArticoloException("Il nome dell'articolo non può essere vuoto");
		}
		this.nome = nome;
	}

    /**
     * Restituisce il prezzo dell'articolo.
     * @return Il prezzo.
     */
	public int getPrezzo() {
		return prezzo;
	}
	
	/**
     * Imposta il prezzo dell'articolo.
     * @param prezzo Il nuovo prezzo (deve essere >= 0).
     * @throws ArticoloException Se il prezzo inserito è negativo.
     */
	public void setPrezzo(int prezzo) throws ArticoloException {
		if (prezzo < 0) {
			throw new ArticoloException("Il prezzo non può essere negativo: " + prezzo);
		}
		this.prezzo = prezzo;
	}

    /**
     * Restituisce la nota associata all'articolo.
     * @return La nota.
     */
	public String getNota() {
		return nota;
	}

    /**
     * Imposta una nota descrittiva per l'articolo.
     * @param nota La nuova nota.
     */
	public void setNota(String nota) {
		this.nota = nota;
	}

    /**
     * Restituisce la categoria dell'articolo.
     * @return La categoria.
     */
	public String getCategoria() {
		return categoria;
	}

    /**
     * Imposta la categoria dell'articolo.
     * Se la categoria è nulla, viene impostata a "Non categorizzato".
     * @param categoria La nuova categoria.
     */
	public void setCategoria(String categoria) {
		if (categoria == null) {
			this.categoria = "Non categorizzato";
		} else {
			this.categoria = categoria;
		}
	}
}