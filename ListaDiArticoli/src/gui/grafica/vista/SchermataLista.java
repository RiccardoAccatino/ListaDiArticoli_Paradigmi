package gui.grafica.vista;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.grafica.controllo.Controller;
import modello.ListaDiArticoli;

/**
 * Finestra specifica per la gestione di una lista aperta dal Menu Principale.
 * A differenza di FinestraPrincipale, questa non chiude l'intera applicazione alla chiusura.
 */
public class SchermataLista extends JFrame {

    /**
     * Costruisce la schermata di dettaglio della lista.
     * @param lista La lista di articoli da visualizzare e modificare.
     */
    public SchermataLista(ListaDiArticoli lista) {
        setTitle("Dettaglio Lista: " + lista.getNome());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(200, 200, 600, 500);

        JPanel mainPanel = new JPanel(new BorderLayout());

        PannelloContenuto vista = new PannelloContenuto(lista);

        Controller controller = new Controller(lista, vista);


        PannelloComandi comandi = new PannelloComandi(controller);

        mainPanel.add(comandi, BorderLayout.NORTH);
        mainPanel.add(vista, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }
}