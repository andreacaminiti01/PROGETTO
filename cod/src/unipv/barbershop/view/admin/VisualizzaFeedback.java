package unipv.barbershop.view.admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import unipv.barbershop.model.feedback.Feedback;
import javax.swing.JFrame;

public class VisualizzaFeedback extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tabella;
	private DefaultTableModel model;

	public VisualizzaFeedback(List<Feedback> feedbackList) {
		setTitle("Recensioni Clienti");
		setSize(700, 450);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		String[] colonne = {"Cliente", "Data Appuntamento", "Voto", "Commento"};
		model = new DefaultTableModel(colonne, 0);

		for (Feedback f : feedbackList) {
			Object[] riga = {
					f.getCliente().getNome() + " " + f.getCliente().getCognome(),
					f.getPrenotazioneRiferimento().getDataOra().toString().replace("T", " "),
					f.getVoto() + " ★",
					f.getCommento()
			};
			model.addRow(riga);
		}

		tabella = new JTable(model);
		add(new JScrollPane(tabella), BorderLayout.CENTER);

		JButton btnChiudi = new JButton("Chiudi");
		btnChiudi.addActionListener(e -> dispose());
		add(btnChiudi, BorderLayout.SOUTH);
	}



}
