package unipv.barbershop.view.prenotazione;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import unipv.barbershop.model.booking.Servizio;
import unipv.barbershop.model.staff.Barbiere;
import javax.swing.*;

public class DashboardNewPrenotazione extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Barbiere> comboBarbiere;
    private JTextField txtData; 
    private JComboBox<String> comboOre;
    private List<JCheckBox> chkServiziList;
    private JButton btnConferma;

    public DashboardNewPrenotazione(List<Barbiere> barbieri, List<Servizio> servizi) {
        setTitle("Nuova Prenotazione");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(0, 1, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelForm.add(new JLabel("Scegli Barbiere:"));
        comboBarbiere = new JComboBox<>(barbieri.toArray(new Barbiere[0]));
        panelForm.add(comboBarbiere);

        panelForm.add(new JLabel("Data (YYYY-MM-DD):"));
        txtData = new JTextField();
        panelForm.add(txtData);

        panelForm.add(new JLabel("Ora:"));
        comboOre = new JComboBox<>(new String[]{"09:00", "10:00", "11:00", "15:00", "16:00", "17:00"});
        panelForm.add(comboOre);

        panelForm.add(new JLabel("Seleziona Servizi:"));
        chkServiziList = new ArrayList<>();
        JPanel pnlSrv = new JPanel();
        pnlSrv.setLayout(new BoxLayout(pnlSrv, BoxLayout.Y_AXIS));
        for (Servizio s : servizi) {
            JCheckBox chk = new JCheckBox(s.getNome() + " (€" + s.getPrezzo() + ")");
            chk.putClientProperty("srv", s);
            chkServiziList.add(chk);
            pnlSrv.add(chk);
        }
        panelForm.add(new JScrollPane(pnlSrv));

        btnConferma = new JButton("Conferma Prenotazione");
        add(panelForm, BorderLayout.CENTER);
        add(btnConferma, BorderLayout.SOUTH);
    }
    /**
     * Metodo ad oggetti: la View espone solo i dati del modello, 
     * nascondendo i componenti grafici (le CheckBox) al Controller.
     */
    public List<Servizio> getServiziSelezionati() {
        List<Servizio> scelti = new ArrayList<>();
        
        for (JCheckBox chk : chkServiziList) {
            if (chk.isSelected()) {
                // Recuperiamo l'oggetto Servizio usando la chiave definita nel ciclo for della View
                Servizio s = (Servizio) chk.getClientProperty("srv");
                if (s != null) {
                    scelti.add(s);
                }
            }
        }
        return scelti;
    }

    // Getter per il Controller
    public JComboBox<Barbiere> getComboBarbiere() { return comboBarbiere; }
    public JTextField getTxtData() { return txtData; }
    public JComboBox<String> getComboOre() { return comboOre; }
    public List<JCheckBox> getChkServiziList() { return chkServiziList; }
    public JButton getBtnConferma() { return btnConferma; }
}
