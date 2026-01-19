package gui.grafica.vista;

import modello.GestioneListe;
import java.io.IOException;

public class MainGui {

    private static final String FILE_DATI = "dati_save.bin";

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