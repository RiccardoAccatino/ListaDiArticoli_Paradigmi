package main;

import gui.riga_comando.RigaComandoInterfaccia;

/**
 * Classe principale (Entry point) per l'avvio dell'applicazione in modalità Console.
 */
public class Main {

    /**
     * Metodo main che avvia l'interfaccia a riga di comando.
     * @param args Argomenti da riga di comando (non utilizzati).
     */
	public static void main(String[] args) {
		new RigaComandoInterfaccia();
		System.out.println("Programma terminato.");
	}

}