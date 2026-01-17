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
    private JLabel labelTotale; // Nuova etichetta per il totale

    public PannelloContenuto(ListaDiArticoli model) {
        this.model = model;
        setLayout(new BorderLayout());
        
        // --- NORD: Titolo ---
        JLabel titolo = new JLabel("Contenuto Lista: " + model.getNome());
        titolo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(titolo, BorderLayout.NORTH);

        // --- CENTRO: Area di testo ---
        areaTesto = new JTextArea(15, 30);
        areaTesto.setEditable(false);
        areaTesto.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(areaTesto, BorderLayout.CENTER);
        
        // --- SUD: Totale in Grassetto ---
        labelTotale = new JLabel("Totale: 0 €");
        // Impostiamo il font in Grassetto (BOLD) e un po' più grande (14)
        labelTotale.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelTotale.setHorizontalAlignment(SwingConstants.RIGHT); // Allineato a destra
        labelTotale.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5)); // Un po' di margine
        
        add(labelTotale, BorderLayout.SOUTH);
        
        // Aggiorniamo la vista appena creata
        updateView();
    }

    /**
     * Aggiorna il testo della lista e ricalcola il totale
     */
    public void updateView() {
        StringBuilder sb = new StringBuilder();
        int prezzoTotale = 0; // Variabile accumulatore
        
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
                
                // Sommiamo il prezzo
                prezzoTotale += a.getPrezzo();
            }
        }
        
        areaTesto.setText(sb.toString());
        
        // Aggiorniamo l'etichetta del totale
        labelTotale.setText("TOTALE LISTA: " + prezzoTotale + " €");
    }
}
