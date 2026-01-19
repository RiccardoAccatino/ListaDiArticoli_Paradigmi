package gui.grafica.vista;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import modello.ListaDiArticoli;
import modello.Articolo;
public class PannelloContenuto extends JPanel {
    
    private ListaDiArticoli model;
    private JTextArea areaTesto;
    private JLabel labelTotale;

    public PannelloContenuto(ListaDiArticoli model) {
        this.model = model;
        setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Contenuto Lista: " + model.getNome());
        titolo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(titolo, BorderLayout.NORTH);

        areaTesto = new JTextArea(15, 30);
        areaTesto.setEditable(false);
        areaTesto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(areaTesto, BorderLayout.CENTER);

        labelTotale = new JLabel("Totale: 0 €");
        labelTotale.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelTotale.setHorizontalAlignment(SwingConstants.RIGHT);
        labelTotale.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        add(labelTotale, BorderLayout.SOUTH);
        updateView();
    }

    public void updateView() {
        StringBuilder sb = new StringBuilder();
        int prezzoTotale = 0;
        
        if (model.getArticoliValidi().isEmpty()) {
            sb.append("--- La lista è vuota ---");
        } else {
            for (Articolo a : model.getArticoliValidi()) {
                sb.append("- ").append(a.getNome())
                  .append(" (").append(a.getCategoria()).append(")")
                  .append(": ").append(a.getPrezzo()).append(" €");
                
                if (!a.getNota().isEmpty()) {
                    sb.append(" [Note: ").append(a.getNota()).append("]");
                }
                sb.append("\n");

                prezzoTotale += a.getPrezzo();
            }
        }
        
        areaTesto.setText(sb.toString());

        labelTotale.setText("TOTALE LISTA: " + prezzoTotale + " €");
    }
}
