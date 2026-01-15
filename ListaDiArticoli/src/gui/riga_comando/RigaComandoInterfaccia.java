package gui.riga_comando;
import jbook.util.Input;
import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;
import modello.exception.ListaDiArticoliException;
public class RigaComandoInterfaccia {
	private Input input;
	public RigaComandoInterfaccia()
	{
		input = new Input();
		Menu();
		
	}

	private void Menu() {
		int sc=0;
		System.out.println("-----------LISTA DI ARTICOLI-------------");
		System.out.println("|1-Crea una nuova lista");
		System.out.println("|2-Seleziona una lista");
		System.out.println("|3-Aggiungi un prodotto");
		System.out.println("|4-Aggiungi una categoria");
		System.out.println("|5-Esci");
		System.out.println("-----------------------------------------");
		sc=input.readInt("|Inserisci=");
		System.out.println("-----------------------------------------");
		if(sc==1)
			CrezioneLista();
		else if(sc==2)
			MenuLista();
		else if(sc==3)
			try {
				AggiuntaProdotto();
			} catch (ArticoloException e) {
				
			}
		else if(sc==4)
			AggiuntaCategoria();
		else if(sc==5)
			System.exit(0);
		else
			Menu();
	}

	private void AggiuntaCategoria() {
		String nome="";
		nome= input.readString("Inserisci il nome della categoriaa che desideri creare=");
		try{
			GestioneListe.createCategoria(nome);
		}catch(GestioneListeException e)
		{
			System.out.println("Errore durante la crezione della categoria!");
		}
		
		Menu();
		
	}

	private void AggiuntaProdotto() throws ArticoloException {
		String nome="";
		nome= input.readString("Inserisci il nome del prodotto che desideri creare=");
		try{
			GestioneListe.createArticolo(nome);
		}catch(GestioneListeException e)
		{
			System.out.println("Errore durante la crezione dell'articolo!");
		}
		
		Menu();
		
	}

	private void MenuLista() {
		int sc=0;
		String nomeLis="";
		nomeLis= input.readString("Inserisci il nome della lista che desideri selezionare=");
		System.out.println("-----------"+nomeLis+"-------------");
		System.out.println("|1-Aggiungi Prodotto");
		System.out.println("|2-Cerca Prodotto");
		System.out.println("|3-Sposta Prodotto nel cestino");
		System.out.println("|4-Ripristina Prodotto");
		System.out.println("|5-Svuota Cestino");
		System.out.println("|6-Torna Indietro");
		System.out.println("-----------------------------------------");
		sc=input.readInt("|Inserisci=");
		System.out.println("-----------------------------------------");
		if(sc==1)
			AggiungiProdottoAllaLista(nomeLis);
		else if(sc==2)
			CercaProdottoNellaLista(nomeLis);
		else if(sc==3)
			SpostaProdottoDallaLista(nomeLis);
		else if(sc==4)
			RipristinaProdotto(nomeLis);
		else if(sc==5)
			SvuotaCesto(nomeLis);
		else if(sc==6)
			Menu();
		else
			MenuLista();
	}

	private void SvuotaCesto(String nomeLista) {
		ListaDiArticoli lista = trovaLista(nomeLista);
		if (lista != null) {
			String conferma = input.readString("Sei sicuro di voler svuotare il cestino? (s/n): ");
			if (conferma.equalsIgnoreCase("s")) {
				lista.svuotaCestino();
				System.out.println("Cestino svuotato.");
			} else {
				System.out.println("Operazione annullata.");
			}
		}
		MenuLista();
	}

	private void RipristinaProdotto(String nomeLista) {
		ListaDiArticoli lista = trovaLista(nomeLista);
		if (lista != null) {
			String nomeArticolo = input.readString("Inserisci il nome dell'articolo da ripristinare: ");
			try {
				lista.ripristina(nomeArticolo);
				System.out.println("Articolo '" + nomeArticolo + "' ripristinato con successo.");
			} catch (ListaDiArticoliException e) {
				System.out.println("Errore: " + e.getMessage());
			}
		}
		MenuLista();
	}

	private void SpostaProdottoDallaLista(String nomeLista) {
		ListaDiArticoli lista = trovaLista(nomeLista);
		if (lista != null) {
			String nomeArticolo = input.readString("Inserisci il nome dell'articolo da cestinare: ");
			try {
				lista.removeArticolo(nomeArticolo);
				System.out.println("Articolo '" + nomeArticolo + "' spostato nel cestino.");
			} catch (ListaDiArticoliException e) {
				System.out.println("Errore: " + e.getMessage());
			}
		}
		MenuLista();
	}

	private void CercaProdottoNellaLista(String nomeLista) {
		ListaDiArticoli lista = trovaLista(nomeLista);
		if (lista != null) {
			String ricerca = input.readString("Inserisci il nome (o parte del nome) da cercare: ");
			Articolo trovato = lista.searchArticolo(ricerca);
			
			if (trovato != null) {
				System.out.println("Articolo trovato: " + trovato.toString());
			} else {
				System.out.println("Nessun articolo trovato con: " + ricerca);
			}
		}
		MenuLista();
	}
    private ListaDiArticoli trovaLista(String nomeLista) {
        for (ListaDiArticoli lista : GestioneListe.getListediarticoli()) {
            if (lista.getNome().equals(nomeLista)) {
                return lista;
            }
        }
        System.out.println("Attenzione: Lista '" + nomeLista + "' non trovata.");
        return null;
    }
    private void AggiungiProdottoAllaLista(String nomeLista) {
		ListaDiArticoli lista = trovaLista(nomeLista);
		if (lista != null) {
			String prd = input.readString("Inserisci il nome del prodotto che vuoi aggiungere alla lista = ");
			
			try {
				lista.addArticolo(prd);
				System.out.println("Prodotto '" + prd + "' aggiunto con successo alla lista '" + nomeLista + "'.");
			} catch (ArticoloException e) {
				System.out.println("Errore: " + e.getMessage());
			}
		}
		MenuLista();
	}

	private void CrezioneLista() {
		
		String nome="";
		nome= input.readString("Inserisci il nome dell lista che desideri creare=");
		try{
			GestioneListe.createListaDiArticoli(nome);
		}catch(GestioneListeException e)
		{
			System.out.println("Errore durante la crezione della lista!");
		}
		
		Menu();
	}
}
