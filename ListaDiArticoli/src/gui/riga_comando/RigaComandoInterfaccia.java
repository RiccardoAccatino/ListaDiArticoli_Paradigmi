package gui.riga_comando;

import java.io.IOException;
import jbook.util.Input;
import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.exception.ArticoloException;
import modello.exception.GestioneListeException;
import modello.exception.ListaDiArticoliException;

public class RigaComandoInterfaccia {
    private Input input;
    private static final String FILE_DATI = "dati_save.bin";

    public RigaComandoInterfaccia() {
        input = new Input();
        try {
            System.out.println("--- Caricamento dati in corso... ---");
            GestioneListe.caricaDati(FILE_DATI);
            System.out.println("Dati caricati con successo!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nessun salvataggio trovato. Verranno create nuove liste vuote.");
        }
        
        avviaMenuPrincipale(); 
    }

    private void avviaMenuPrincipale() {
        int sc = 0;
        do {
            System.out.println("\n-----------LISTA DI ARTICOLI-------------");
            System.out.println("|1-Crea una nuova lista");
            System.out.println("|2-Seleziona una lista (entra nel menu lista)");
            System.out.println("|3-Aggiungi un prodotto al catalogo generale");
            System.out.println("|4-Aggiungi una categoria");
            System.out.println("|5-Esci (Salva e Chiudi)");
            System.out.println("-----------------------------------------");
      
            try {
                sc = Input.readInt("|Inserisci scelta: ");
            } catch (NumberFormatException e) {
                System.out.println("Per favore, inserisci un numero valido.");
                sc = 0;
                continue;
            }

            System.out.println("-----------------------------------------");

            switch (sc) {
                case 1:
                    creazioneLista();
                    break;
                case 2:
                    selezionaEGestisciLista();
                    break;
                case 3:
                    try {
                        aggiuntaProdottoCatalogo();
                    } catch (ArticoloException e) {
                        System.out.println("Errore: " + e.getMessage());
                    }
                    break;
                case 4:
                    aggiuntaCategoria();
                    break;
                case 5:
                    System.out.println("Salvataggio in corso...");
                    try {
                        GestioneListe.salvaDati(FILE_DATI);
                        System.out.println("Dati salvati correttamente in " + FILE_DATI);
                    } catch (IOException e) {
                        System.out.println("Errore critico nel salvataggio: " + e.getMessage());
                    }
                    System.out.println("Uscita in corso...");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        } while (sc != 5);
    }

    private void creazioneLista() {
        String nome = Input.readString("Inserisci il nome della lista che desideri creare: ");
        try {
            GestioneListe.createListaDiArticoli(nome);
            System.out.println("Lista creata con successo!");
        } catch (GestioneListeException e) {
            System.out.println("Errore durante la creazione della lista: " + e.getMessage());
        }
    }

    private void selezionaEGestisciLista() {
        String nomeLis = Input.readString("Inserisci il nome della lista che desideri selezionare: ");
        ListaDiArticoli listaSelezionata = trovaLista(nomeLis);

        if (listaSelezionata != null) {
            menuOperazioniLista(listaSelezionata);
        }
    }

    private void menuOperazioniLista(ListaDiArticoli lista) {
        int sc = 0;
        do {
            System.out.println("\n----------- " + lista.getNome().toUpperCase() + " -------------");
            System.out.println("|1-Aggiungi Prodotto alla lista");
            System.out.println("|2-Visualizza Articoli nella lista");
            System.out.println("|3-Cerca Prodotto nella lista");
            System.out.println("|4-Modifica un Prodotto (Prezzo/Cat/Note)");
            System.out.println("|5-Sposta Prodotto nel cestino");
            System.out.println("|6-Ripristina Prodotto");
            System.out.println("|7-Svuota Cestino");
            System.out.println("|8-Torna al Menu Principale");
            System.out.println("-----------------------------------------");
            
            try {
                sc = Input.readInt("|Inserisci scelta: ");
            } catch (NumberFormatException e) {
                 System.out.println("Inserisci un numero valido.");
                 sc = 0;
                 continue;
            }

            System.out.println("-----------------------------------------");

            switch (sc) {
                case 1:
                    aggiungiProdottoAllaLista(lista);
                    break;
                case 2:
                    visualizzaContenutoLista(lista);
                    break;
                case 3:
                    cercaProdottoNellaLista(lista);
                    break;
                case 4:
                    modificaArticolo(lista);
                    break;
                case 5:
                    spostaProdottoDallaLista(lista);
                    break;
                case 6:
                    ripristinaProdotto(lista);
                    break;
                case 7:
                    svuotaCesto(lista);
                    break;
                case 8:
                    System.out.println("Ritorno al menu principale...");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        } while (sc != 8);
    }
    
    private void modificaArticolo(ListaDiArticoli lista) {
        String nomeRicerca = Input.readString("Inserisci il nome dell'articolo da modificare: ");
        Articolo art = lista.searchArticolo(nomeRicerca);

        if (art == null) {
            System.out.println("Articolo non trovato!");
            return;
        }

        System.out.println("Stai modificando: " + art.getNome());
        System.out.println("1. Modifica Prezzo (Attuale: " + art.getPrezzo() + ")");
        System.out.println("2. Modifica Categoria (Attuale: " + art.getCategoria() + ")");
        System.out.println("3. Modifica Nota (Attuale: " + art.getNota() + ")");
        System.out.println("4. Annulla");
        
        int scelta = Input.readInt("Cosa vuoi cambiare? ");

        try {
            switch (scelta) {
                case 1:
                    int nuovoPrezzo = Input.readInt("Inserisci il nuovo prezzo: ");
                    art.setPrezzo(nuovoPrezzo);
                    System.out.println("Prezzo aggiornato.");
                    break;
                case 2:
                    String nuovaCat = Input.readString("Inserisci la nuova categoria: ");
                    art.setCategoria(nuovaCat);
                    System.out.println("Categoria aggiornata.");
                    break;
                case 3:
                    String nuovaNota = Input.readString("Inserisci la nota: ");
                    art.setNota(nuovaNota);
                    System.out.println("Nota aggiornata.");
                    break;
                case 4:
                    System.out.println("Modifica annullata.");
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        } catch (ArticoloException e) {
            System.out.println("Errore durante la modifica: " + e.getMessage());
        }
    }

    private void aggiuntaCategoria() {
        String nome = Input.readString("Inserisci il nome della categoria che desideri creare: ");
        try {
            GestioneListe.createCategoria(nome);
            System.out.println("Categoria aggiunta!");
        } catch (GestioneListeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private void aggiuntaProdottoCatalogo() throws ArticoloException {
        String nome = Input.readString("Inserisci il nome del prodotto per il catalogo: ");
        try {
            GestioneListe.createArticolo(nome);
            System.out.println("Prodotto aggiunto al catalogo!");
        } catch (GestioneListeException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private void svuotaCesto(ListaDiArticoli lista) {
        String conferma = Input.readString("Sei sicuro di voler svuotare il cestino? (s/n): ");
        if (conferma.equalsIgnoreCase("s")) {
            lista.svuotaCestino();
            System.out.println("Cestino svuotato.");
        } else {
            System.out.println("Operazione annullata.");
        }
    }

    private void ripristinaProdotto(ListaDiArticoli lista) {
        String nomeArticolo = Input.readString("Inserisci il nome dell'articolo da ripristinare: ");
        try {
            lista.ripristina(nomeArticolo);
            System.out.println("Articolo '" + nomeArticolo + "' ripristinato con successo.");
        } catch (ListaDiArticoliException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private void spostaProdottoDallaLista(ListaDiArticoli lista) {
        String nomeArticolo = Input.readString("Inserisci il nome dell'articolo da cestinare: ");
        try {
            lista.removeArticolo(nomeArticolo);
            System.out.println("Articolo '" + nomeArticolo + "' spostato nel cestino.");
        } catch (ListaDiArticoliException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private void cercaProdottoNellaLista(ListaDiArticoli lista) {
        String ricerca = Input.readString("Inserisci il nome (o parte del nome) da cercare: ");
        Articolo trovato = lista.searchArticolo(ricerca);

        if (trovato != null) {
            System.out.println("Articolo trovato: " + trovato.toString());
        } else {
            System.out.println("Nessun articolo trovato con: " + ricerca);
        }
    }
    
    private void visualizzaContenutoLista(ListaDiArticoli lista) {
        System.out.println("\n*** CONTENUTO DELLA LISTA: " + lista.getNome() + " ***");
        
        if (lista.getArticoliValidi().isEmpty()) {
            System.out.println("  [La lista è vuota]");
        } else {
            int contatore = 1;
            for (Articolo art : lista.getArticoliValidi()) {
                System.out.println(contatore + ". " + art.getNome() + 
                                   " (" + art.getCategoria() + ") - €" + art.getPrezzo());
                
                if (!art.getNota().isEmpty()) {
                    System.out.println("   Nota: " + art.getNota());
                }
                contatore++;
            }
        }

        if (!lista.getArticoliCancellati().isEmpty()) {
            System.out.println("\n--- Cestino (" + lista.getArticoliCancellati().size() + " articoli) ---");
            for (Articolo art : lista.getArticoliCancellati()) {
                System.out.println(" - [Cestinato] " + art.getNome());
            }
        }
        System.out.println("**************************************************");
    }

    private void aggiungiProdottoAllaLista(ListaDiArticoli lista) {
        String prd = Input.readString("Inserisci il nome del prodotto che vuoi aggiungere alla lista: ");
        try {
            lista.addArticolo(prd);
            System.out.println("Prodotto '" + prd + "' aggiunto con successo alla lista '" + lista.getNome() + "'.");
        } catch (ArticoloException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private ListaDiArticoli trovaLista(String nomeLista) {
        for (ListaDiArticoli lista : GestioneListe.getListediarticoli()) {
            if (lista.getNome().equals(nomeLista)) {
                return lista;
            }
        }
        System.out.println("Attenzione: Lista '" + nomeLista + "' non trovata.");
        return null;
    }
}