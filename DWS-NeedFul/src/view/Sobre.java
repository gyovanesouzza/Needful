package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import util.Constantes;
import util.Log;

public class Sobre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Process p;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre frame = new Sobre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sobre() {
		setType(Type.UTILITY);
		initComponent();
	}

	private void initComponent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/imgs/RSS.png")));
		setTitle("NeedFul");
		setLocationRelativeTo(null);
		setBounds(100, 100, 613, 185);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDesevolvidoPeloGrupo = new JLabel("Software desevolvido para o Trabalho de Conclus\u00E3o de Curso.");
		lblDesevolvidoPeloGrupo.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesevolvidoPeloGrupo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDesevolvidoPeloGrupo.setBounds(10, 11, 587, 22);
		contentPane.add(lblDesevolvidoPeloGrupo);

		JButton btnManualDeInstruo = new JButton("Manual de Instru\u00E7\u00E3o");
		btnManualDeInstruo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					p = Runtime.getRuntime().exec("cmd.exe /C " + Constantes.getLocalArquivos() + "\\manual.pdf");
				} catch (IOException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnManualDeInstruo.setBounds(143, 128, 156, 23);
		contentPane.add(btnManualDeInstruo);

		JLabel lblMail = new JLabel("Suporte Tecnico");
		lblMail.setForeground(Color.BLUE);
		lblMail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMail.setHorizontalAlignment(SwingConstants.CENTER);
		lblMail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.mail(new URI("mailto:" + Constantes.getEmailneedful()));
				} catch (IOException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
				} catch (URISyntaxException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
				}
			}
		});
		lblMail.setBounds(340, 129, 164, 18);
		contentPane.add(lblMail);

		JLabel lblIntituioCentro = new JLabel("Intitui\u00E7\u00E3o : Centro Paula Souza - Etec Jardim Angela.\r\n");
		lblIntituioCentro.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntituioCentro.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIntituioCentro.setBounds(10, 41, 587, 22);
		contentPane.add(lblIntituioCentro);

		JLabel lblParceira = new JLabel("Parceiro: USB Internet ");
		lblParceira.setHorizontalAlignment(SwingConstants.CENTER);
		lblParceira.setFont(new Font("Arial", Font.PLAIN, 15));
		lblParceira.setBounds(10, 69, 587, 22);
		contentPane.add(lblParceira);
	}
}
