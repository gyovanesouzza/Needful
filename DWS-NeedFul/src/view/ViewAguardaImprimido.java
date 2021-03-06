package view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ViewAguardaImprimido extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6932306729751010377L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAguardaImprimido frame = new ViewAguardaImprimido();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewAguardaImprimido() {
		setUndecorated(true);
		initComponet();
	}

	private void initComponet() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 431, 130);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAguardeGerandoO = new JLabel("Aguarde, Estamos imprimido o relatorio...");
		lblAguardeGerandoO.setHorizontalAlignment(SwingConstants.CENTER);
		lblAguardeGerandoO.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAguardeGerandoO.setBounds(10, 25, 411, 18);
		contentPane.add(lblAguardeGerandoO);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(10, 77, 411, 14);
		contentPane.add(progressBar);
	}
}
