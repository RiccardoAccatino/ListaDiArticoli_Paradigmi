package gui.grafica.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modello.Articolo;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;

/**
 * Pannello grafico che mostra il contenuto di una lista specifica.
 * Aggiornato per mostrare il prezzo totale.
 */
public class SchermataLista extends JPanel {
    private ListaDiArticoli listaCorrente;
    private DefaultListModel<String> listModelValidi;
    private DefaultListModel<String> listModelCancellati;
    private JLabel lblTotale; 

    public SchermataLista(ListaDiArticoli lista) {
        this.listaCorrente = lista;
        setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.add(new JLabel("Lista: " + lista.getNome()));
        
        lblTotale = new JLabel("Totale: 0");
        lblTotale.setFont(new Font("Arial", Font.BOLD, 14));
        panelInfo.add(Box.createHorizontalStrut(20)); 
        panelInfo.add(lblTotale);
        
        add(panelInfo, BorderLayout.NORTH);

        JPanel panelListe = new JPanel(new GridLayout(1, 2));
        
        listModelValidi = new DefaultListModel<>();
        JList<String> jListValidi = new JList<>(listModelValidi);
        jListValidi.setBorder(BorderFactory.createTitledBorder("Articoli da comprare"));
        panelListe.add(new JScrollPane(jListValidi));

        listModelCancellati = new DefaultListModel<>();
        JList<String> jListCancellati = new JList<>(listModelCancellati);
        jListCancellati.setBorder(BorderFactory.createTitledBorder("Cestino (Cancellati)"));
        panelListe.add(new JScrollPane(jListCancellati));

        add(panelListe, BorderLayout.CENTER);

        JPanel panelComandi = new JPanel();
        JButton btnAggiungi = new JButton("Aggiungi Rapido");
        JButton btnAggiorna = new JButton("Aggiorna Vista");

        btnAggiungi.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Nome articolo:");
            if (nome != null && !nome.isEmpty()) {
                try {
                    listaCorrente.addArticolo(nome, 100);
                    aggiornaVista();
                } catch (ArticoloException ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        });
        
        btnAggiorna.addActionListener(e -> aggiornaVista());

        panelComandi.add(btnAggiungi);
        panelComandi.add(btnAggiorna);
        add(panelComandi, BorderLayout.SOUTH);

        aggiornaVista();
    }

    /**
     * Aggiorna le liste a video e il totale.
     */
    public void aggiornaVista() {
        listModelValidi.clear();
        listModelCancellati.clear();

        for (Articolo a : listaCorrente.getArticoliValidi()) {
            listModelValidi.addElement(a.toString());
        }

        for (Articolo a : listaCorrente.getArticoliCancellati()) {
            listModelCancellati.addElement(a.toString());
        }

        int totale = listaCorrente.calcolaPrezzoTotale();
        lblTotale.setText("Totale: " + totale + " (valuta)");
    }
}