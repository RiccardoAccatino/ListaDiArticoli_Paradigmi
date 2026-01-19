package gui.riga_comando;

import java.util.Scanner;
import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;

/**
 * Gestisce l'interazione con l'utente tramite terminale.
 * Adattata per supportare le nuove funzionalità del modello (Iterabilità, Prezzo Totale, Incapsulamento).
 */
public class RigaComandoInterfaccia {
    private Scanner scanner;

    public RigaComandoInterfaccia() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Benvenuto nel gestore Liste di Articoli.");
        boolean running = true;
        
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Crea Lista");
            System.out.println("2. Visualizza Liste e Accedi");
            System.out.println("3. Esci");
            System.out.print("Scelta: ");
            
            String input = scanner.nextLine();
            
            switch (input) {
                case "1":
                    creaLista();
                    break;
                case "2":
                    gestisciListe();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    private void creaLista() {
        System.out.print("Nome nuova lista: ");
        String nome = scanner.nextLine();
        try {
            GestioneListe.createListaDiArticoli(nome);
            System.out.println("Lista creata con successo.");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private void gestisciListe() {
        var liste = GestioneListe.getListediarticoli();
        if (liste.isEmpty()) {
            System.out.println("Nessuna lista presente.");
            return;
        }

        System.out.println("Liste disponibili:");
        for (int i = 0; i < liste.size(); i++) {
            System.out.println((i + 1) + ". " + liste.get(i).getNome());
        }

        System.out.print("Seleziona numero lista (0 per tornare): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < liste.size()) {
                menuLista(liste.get(idx));
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
        }
    }

    private void menuLista(ListaDiArticoli lista) {
        boolean inLista = true;
        while (inLista) {
            System.out.println("\n--- LISTA: " + lista.getNome() + " ---");
            System.out.println("Totale spesa corrente: " + lista.calcolaPrezzoTotale() + "€");
            System.out.println("1. Aggiungi Articolo");
            System.out.println("2. Rimuovi Articolo");
            System.out.println("3. Visualizza Tutto (Iteratore)");
            System.out.println("4. Cerca Articolo (Prefisso)");
            System.out.println("5. Torna indietro");
            System.out.print("Scelta: ");

            String input = scanner.nextLine();
            try {
                switch (input) {
                    case "1":
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Prezzo: ");
                        int prezzo = Integer.parseInt(scanner.nextLine());
                        lista.addArticolo(nome, prezzo);
                        System.out.println("Articolo aggiunto.");
                        break;
                    case "2":
                        System.out.print("Nome da rimuovere: ");
                        lista.removeArticolo(scanner.nextLine());
                        System.out.println("Articolo rimosso (spostato nel cestino).");
                        break;
                    case "3":
                        stampaConIteratore(lista);
                        break;
                    case "4":
                        System.out.print("Inserisci prefisso ricerca: ");
                        Articolo trovato = lista.searchArticolo(scanner.nextLine());
                        if (trovato != null) System.out.println("Trovato: " + trovato);
                        else System.out.println("Nessun articolo trovato.");
                        break;
                    case "5":
                        inLista = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    /**
     * Dimostra l'uso dell'interfaccia Iterable implementata in ListaDiArticoli.
     */
    private void stampaConIteratore(ListaDiArticoli lista) {
        System.out.println("\n--- Elenco Completo (Validi + Cancellati) ---");
        for (Articolo a : lista) {
            System.out.println(a); 
        }
    }
}