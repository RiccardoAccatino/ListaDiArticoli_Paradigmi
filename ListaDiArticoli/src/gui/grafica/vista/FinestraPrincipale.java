package gui.grafica.vista;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.grafica.controllo.Controller;
import modello.ListaDiArticoli;
public class FinestraPrincipale extends JFrame{
	public FinestraPrincipale(ListaDiArticoli model) {
        // Configurazioni base del JFrame
        setTitle("Gestione Lista: " + model.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400); // Dimensioni e posizione
        
        // Creazione del pannello principale (Container)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // 1. Creiamo la vista centrale (Contenuto)
        PannelloContenuto pannelloContenuto = new PannelloContenuto(model);
        
        // 2. Creiamo il controllore (passandogli modello e vista)
        Controller controllore = new Controller(model, pannelloContenuto);
        
        // 3. Creiamo il pannello comandi (passandogli il controllore)
        PannelloComandi pannelloComandi = new PannelloComandi(controllore);
        
        // 4. Assembliamo i pezzi nel layout
        // Comandi in alto (NORTH) come in Slide 26
        mainPanel.add(pannelloComandi, BorderLayout.NORTH);
        // Contenuto al centro (CENTER)
        mainPanel.add(pannelloContenuto, BorderLayout.CENTER);
        
        // Impostiamo il pannello principale come contenuto della finestra
        setContentPane(mainPanel);
        
        // Rendiamo visibile
        setVisible(true);
    }

}
