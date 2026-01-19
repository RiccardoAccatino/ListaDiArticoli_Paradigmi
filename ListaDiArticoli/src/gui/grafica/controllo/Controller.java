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

/**
 * Gestisce le interazioni dell'utente provenienti dall'interfaccia grafica (View)
 * e aggiorna di conseguenza il modello dei dati (Model).
 * <p>
 * Implementa {@link ActionListener} per catturare i click sui pulsanti del {@link gui.grafica.vista.PannelloComandi}.
 * </p>
 */
public class Controller implements ActionListener {

    private ListaDiArticoli model;
    private PannelloContenuto view;

    /**
     * Costruttore del Controller.
     * @param model Il modello dati da gestire (la lista di articoli).
     * @param view La vista da aggiornare dopo le operazioni.
     */
    public Controller(ListaDiArticoli model, PannelloContenuto view) {
        this.model = model;
        this.view = view;
    }

    /**
    * Gestisce gli eventi scatenati dai pulsanti dell'interfaccia.
    * <p>
    * Comandi gestiti:
    * <ul>
    * <li><b>AGGIUNGI:</b> Mostra un dialog per selezionare un prodotto dal catalogo e aggiungerlo alla lista.</li>
    * <li><b>MODIFICA:</b> Permette di modificare prezzo, categoria o nota di un articolo esistente.</li>
    * <li><b>RIMUOVI:</b> Sposta un articolo nel cestino.</li>
    * <li><b>RIPRISTINA:</b> Recupera un articolo dal cestino.</li>
    * <li><b>SVUOTA:</b> Elimina definitivamente gli oggetti nel cestino.</li>
    * </ul>
    * </p>
    * @param e L'evento che ha scatenato la chiamata.
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if (comando.equals("AGGIUNGI")) {
            ArrayList<Articolo> catalogo = GestioneListe.getArticoli();
            
            if (catalogo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Il catalogo è vuoto! Crea prima dei prodotti dal Menu Principale.");
                return;
            }

            
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
                Articolo articoloScelto = null;
                for (Articolo a : catalogo) {
                    if (scelta.startsWith(a.getNome() + " (")) {
                        articoloScelto = a;
                        break;
                    }
                }

                if (articoloScelto != null) {
                    try {
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
        }else if (comando.equals("MODIFICA")) {
            String nomeArticolo = JOptionPane.showInputDialog(null, "Inserisci il nome dell'articolo da modificare:");
            
            if (nomeArticolo != null && !nomeArticolo.trim().isEmpty()) {
                Articolo art = model.searchArticolo(nomeArticolo);
                if (art != null && !model.getArticoliCancellati().contains(art)) {
                    String[] opzioni = {"Prezzo", "Categoria", "Nota"};
                    int scelta = JOptionPane.showOptionDialog(null, 
                        "Cosa vuoi modificare di " + art.getNome() + "?", 
                        "Modifica Articolo",
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, opzioni, opzioni[0]);

                    try {
                        switch (scelta) {
                            case 0:
                                String nuovoPrezzoStr = JOptionPane.showInputDialog("Nuovo prezzo (Attuale: " + art.getPrezzo() + "):");
                                if (nuovoPrezzoStr != null) {
                                    int nuovoPrezzo = Integer.parseInt(nuovoPrezzoStr);
                                    model.modificaArticolo(nomeArticolo, nuovoPrezzo, art.getCategoria(), art.getNota());
                                }
                                break;
                            case 1:
                                 String nuovaCat = JOptionPane.showInputDialog("Nuova categoria (Attuale: " + art.getCategoria() + "):");
                                 if (nuovaCat != null) {
                                     model.modificaArticolo(nomeArticolo, art.getPrezzo(), nuovaCat, art.getNota());
                                 }
                                break;
                            case 2:
                                String nuovaNota = JOptionPane.showInputDialog("Nuova nota (Attuale: " + art.getNota() + "):");
                                if (nuovaNota != null) {
                                    model.modificaArticolo(nomeArticolo, art.getPrezzo(), art.getCategoria(), nuovaNota);
                                }
                                break;
                        }

                        view.updateView(); 
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Il prezzo deve essere un numero intero!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                    }
                } else {
                     JOptionPane.showMessageDialog(null, "Articolo non trovato o presente nel cestino.");
                }
            }
        }else if (comando.equals("RIMUOVI")) {
            String nome = JOptionPane.showInputDialog("Nome articolo da cestinare:");
            if (nome != null && !nome.trim().isEmpty()) {
                try {
                    model.removeArticolo(nome);
                    view.updateView();
                } catch (ListaDiArticoliException ex) {
                    JOptionPane.showMessageDialog(null, "Errore: " + ex.getMessage());
                }
            }
        }else if (comando.equals("RIPRISTINA")) {
            ArrayList<Articolo> cestinati = model.getArticoliCancellati();
            
            if (cestinati.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Il cestino è vuoto.");
                return;
            }

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
        }else if (comando.equals("SVUOTA")) {
             int confirm = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler svuotare il cestino?", "Conferma", JOptionPane.YES_NO_OPTION);
             if (confirm == JOptionPane.YES_OPTION) {
                 model.svuotaCestino();
                 view.updateView();
                 JOptionPane.showMessageDialog(null, "Cestino svuotato.");
             }
        }
    }
}