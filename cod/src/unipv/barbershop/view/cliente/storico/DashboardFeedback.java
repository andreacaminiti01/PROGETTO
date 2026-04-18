package unipv.barbershop.view.cliente.storico;
import javax.swing.*;
import java.awt.*;
import javax.swing.JDialog;

public class DashboardFeedback extends JDialog {
	private static final long serialVersionUID = 1L;
    private JComboBox<Integer> comboVoto;
    private JTextArea txtCommento;
    private JButton btnInvia;

    public DashboardFeedback(JFrame parentFrame) {
        super(parentFrame, "Lascia un Feedback", true); // 'true' la rende modale (blocca la finestra dietro)
        setSize(350, 250);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCentro = new JPanel(new BorderLayout(5, 5));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Selezione Voto
        JPanel panelVoto = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVoto.add(new JLabel("Voto (Stelle): "));
        Integer[] voti = {5, 4, 3, 2, 1};
        comboVoto = new JComboBox<>(voti);
        panelVoto.add(comboVoto);
        panelCentro.add(panelVoto, BorderLayout.NORTH);

        // Area Commento
        panelCentro.add(new JLabel("Scrivi un commento:"), BorderLayout.CENTER);
        txtCommento = new JTextArea(4, 20);
        txtCommento.setLineWrap(true);
        panelCentro.add(new JScrollPane(txtCommento), BorderLayout.SOUTH);

        add(panelCentro, BorderLayout.CENTER);

        // Bottone Invio
        btnInvia = new JButton("Invia Feedback");
        JPanel panelSud = new JPanel();
        panelSud.add(btnInvia);
        add(panelSud, BorderLayout.SOUTH);
    }

    public Integer getVotoScelto() { return (Integer) comboVoto.getSelectedItem(); }
    public String getCommentoTesto() { return txtCommento.getText().trim(); }
    public JButton getBtnInvia() { return btnInvia; }
}


