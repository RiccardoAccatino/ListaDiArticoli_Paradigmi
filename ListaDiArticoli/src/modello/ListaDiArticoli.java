package modello;

import java.util.ArrayList;

import modello.exception.ArticoloException;

public class ListaDiArticoli {
	private String nome="";
	private ArrayList<Articolo> listaValidi;
	private ArrayList<Articolo> listaCancellati;
	public ListaDiArticoli(String nome)
	{
		setNome(nome);
		ArrayList<Articolo> listaValidi = new ArrayList();
		ArrayList<Articolo> listaCancellati = new ArrayList();
	}
	public void addArticolo(String nome,String categoria, int prezzo,String nota) throws ArticoloException 
	{
	    Articolo nuovoArticolo = new Articolo(nome, categoria,prezzo, nota);
	    listaValidi.add(nuovoArticolo);
	}
	
	public void addArticolo(String nome, int prezzo,String categoria) throws ArticoloException 
	{
	    Articolo nuovoArticolo = new Articolo(nome, prezzo,categoria);
	    listaValidi.add(nuovoArticolo);
	}
	public void addArticolo(String nome, int prezzo) throws ArticoloException 
	{
	    Articolo nuovoArticolo = new Articolo(nome, prezzo);
	    listaValidi.add(nuovoArticolo);
	}
	public void addArticolo(String nome) throws ArticoloException 
	{
	    Articolo nuovoArticolo = new Articolo(nome);
	    listaValidi.add(nuovoArticolo);
	}
	public void removeArticolo(String nome) throws ArticoloException 
	{
		int i=0;
		for(Articolo sc: listaValidi)
		{
			if(sc.getNome()==nome)
			{
				listaCancellati.add(sc);
				listaValidi.remove(i);
			}else
			{
				i++;
			}
		}
	}
	public void ripristina(String nome) throws ArticoloException
	{
		Articolo target=searchArticolo(nome);
		listaValidi.add(target);
		int i=0;
		for(Articolo sc: listaCancellati)
		{
			if(sc.getNome()==nome)
			{
				
				listaCancellati.remove(i);
			}else
			{
				i++;
			}
		}
		
	}
	public void svuotaCestino() throws ArticoloException
	{
		listaCancellati.clear();
	}
	public Articolo searchArticolo(String prefisso) throws ArticoloException
	{
		Articolo target=null;
		for(Articolo sc: listaValidi)
		{
			if(sc.getNome().contains(prefisso)==true)
				target=sc;
		}
		for(Articolo sc: listaCancellati)
		{
			if(sc.getNome().contains(prefisso)==true)
				target=sc;
		}
		return target;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
