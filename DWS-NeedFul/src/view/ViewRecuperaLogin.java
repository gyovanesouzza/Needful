package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonSyntaxException;

import controller.UsuarioController;
import util.Log;
import validator.MascaraEmail;
import vo.UsuarioVO;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ViewRecuperaLogin extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2783285639060990102L;
	UsuarioController controller = new UsuarioController();
	UsuarioVO usuarioVO = new UsuarioVO();

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEmail;
	private JTextField txtLogin;
	private JButton btnProcurar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ViewRecuperaLogin dialog = new ViewRecuperaLogin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewRecuperaLogin() {
		initCompotent();
	}

	private void initCompotent() {
		setBounds(100, 100, 449, 172);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLogin.setBounds(10, 63, 43, 18);
		contentPanel.add(lblLogin);

		JLabel lblEmail = new JLabel("E-Mail: ");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEmail.setBounds(10, 23, 48, 18);
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent evt) {

				if (validarEmail()) {
					try {
						procurarLogin();
					} catch (SocketException e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						dispose();
					} catch (IOException e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());

						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						dispose();
					}
				}else{
					txtEmail.setBackground(new Color(240, 128, 128));
				}

			}
		});
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				txtEmail.setBackground(new Color(255, 255, 255));

				if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					btnProcurar.setEnabled(false);

				}
				btnProcurar.setEnabled(false);
				txtLogin.setBackground(Color.LIGHT_GRAY);
				if (validarEmail()) {
					if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
						try {
							procurarLogin();
						} catch (SocketException e) {
							Log.logFatal(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null,
									"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
									"ERROR", JOptionPane.ERROR_MESSAGE);
							dispose();
						} catch (IOException e) {
							Log.logFatal(e.getMessage(), e.getCause(), getClass());

							JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
							dispose();
						}

					}
				}
			}
		});
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEmail.setBounds(68, 23, 212, 20);
		contentPanel.add(txtEmail);
		txtEmail.setColumns(10);

		txtLogin = new JTextField();
		txtLogin.setEditable(false);
		txtLogin.setForeground(Color.BLACK);
		txtLogin.setBackground(Color.LIGHT_GRAY);
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLogin.setColumns(10);
		txtLogin.setBounds(68, 63, 212, 20);
		contentPanel.add(txtLogin);

		btnProcurar = new JButton("Procurar");
		btnProcurar.setEnabled(false);
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				try {
					procurarLogin();
				} catch (SocketException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);

				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		btnProcurar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnProcurar.setBounds(319, 22, 89, 23);
		contentPanel.add(btnProcurar);

	}

	private boolean validarEmail() {
		boolean retorno = false;
		txtLogin.setText("");
		if (MascaraEmail.validarEmail(txtEmail.getText())) {
			retorno = true;
			btnProcurar.setEnabled(true);
			usuarioVO.setEmail(txtEmail.getText());
		}
		return retorno;
	}

	private void procurarLogin() throws JsonSyntaxException, SocketException, IOException {

		if (controller.verificarExistenciaLoginEmail(usuarioVO)) {
			if (!controller.existerContaAD(usuarioVO)) {
				UsuarioVO vo = controller.buscarEmailLogin(usuarioVO);
				Log.logInfo("Usuario com o E-Mail : " + usuarioVO.getEmail() + " Esqueceu o Login", getClass());
				txtLogin.setText(vo.getLogin());
			} else {
				Log.logWarn(
						"Tentativa de Recupera o login do Usuario desativado com o E-Mail : " + usuarioVO.getEmail(),
						getClass());
				JOptionPane.showMessageDialog(null,
						"Usuario desativado, Por favor entre em contato com o Administrador", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			Log.logInfo("Usuario com o E-Mail : " + usuarioVO.getEmail() + " não encontrado", getClass());

			JOptionPane.showMessageDialog(null, "Usuario não encontrado", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
