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
