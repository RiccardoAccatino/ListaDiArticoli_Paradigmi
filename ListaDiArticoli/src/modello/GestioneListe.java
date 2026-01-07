package modello;

import java.util.ArrayList;
import java.util.Iterator;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;

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
}