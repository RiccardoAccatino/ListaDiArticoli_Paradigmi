package gui.grafica.vista;

import modello.GestioneListe;
import java.io.IOException;

/**
 * Classe di avvio per l'interfaccia grafica (GUI).
 */
public class MainGui {

    private static final String FILE_DATI = "dati_save.bin";

    /**
     * Metodo main che avvia l'applicazione in modalità grafica.
     * Carica i dati salvati e lancia il Menu Principale.
     * @param args Argomenti da riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
  
        try {
            GestioneListe.caricaDati(FILE_DATI);
            System.out.println("GUI: Dati caricati correttamente.");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("GUI: Primo avvio o file non trovato. Si parte con liste vuote.");
        }
  
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MenuPrincipale();
        });
    }
}