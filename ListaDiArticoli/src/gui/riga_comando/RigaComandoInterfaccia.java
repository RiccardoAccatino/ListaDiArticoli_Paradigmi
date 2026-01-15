package gui.riga_comando;
import jbook.util.Input;
import modello.GestioneListe;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;
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
		nome= input.readString("|Inserisci il nome della categoriaa che desideri creare=");
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
		nome= input.readString("|Inserisci il nome del prodotto che desideri creare=");
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
			AggiungiProdottoAllaLista();
		else if(sc==2)
			CercaProdottoNellaLista();
		else if(sc==3)
			SpostaProdottoDallaLista();
		else if(sc==4)
			RipristinaProdotto();
		else if(sc==5)
			SvuotaCesto();
		else if(sc==6)
			Menu();
		else
			MenuLista();
	}

	private void SvuotaCesto() {
		// TODO Auto-generated method stub
		
	}

	private void RipristinaProdotto() {
		// TODO Auto-generated method stub
		
	}

	private void SpostaProdottoDallaLista() {
		// TODO Auto-generated method stub
		
	}

	private void CercaProdottoNellaLista() {
		// TODO Auto-generated method stub
		
	}

	private void AggiungiProdottoAllaLista() {
		String prd="";
		prd=input.readString("Inserisci il nome del prodotto che vuoi aggiungere lla lista=");
		
	}

	private void CrezioneLista() {
		
		String nome="";
		nome= input.readString("|Inserisci il nome dell lista che desideri creare=");
		try{
			GestioneListe.createListaDiArticoli(nome);
		}catch(GestioneListeException e)
		{
			System.out.println("Errore durante la crezione della lista!");
		}
		
		Menu();
	}
}
