package modello;

import java.util.ArrayList;

import modello.exception.ArticoloException;

public class GestioneListe {
	private static ArrayList<ListaDiArticoli> listediarticoli;
	private static ArrayList<Articolo> articoli;
	private static ArrayList<String> categorie;
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
	
	public static void createListaDiArticoli(String Nome)
	{
		ListaDiArticoli temp= new ListaDiArticoli(Nome);
		listediarticoli.add(temp);
	}
	
	public static void removeListaDiArticoli(String Nome)
	{
		int i=0;
		for(ListaDiArticoli lsa : listediarticoli)
		{
			if(lsa.getNome()==Nome)
			{
				listediarticoli.remove(i);
			}else
			{
				i++;
			}
		}
		
	}
	
	public static void createCategoria(String Nome)
	{
		categorie.add(Nome);
	}
	
	public static void removeCategoria(String Nome)
	{
		int i=0;
		for(String ctg : categorie)
		{
			if(ctg.equals(Nome))
			{
				categorie.remove(i);
			}else
			{
				i++;
			}
		}
		
	}
	
	public static void createArticolo(String Nome) throws ArticoloException
	{
		Articolo art = new Articolo(Nome);
		articoli.add(art);
	}
	
	public static void removeArticolo(String Nome)
	{
		int i=0;
		for(Articolo art : articoli)
		{
			if(art.getNome()==Nome)
			{
				articoli.remove(i);
			}else
			{
				i++;
			}
		}
		
	}
	
}
