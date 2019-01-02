package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Main;
import util.Constantes;
import util.Log;

@SuppressWarnings("unused")
public class ViewProblemaComLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5383020556060460911L;
	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ViewProblemaComLogin dialog = new ViewProblemaComLogin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewProblemaComLogin() {
		setModal(true);

		setResizable(false);
		initComponent();
	}

	private void initComponent() {
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);

		JCheckBox chckbxEsqueceuASenha = new JCheckBox("Esqueceu a senha ?");
		chckbxEsqueceuASenha.setActionCommand("1");
		buttonGroup.add(chckbxEsqueceuASenha);
		chckbxEsqueceuASenha.setFont(new Font("Arial", Font.PLAIN, 15));
		chckbxEsqueceuASenha.setBounds(6, 7, 182, 27);
		contentPanel.add(chckbxEsqueceuASenha);

		JCheckBox chckbxEsqueceuOLogin = new JCheckBox("Esqueceu o login ?");
		chckbxEsqueceuOLogin.setActionCommand("2");
		buttonGroup.add(chckbxEsqueceuOLogin);
		chckbxEsqueceuOLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		chckbxEsqueceuOLogin.setBounds(6, 50, 203, 27);
		contentPanel.add(chckbxEsqueceuOLogin);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("Enviar");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				try {
					HttpURLConnection httpURLConnection = null;

					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();

					if (buttonGroup.getSelection().getActionCommand().equals("1")) {
						dispose();
						ViewRecuperaSenha viewRecuperaSenha = new ViewRecuperaSenha();
						viewRecuperaSenha.setUndecorated(true);
						viewRecuperaSenha.setVisible(true);
					}
					if (buttonGroup.getSelection().getActionCommand().equals("2")) {
						dispose();
						ViewRecuperaLogin viewRecuperaLogin = new ViewRecuperaLogin();
						viewRecuperaLogin.setVisible(true);
					}
				} catch (SocketException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());

					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					dispose();

				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					System.gc();
					dispose();
				}

			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
}
