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
	@Override
	public String toString() {
		return "Articolo [nome=" + nome + ", prezzo=" + prezzo + ", categoria=" + categoria + ", nota=" + nota + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, nome, nota, prezzo);
	}

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
     * * @param nome      Il nome dell'articolo (non può essere vuoto).
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

	public Articolo(String nome, int prezzo, String categoria) throws ArticoloException {
		setNome(nome);
		setPrezzo(prezzo);
		setCategoria(categoria);
	}

	public Articolo(String nome, int prezzo) throws ArticoloException {
		setNome(nome);
		setPrezzo(prezzo);
	}

	public Articolo(String nome) throws ArticoloException {
		setNome(nome);
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) throws ArticoloException {
		if (nome == null || nome.trim().isEmpty()) {
			throw new ArticoloException("Il nome dell'articolo non può essere vuoto");
		}
		this.nome = nome;
	}

	public int getPrezzo() {
		return prezzo;
	}
	
	/**
     * Imposta il prezzo dell'articolo.
     * * @param prezzo Il nuovo prezzo (deve essere >= 0).
     * @throws ArticoloException Se il prezzo inserito è negativo.
     */

	public void setPrezzo(int prezzo) throws ArticoloException {
		if (prezzo < 0) {
			throw new ArticoloException("Il prezzo non può essere negativo: " + prezzo);
		}
		this.prezzo = prezzo;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		if (categoria == null) {
			this.categoria = "Non categorizzato";
		} else {
			this.categoria = categoria;
		}
	}
}