package gui.grafica.vista;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.exception.GestioneListeException;
public class MainGui {

    public static void main(String[] args) {
  
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MenuPrincipale();
        });
    }
}
