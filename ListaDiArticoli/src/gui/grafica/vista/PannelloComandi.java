package gui.grafica.vista;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import gui.grafica.controllo.Controller;

public class PannelloComandi extends JPanel {

    public PannelloComandi(Controller controllore) {
        setLayout(new FlowLayout());

        JButton btnAggiungi = new JButton("Aggiungi");
        btnAggiungi.setActionCommand("AGGIUNGI");
        btnAggiungi.addActionListener(controllore);
        add(btnAggiungi);

       
        JButton btnModifica = new JButton("Modifica");
        btnModifica.setActionCommand("MODIFICA");
        btnModifica.addActionListener(controllore);
        add(btnModifica);
        

        JButton btnRimuovi = new JButton("Rimuovi");
        btnRimuovi.setActionCommand("RIMUOVI");
        btnRimuovi.addActionListener(controllore);
        add(btnRimuovi);
        
        JButton btnRipristina = new JButton("Ripristina");
        btnRipristina.setActionCommand("RIPRISTINA");
        btnRipristina.addActionListener(controllore);
        add(btnRipristina);
        
        JButton btnSvuota = new JButton("Svuota Cestino");
        btnSvuota.setActionCommand("SVUOTA");
        btnSvuota.addActionListener(controllore);
        add(btnSvuota);
    }
}