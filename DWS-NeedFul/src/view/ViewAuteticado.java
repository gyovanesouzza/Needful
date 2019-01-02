package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonSyntaxException;

import controller.UsuarioController;
import util.Constantes;
import util.CriptografaSenha;
import util.LookAndFeel;
import vo.UsuarioVO;

public class ViewAuteticado extends JDialog {

	UsuarioController controller = new UsuarioController();

	private static final long serialVersionUID = 8977668388961620980L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * @wbp.parser.constructor
	 */
	public ViewAuteticado(java.awt.Frame parent, boolean modal) {
		super();
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ViewAuteticado.class.getResource("/imgs/icons8-lock-30.png")));
		setFont(new Font("Arial", Font.PLAIN, 15));
		setModal(true);
		setTitle("\u00C1rea Restrita");
		initComponents();

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public ViewAuteticado(ActionListener actionListener, boolean modal) {

		super();
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ViewAuteticado.class.getResource("/imgs/icons8-lock-30.png")));
		setFont(new Font("Arial", Font.PLAIN, 15));
		setModal(true);
		setTitle("\u00C1rea Restrita");
		initComponents();

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public ViewAuteticado() {
		initComponents();
	}

	public static void main(String[] args) {

		LookAndFeel.Layout();
		try {
			ViewAuteticado dialog = new ViewAuteticado(new javax.swing.JFrame(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	private void initComponents() {

		setBounds(100, 100, 425, 231);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());

		panel.setBounds(10, 11, 399, 181);
		contentPanel.add(panel);
		panel.setLayout(null);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				acesso();
			}
		});
		btnEntrar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEntrar.setBounds(293, 132, 79, 27);
		panel.add(btnEntrar);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLogin.setBounds(96, 36, 43, 20);
		panel.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSenha.setBounds(96, 77, 46, 20);
		panel.add(lblSenha);

		txtLogin = new JTextField();
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				proibidoCopiaColar(evt);
				txtLogin.setBackground(new Color(255, 255, 255));

			}
		});
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLogin.setBounds(156, 36, 124, 20);
		panel.add(txtLogin);
		txtLogin.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		txtSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				txtSenha.setBackground(new Color(255, 255, 255));
				proibidoCopiaColar(evt);

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					acesso();
				}
			}
		});
		txtSenha.setBounds(156, 77, 124, 20);
		panel.add(txtSenha);
	}

	private void acesso() {
		HttpURLConnection httpURLConnection = null;

		try {

			URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
			httpURLConnection = (HttpURLConnection) name.openConnection();
			httpURLConnection.connect();

			entrar();
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(ViewAbreChamadoInstalacao.class.getName()).log(Level.ALL, null, e.getCause());
			System.gc();
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new ViewLogin().setVisible(true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(ViewAbreChamadoInstalacao.class.getName()).log(Level.ALL, null, e.getCause());
			System.gc();
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new ViewLogin().setVisible(true);
		} finally {
			httpURLConnection.disconnect();
		}
	}

	private boolean entrar() throws JsonSyntaxException, HeadlessException, SocketException, IOException {
		boolean retorno = false;
		if (!controller.campoVazio(txtLogin, txtSenha)) {
			UsuarioVO usuarioVO = popularDados();
			if (controller.verificarExistenciaLoginEmail(usuarioVO)) {
				if (controller.acessoAD(usuarioVO)) {

					if (controller.checkLogin(usuarioVO)) {
						if (controller.permissaoDaAreaRestrita(usuarioVO)) {

							dispose();

							JOptionPane.showMessageDialog(null, "Acesso Permitido", "Área Restrita",
									JOptionPane.INFORMATION_MESSAGE);
							ViewGerenciamentoUsuario gerenciarTecnico = new ViewGerenciamentoUsuario();
							gerenciarTecnico.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(null,
									"Acesso Negado, você não tem acesso a esta página. Por favor entre em contato com algum administrador",
									"Área Restrita", JOptionPane.ERROR_MESSAGE);

						}
					} else {
						JOptionPane.showMessageDialog(null, "Senha incorreto.", "Information",
								JOptionPane.INFORMATION_MESSAGE);

					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Usuario desativado, Por favor entre em contato com o Desevolvedor", "Information",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else {

				JOptionPane.showMessageDialog(null, "Login incorreto.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return retorno;
	}

	private void proibidoCopiaColar(KeyEvent evt) {
		if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
			if (evt.getKeyCode() == KeyEvent.VK_V) {
				evt.consume();
			}
		} else if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
			if (evt.getKeyCode() == KeyEvent.VK_C) {
				evt.consume();
			}
		}
	}

	private UsuarioVO popularDados() {
		UsuarioVO retorno = new UsuarioVO();

		retorno.setLogin(txtLogin.getText());
		retorno.setSenha(CriptografaSenha.criptografaSenha(new String(txtSenha.getPassword())));

		return retorno;
	}
}
