package gui.grafica.vista;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import modello.Articolo;
import modello.GestioneListe; 
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.ListaDiArticoliException;

/**
 * Pannello grafico che mostra il contenuto di una lista specifica.
 * Aggiornato con funzionalità complete: Aggiungi da Catalogo, Rimuovi, Ripristina, Svuota.
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

        JPanel panelComandi = new JPanel(new GridLayout(2, 3, 5, 5));
        panelComandi.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton btnAggiungiCat = new JButton("Aggiungi da Catalogo");
        JButton btnRimuovi = new JButton("Rimuovi Articolo");
        JButton btnModifica = new JButton("Modifica Articolo");
        JButton btnRipristina = new JButton("Ripristina Articolo");
        JButton btnSvuota = new JButton("Svuota Cestino");
        JButton btnAggiungiRapido = new JButton("Aggiungi Rapido (Test)");
        JButton btnAggiorna = new JButton("Aggiorna Vista");

        btnAggiungiCat.addActionListener(e -> aggiungiDaCatalogo());

        btnRimuovi.addActionListener(e -> rimuoviArticolo());
        
        btnModifica.addActionListener(e -> modificaArticolo());

        btnRipristina.addActionListener(e -> ripristinaArticolo());

        btnSvuota.addActionListener(e -> svuotaCestino());

        btnAggiungiRapido.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome articolo rapido:");
            if (nome != null && !nome.isEmpty()) {
                try {
                    listaCorrente.addArticolo(nome, 0);
                    aggiornaVista();
                } catch (ArticoloException ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        });
       
        btnAggiorna.addActionListener(e -> aggiornaVista());

        panelComandi.add(btnAggiungiCat);
        panelComandi.add(btnModifica);
        panelComandi.add(btnRimuovi);
        panelComandi.add(btnRipristina);
        panelComandi.add(btnSvuota);
        panelComandi.add(btnAggiungiRapido);
        panelComandi.add(btnAggiorna);
        
        add(panelComandi, BorderLayout.SOUTH);

        aggiornaVista();
    }

    /**
     * Logica per aggiungere un articolo scegliendolo dal Catalogo Globale
     */
    private void aggiungiDaCatalogo() {
        ArrayList<Articolo> catalogo = GestioneListe.getArticoli();
        if (catalogo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il catalogo è vuoto! Crea prima dei prodotti dal Menu Principale.");
            return;
        }

        Object[] scelte = new Object[catalogo.size()];
        for (int i = 0; i < catalogo.size(); i++) {
            Articolo a = catalogo.get(i);
            scelte[i] = a.getNome() + " (" + a.getCategoria() + ")";
        }

        String scelta = (String) JOptionPane.showInputDialog(this, 
                "Seleziona un prodotto dal catalogo:", 
                "Aggiungi alla Lista", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                scelte, 
                scelte[0]);

        if (scelta != null) {
            Articolo articoloScelto = null;
            for (Articolo a : catalogo) {
                if (scelta.startsWith(a.getNome() + " (")) {
                    articoloScelto = a;
                    break;
                }
            }

            if (articoloScelto != null) {
                try {
                    listaCorrente.addArticolo(
                        articoloScelto.getNome(), 
                        articoloScelto.getCategoria(), 
                        articoloScelto.getPrezzo(), 
                        articoloScelto.getNota()
                    );
                    aggiornaVista();
                } catch (ArticoloException ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
                }
            }
        }
    }

    /**
     * Logica per rimuovere un articolo (spostarlo nel cestino)
     */
    private void rimuoviArticolo() {
        ArrayList<Articolo> validi = listaCorrente.getArticoliValidi();
        if (validi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun articolo da rimuovere.");
            return;
        }

        Object[] scelte = new Object[validi.size()];
        for (int i = 0; i < validi.size(); i++) {
            scelte[i] = validi.get(i).getNome();
        }

        String nomeDaRimuovere = (String) JOptionPane.showInputDialog(this, 
                "Scegli l'articolo da cestinare:", 
                "Rimuovi Articolo", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                scelte, 
                scelte[0]);

        if (nomeDaRimuovere != null) {
            try {
                listaCorrente.removeArticolo(nomeDaRimuovere);
                aggiornaVista();
            } catch (ListaDiArticoliException ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    /**
     * Logica per ripristinare un articolo dal cestino
     */
    private void ripristinaArticolo() {
        ArrayList<Articolo> cestinati = listaCorrente.getArticoliCancellati();
        if (cestinati.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il cestino è vuoto.");
            return;
        }

        Object[] scelte = new Object[cestinati.size()];
        for (int i = 0; i < cestinati.size(); i++) {
            scelte[i] = cestinati.get(i).getNome();
        }

        String nomeDaRipristinare = (String) JOptionPane.showInputDialog(this, 
                "Scegli l'articolo da ripristinare:", 
                "Ripristina Articolo", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                scelte, 
                scelte[0]);

        if (nomeDaRipristinare != null) {
            try {
                listaCorrente.ripristina(nomeDaRipristinare);
                aggiornaVista();
                JOptionPane.showMessageDialog(this, "Articolo ripristinato!");
            } catch (ListaDiArticoliException ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        }
    }

    /**
     * Logica per svuotare definitivamente il cestino
     */
    private void svuotaCestino() {
        if (listaCorrente.getArticoliCancellati().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il cestino è già vuoto.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Sei sicuro di voler eliminare definitivamente gli articoli nel cestino?", 
                "Conferma Svuota Cestino", 
                JOptionPane.YES_NO_OPTION);
                
        if (confirm == JOptionPane.YES_OPTION) {
            listaCorrente.svuotaCestino();
            aggiornaVista();
            JOptionPane.showMessageDialog(this, "Cestino svuotato.");
        }
    }
    
    /**
     * Logica per modificare un articolo esistente (Prezzo, Categoria o Nota).
     */
    private void modificaArticolo() {
        ArrayList<Articolo> validi = listaCorrente.getArticoliValidi();
        
        if (validi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun articolo da modificare.");
            return;
        }

        Object[] scelteArticoli = new Object[validi.size()];
        for (int i = 0; i < validi.size(); i++) {
            scelteArticoli[i] = validi.get(i).getNome();
        }

        String nomeArticolo = (String) JOptionPane.showInputDialog(this, 
                "Scegli l'articolo da modificare:", 
                "Modifica Articolo", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                scelteArticoli, 
                scelteArticoli[0]);

        if (nomeArticolo != null) {
            Articolo art = listaCorrente.searchArticolo(nomeArticolo);
            
            String[] opzioni = {"Prezzo", "Categoria", "Nota"};
            int sceltaCampo = JOptionPane.showOptionDialog(this, 
                "Cosa vuoi modificare di '" + art.getNome() + "'?", 
                "Dettaglio Modifica",
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, opzioni, opzioni[0]);

            try {
                int nuovoPrezzo = art.getPrezzo();
                String nuovaCategoria = art.getCategoria();
                String nuovaNota = art.getNota();
                boolean procedi = false;

                switch (sceltaCampo) {
                    case 0: 
                        String inputPrezzo = JOptionPane.showInputDialog(this, "Nuovo prezzo (Attuale: " + art.getPrezzo() + "):");
                        if (inputPrezzo != null) {
                            nuovoPrezzo = Integer.parseInt(inputPrezzo);
                            procedi = true;
                        }
                        break;
                    case 1: 
                         String inputCat = JOptionPane.showInputDialog(this, "Nuova categoria (Attuale: " + art.getCategoria() + "):");
                         if (inputCat != null) {
                             nuovaCategoria = inputCat;
                             procedi = true;
                         }
                        break;
                    case 2: 
                        String inputNota = JOptionPane.showInputDialog(this, "Nuova nota (Attuale: " + art.getNota() + "):");
                        if (inputNota != null) {
                            nuovaNota = inputNota;
                            procedi = true;
                        }
                        break;
                }

                
                if (procedi) {
                    listaCorrente.modificaArticolo(nomeArticolo, nuovoPrezzo, nuovaCategoria, nuovaNota);
                    aggiornaVista();
                    JOptionPane.showMessageDialog(this, "Articolo modificato con successo!");
                }
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Errore: Il prezzo deve essere un numero intero!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore durante la modifica: " + ex.getMessage());
            }
        }
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
        lblTotale.setText("Totale: " + totale + "€");
    }
}