package modello;

import java.util.Objects;

import modello.exception.ArticoloException;

public class Articolo {
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