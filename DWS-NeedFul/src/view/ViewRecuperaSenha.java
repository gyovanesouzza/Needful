package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.UsuarioController;
import util.Constantes;
import util.EnviarEmailSenha;
import util.GeradorCodigo;
import util.Log;
import util.LookAndFeel;
import validator.MascaraLogin;
import vo.UsuarioVO;

public class ViewRecuperaSenha extends JDialog {

	private static final long serialVersionUID = 8343091715462082960L;

	UsuarioController controller = new UsuarioController();
	UsuarioVO usuarioVO = new UsuarioVO();
	private JPanel panel;
	private JFormattedTextField txtLogin;
	private JButton btnProcurar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeel.Layout();
		try {
			ViewRecuperaSenha dialog = new ViewRecuperaSenha();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewRecuperaSenha() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewRecuperaSenha.class.getResource("/imgs/RSS.png")));
		setTitle("Recupera\u00E7\u00E3o de Senha");
		initComponent();
		setLocationRelativeTo(null);
	}

	private void initComponent() {
		setBounds(100, 100, 426, 111);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 417, 72);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLogin.setBounds(10, 25, 49, 20);
		panel.add(lblLogin);

		txtLogin = new JFormattedTextField();
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					usuarioVO.setLogin(txtLogin.getText());

					enviar();
				}
				txtLogin.setBackground(new Color(255, 255, 255));

			}
		});
		txtLogin.setBounds(139, 26, 127, 20);
		txtLogin.setDocument(new MascaraLogin());
		txtLogin.setColumns(10);
		panel.add(txtLogin);

		btnProcurar = new JButton("Enviar");
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				usuarioVO.setLogin(txtLogin.getText());

				enviar();
			}
		});
		btnProcurar.setBounds(297, 25, 89, 23);
		panel.add(btnProcurar);
	}

	private void enviarCodigo() throws SocketException, IOException {
		usuarioVO.setLogin(txtLogin.getText());

		if (controller.verificarExistenciaLoginEmail(usuarioVO)) {

			String codigo = GeradorCodigo.geradorCodigo();
			System.out.println(codigo);
			UsuarioVO vo = controller.buscarEmailLogin(usuarioVO);
			JOptionPane.showMessageDialog(null, "O codigo de seguração foi enviado para o seu E-Mail.");
			EnviarEmailSenha.enviarCodigo(codigo, vo);
			dispose();

			ViewRecuperaSenhaAlterar recuperaSenhaAlterar = new ViewRecuperaSenhaAlterar(codigo, vo);
			recuperaSenhaAlterar.setVisible(true);

		} else {
			Log.logInfo("Usuario não encontrado", getClass());

			JOptionPane.showMessageDialog(null, "Login Incorreto", "Error", JOptionPane.ERROR_MESSAGE);

		}

	}

	public boolean validarLogin() {
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor digite o Usuario", "Recupera Senha",
					JOptionPane.WARNING_MESSAGE);
			txtLogin.setBackground(new Color(240, 128, 128));
			return false;
		}
		return true;
	}

	private void enviarEmail() {
		try {
			HttpURLConnection httpURLConnection = null;

			URL name = new URL("http://"+Constantes.getIpWebservice()+":8080/");
			httpURLConnection = (HttpURLConnection) name.openConnection();
			httpURLConnection.connect();

			if (validarLogin()) {
				enviarCodigo();
			}
		} catch (SocketException e) {
			Log.logFatal(e.getMessage(), e.getCause(), getClass());

			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(ViewLogin.class.getName()).log(Level.ALL, null, e.getCause());
			System.gc();
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new ViewLogin().setVisible(true);
		} catch (IOException e) {
			Log.logFatal(e.getMessage(), e.getCause(), getClass());

			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}

	private void enviar() {
		try {

			if (controller.existerContaAD(usuarioVO)) {
				Log.logWarn("Tentativa de Recupera Senha do Usuario desativado : " + usuarioVO.getNome()
						+ " Com Login : " + usuarioVO.getLogin(), getClass());

				JOptionPane.showMessageDialog(null,
						"Usuario desativado, Por favor entre em contato com o Administrador", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			} else {

				enviarEmail();

			}
		} catch (SocketException e) {
			Log.logFatal(e.getMessage(), e.getCause(), getClass());
			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.gc();
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new ViewLogin().setVisible(true);
		} catch (IOException e) {
			Log.logFatal(e.getMessage(), e.getCause(), getClass());
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}
}
