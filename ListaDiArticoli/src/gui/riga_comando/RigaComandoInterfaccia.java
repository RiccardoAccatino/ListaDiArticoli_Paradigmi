package gui.riga_comando;
import jbook.util.Input;
import modello.GestioneListe;
public class RigaComandoInterfaccia {

	private GestioneListe gestor;
	private Input input;
	public RigaComandoInterfaccia()
	{
		gestor = new GestioneListe();
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
			AggiuntaProdotto();
		else if(sc==4)
			AggiuntaCategoria();
		else if(sc==5)
			System.exit(0);
		else
			Menu();
	}

	private void AggiuntaCategoria() {
		// TODO Auto-generated method stub
		
	}

	private void AggiuntaProdotto() {
		// TODO Auto-generated method stub
		
	}

	private void MenuLista() {
		// TODO Auto-generated method stub
		
	}

	private void CrezioneLista() {
		
		
	}
}
