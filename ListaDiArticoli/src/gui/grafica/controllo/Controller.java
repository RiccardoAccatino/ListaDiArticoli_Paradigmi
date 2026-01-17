package gui.grafica.controllo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import gui.grafica.vista.PannelloContenuto;
import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.ListaDiArticoliException;
public class Controller implements ActionListener {

    private ListaDiArticoli model;
    private PannelloContenuto view;

    public Controller(ListaDiArticoli model, PannelloContenuto view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if (comando.equals("AGGIUNGI")) {
            ArrayList<Articolo> catalogo = GestioneListe.getArticoli();
            
            if (catalogo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Il catalogo è vuoto! Crea prima dei prodotti dal Menu Principale.");
                return;
            }

            // Prepariamo i nomi per la scelta
            Object[] scelte = new Object[catalogo.size()];
            for (int i = 0; i < catalogo.size(); i++) {
                Articolo a = catalogo.get(i);
                scelte[i] = a.getNome() + " (" + a.getCategoria() + ")";
            }

            String scelta = (String) JOptionPane.showInputDialog(null, 
                    "Seleziona un prodotto dal catalogo:", 
                    "Aggiungi alla Lista", 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    scelte, 
                    scelte[0]);

            if (scelta != null) {
                // Estraiamo il nome del prodotto selezionato (prima della parentesi)
                // Oppure cerchiamo l'oggetto corrispondente
                Articolo articoloScelto = null;
                for (Articolo a : catalogo) {
                    if (scelta.startsWith(a.getNome() + " (")) { // Match semplice
                        articoloScelto = a;
                        break;
                    }
                }

                if (articoloScelto != null) {
                    try {
                        // Aggiungiamo copiando i dati dal catalogo alla lista specifica
                        model.addArticolo(
                            articoloScelto.getNome(), 
                            articoloScelto.getCategoria(), 
                            articoloScelto.getPrezzo(), 
                            articoloScelto.getNota()
                        );
                        view.updateView();
                    } catch (ArticoloException ex) {
                        JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                    }
                }
            }
        }
     // --- UPDATE (MODIFICA) ---
        else if (comando.equals("MODIFICA")) {
            // 1. Chiediamo quale articolo modificare
            String nomeArticolo = JOptionPane.showInputDialog(null, "Inserisci il nome dell'articolo da modificare:");
            
            if (nomeArticolo != null && !nomeArticolo.trim().isEmpty()) {
                // Cerchiamo l'articolo per verificare che esista prima di chiedere i dati
                Articolo art = model.searchArticolo(nomeArticolo);
                
                // Verifichiamo che esista e non sia nel cestino (opzionale, ma buona prassi)
                if (art != null && !model.getArticoliCancellati().contains(art)) {
                    
                    // 2. Chiediamo cosa modificare. Per semplicità usiamo un dialog con opzioni
                    String[] opzioni = {"Prezzo", "Categoria", "Nota"};
                    int scelta = JOptionPane.showOptionDialog(null, 
                        "Cosa vuoi modificare di " + art.getNome() + "?", 
                        "Modifica Articolo",
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, opzioni, opzioni[0]);

                    try {
                        switch (scelta) {
                            case 0: // Prezzo
                                String nuovoPrezzoStr = JOptionPane.showInputDialog("Nuovo prezzo (Attuale: " + art.getPrezzo() + "):");
                                if (nuovoPrezzoStr != null) {
                                    int nuovoPrezzo = Integer.parseInt(nuovoPrezzoStr);
                                    // Usiamo il metodo del modello per modificare
                                    model.modificaArticolo(nomeArticolo, nuovoPrezzo, art.getCategoria(), art.getNota());
                                }
                                break;
                            case 1: // Categoria
                                 String nuovaCat = JOptionPane.showInputDialog("Nuova categoria (Attuale: " + art.getCategoria() + "):");
                                 if (nuovaCat != null) {
                                     model.modificaArticolo(nomeArticolo, art.getPrezzo(), nuovaCat, art.getNota());
                                 }
                                break;
                            case 2: // Nota
                                String nuovaNota = JOptionPane.showInputDialog("Nuova nota (Attuale: " + art.getNota() + "):");
                                if (nuovaNota != null) {
                                    model.modificaArticolo(nomeArticolo, art.getPrezzo(), art.getCategoria(), nuovaNota);
                                }
                                break;
                        }
                        // 3. Aggiorniamo la vista
                        view.updateView(); 
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Il prezzo deve essere un numero intero!");
                    } catch (Exception ex) { // Cattura ArticoloException o ListaDiArticoliException
                        JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                    }
                } else {
                     JOptionPane.showMessageDialog(null, "Articolo non trovato o presente nel cestino.");
                }
            }
        }
        // --- RIMOZIONE ---
        else if (comando.equals("RIMUOVI")) {
            String nome = JOptionPane.showInputDialog("Nome articolo da cestinare:");
            if (nome != null && !nome.trim().isEmpty()) {
                try {
                    model.removeArticolo(nome);
                    view.updateView();
                } catch (ListaDiArticoliException ex) {
                    JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                }
            }
        }

        // --- RIPRISTINO DAL CESTINO ---
        else if (comando.equals("RIPRISTINA")) {
            ArrayList<Articolo> cestinati = model.getArticoliCancellati();
            
            if (cestinati.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Il cestino è vuoto.");
                return;
            }

            // Creiamo una lista di scelta coi nomi nel cestino
            Object[] scelte = new Object[cestinati.size()];
            for (int i = 0; i < cestinati.size(); i++) {
                scelte[i] = cestinati.get(i).getNome();
            }

            String nomeDaRipristinare = (String) JOptionPane.showInputDialog(null, 
                    "Scegli l'articolo da ripristinare:", 
                    "Ripristina Articolo", 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    scelte, 
                    scelte[0]);

            if (nomeDaRipristinare != null) {
                try {
                    model.ripristina(nomeDaRipristinare);
                    view.updateView();
                    JOptionPane.showMessageDialog(null, "Articolo ripristinato!");
                } catch (ListaDiArticoliException ex) {
                    JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                }
            }
        }
        
        // --- SVUOTA CESTINO ---
        else if (comando.equals("SVUOTA")) {
             int confirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler svuotare il cestino?", "Conferma", JOptionPane.YES_NO_OPTION);
             if (confirm == JOptionPane.YES_OPTION) {
                 model.svuotaCestino();
                 view.updateView(); // Importante aggiornare la vista se mostriamo il cestino
                 JOptionPane.showMessageDialog(null, "Cestino svuotato.");
             }
        }
    }
}
