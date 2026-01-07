package modello;

import java.util.ArrayList;
import java.util.Iterator;
import modello.exception.ArticoloException;
import modello.exception.ListaDiArticoliException;

public class ListaDiArticoli {
    private String nome = "";
    private ArrayList<Articolo> listaValidi;
    private ArrayList<Articolo> listaCancellati;

    public ListaDiArticoli(String nome) {
        setNome(nome);
        this.listaValidi = new ArrayList<>();
        this.listaCancellati = new ArrayList<>();
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

    public void svuotaCestino() {
        listaCancellati.clear();
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getNumeroArticoliValidi() {
        return listaValidi.size();
    }
    
    public int getNumeroArticoliCancellati() {
        return listaCancellati.size();
    }
}