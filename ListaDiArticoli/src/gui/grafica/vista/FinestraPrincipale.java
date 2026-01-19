package gui.grafica.vista;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.grafica.controllo.Controller;
import modello.ListaDiArticoli;
public class FinestraPrincipale extends JFrame{
	public FinestraPrincipale(ListaDiArticoli model) {
        setTitle("Gestione Lista: " + model.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        PannelloContenuto pannelloContenuto = new PannelloContenuto(model);
        Controller controllore = new Controller(model, pannelloContenuto);
        PannelloComandi pannelloComandi = new PannelloComandi(controllore);
        mainPanel.add(pannelloComandi, BorderLayout.NORTH);
        mainPanel.add(pannelloContenuto, BorderLayout.CENTER);
        setContentPane(mainPanel);
        setVisible(true);
    }

}
