package gui.grafica.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;

/**
 * Finestra principale dell'applicazione GUI.
 * Permette di creare, eliminare e aprire liste, oltre a gestire il catalogo globale.
 */
public class MenuPrincipale extends JFrame implements ActionListener {

    private DefaultListModel<String> listModel;
    private JList<String> listaGrafica;
    private static final String FILE_DATI = "dati_save.bin";

    /**
     * Costruisce e visualizza il menu principale.
     */
    public MenuPrincipale() {
        setTitle("Gestione Liste - Menu Principale");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvaEdEsci();
            }
        });
        
        setBounds(100, 100, 700, 450);
        setLayout(new BorderLayout());

       
        listModel = new DefaultListModel<>();
        aggiornaElencoListe();
        
        listaGrafica = new JList<>(listModel);
        listaGrafica.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaGrafica);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Le tue Liste"));
        add(scrollPane, BorderLayout.CENTER);

        
    
        JPanel panelBottoni = new JPanel(new GridLayout(6, 1, 10, 10)); 
        panelBottoni.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnApriLista = new JButton("Apri Lista Selezionata");
        btnApriLista.setActionCommand("APRI_LISTA");
        btnApriLista.addActionListener(this);
        
        JButton btnEliminaLista = new JButton("Elimina Lista Selezionata");
        btnEliminaLista.setActionCommand("ELIMINA_LISTA");
        btnEliminaLista.addActionListener(this);

        JButton btnCreaLista = new JButton("Crea Nuova Lista");
        btnCreaLista.setActionCommand("CREA_LISTA");
        btnCreaLista.addActionListener(this);

        JButton btnCreaCat = new JButton("Crea Categoria");
        btnCreaCat.setActionCommand("CREA_CATEGORIA");
        btnCreaCat.addActionListener(this);

        JButton btnCreaProd = new JButton("Crea Prodotto (Catalogo)");
        btnCreaProd.setActionCommand("CREA_PRODOTTO");
        btnCreaProd.addActionListener(this);
        
        
        panelBottoni.add(btnApriLista);
        panelBottoni.add(btnEliminaLista); 
        panelBottoni.add(new JLabel("--- Creazione ---", JLabel.CENTER));
        panelBottoni.add(btnCreaLista);
        panelBottoni.add(btnCreaCat);
        panelBottoni.add(btnCreaProd);

        add(panelBottoni, BorderLayout.EAST);
        setVisible(true);
    }
    
    /**
     * Salva i dati su file e termina l'applicazione.
     */
    private void salvaEdEsci() {
        try {
            GestioneListe.salvaDati(FILE_DATI);
            System.out.println("Dati salvati correttamente all'uscita della GUI.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nel salvataggio: " + e.getMessage());
        } finally {
            System.exit(0);
        }
    }

    /**
     * Aggiorna la JList grafica con l'elenco delle liste presenti nel modello.
     */
    private void aggiornaElencoListe() {
        listModel.clear();
        if(GestioneListe.getListediarticoli() != null) {
            for (ListaDiArticoli l : GestioneListe.getListediarticoli()) {
                listModel.addElement(l.getNome());
            }
        }
    }

    /**
     * Gestisce gli eventi dei pulsanti del menu principale.
     * @param e L'evento generato.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            if (cmd.equals("CREA_LISTA")) {
                String nome = JOptionPane.showInputDialog(this, "Nome della nuova lista:");
                if (nome != null && !nome.trim().isEmpty()) {
                    GestioneListe.createListaDiArticoli(nome);
                    aggiornaElencoListe();
                }
            }
            else if (cmd.equals("APRI_LISTA")) {
                apriListaSelezionata();
            }
            
            else if (cmd.equals("ELIMINA_LISTA")) {
                eliminaListaSelezionata();
            }
            else if (cmd.equals("CREA_CATEGORIA")) {
                String cat = JOptionPane.showInputDialog(this, "Nome nuova categoria:");
                if (cat != null && !cat.trim().isEmpty()) {
                    GestioneListe.createCategoria(cat);
                    JOptionPane.showMessageDialog(this, "Categoria creata.");
                }
            }
            else if (cmd.equals("CREA_PRODOTTO")) {
                creaProdottoCompleto();
            }

        } catch (GestioneListeException | ArticoloException ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Apre la finestra di dettaglio per la lista selezionata.
     */
    private void apriListaSelezionata() {
    	String nomeSelezionato = listaGrafica.getSelectedValue();
        if (nomeSelezionato == null) {
            JOptionPane.showMessageDialog(this, "Seleziona una lista da aprire.");
            return;
        }
        for(ListaDiArticoli l : GestioneListe.getListediarticoli()) {
            if(l.getNome().equals(nomeSelezionato)) {
                JFrame frameDettaglio = new JFrame("Dettaglio Lista: " + l.getNome());
                frameDettaglio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                
                frameDettaglio.add(new SchermataLista(l));
                
                frameDettaglio.pack();
                frameDettaglio.setLocationRelativeTo(null); 
                frameDettaglio.setVisible(true);
                break;
            }
        }
    }

    /**
     * Elimina la lista selezionata dopo conferma dell'utente.
     * @throws GestioneListeException Se ci sono errori durante la rimozione.
     */
    private void eliminaListaSelezionata() throws GestioneListeException {
        String nomeSelezionato = listaGrafica.getSelectedValue();
        
        if (nomeSelezionato == null) {
            JOptionPane.showMessageDialog(this, "Seleziona una lista da eliminare.");
            return;
        }

       
        int scelta = JOptionPane.showConfirmDialog(
            this, 
            "Sei sicuro di voler eliminare definitivamente la lista '" + nomeSelezionato + "'?", 
            "Conferma Eliminazione", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (scelta == JOptionPane.YES_OPTION) {
            GestioneListe.removeListaDiArticoli(nomeSelezionato);
            aggiornaElencoListe();
            JOptionPane.showMessageDialog(this, "Lista eliminata con successo.");
        }
    }

    /**
     * Apre un dialog complesso per la creazione di un nuovo prodotto nel catalogo.
     * @throws GestioneListeException Se ci sono errori nel gestore liste.
     * @throws ArticoloException Se i dati inseriti per l'articolo non sono validi.
     */
    private void creaProdottoCompleto() throws GestioneListeException, ArticoloException {
        ArrayList<String> categorie = GestioneListe.getCategorie();
        if (categorie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Crea prima almeno una Categoria!");
            return;
        }

        JTextField fieldNome = new JTextField();
        JTextField fieldPrezzo = new JTextField();
        JTextField fieldNota = new JTextField();
        JComboBox<String> comboCat = new JComboBox<>(categorie.toArray(new String[0]));

        JPanel panelInput = new JPanel(new GridLayout(0, 1));
        panelInput.add(new JLabel("Nome Prodotto:"));
        panelInput.add(fieldNome);
        panelInput.add(new JLabel("Prezzo (Intero):"));
        panelInput.add(fieldPrezzo);
        panelInput.add(new JLabel("Categoria:"));
        panelInput.add(comboCat);
        panelInput.add(new JLabel("Nota (Opzionale):"));
        panelInput.add(fieldNota);

        int result = JOptionPane.showConfirmDialog(this, panelInput, 
                "Nuovo Prodotto a Catalogo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nome = fieldNome.getText().trim();
            String prezzoStr = fieldPrezzo.getText().trim();
            String cat = (String) comboCat.getSelectedItem();
            String nota = fieldNota.getText().trim();

            if (nome.isEmpty() || prezzoStr.isEmpty()) {
                throw new ArticoloException("Nome e Prezzo sono obbligatori.");
            }

            int prezzo = 0;
            try {
                prezzo = Integer.parseInt(prezzoStr);
            } catch (NumberFormatException ex) {
                throw new ArticoloException("Il prezzo deve essere un numero intero.");
            }

            GestioneListe.createArticolo(nome);
            
            for (Articolo a : GestioneListe.getArticoli()) {
                if (a.getNome().equals(nome)) {
                    a.setPrezzo(prezzo);
                    a.setCategoria(cat);
                    a.setNota(nota);
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Prodotto aggiunto al catalogo!");
        }
    }
}