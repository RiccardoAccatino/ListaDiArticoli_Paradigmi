package modello;

import java.util.ArrayList;

public class ListaDiArticoli {
	private String nome="";
	private ArrayList<Articolo> lista;
	public ListaDiArticoli(String nome)
	{
		setNome(nome);
		ArrayList<Articolo> lista = new ArrayList();
	}
	public void addArticolo()
	{
		
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
