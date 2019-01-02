package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.UsuarioController;
import util.Constantes;
import util.CriptografaSenha;
import util.Log;
import util.LookAndFeel;
import validator.MascaraLogin;
import vo.UsuarioVO;

public class ViewLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	UsuarioVO usuarioVO = new UsuarioVO();
	UsuarioController controller = new UsuarioController();
	private HttpURLConnection httpURLConnection = null;

	private JPanel Login;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private UsuarioVO nomeETipo;

	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewLogin frame = new ViewLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewLogin() {
		initCompotent();
	}

	private void initCompotent() {
		setResizable(false);
		setTitle("\u00C0rea de Acesso");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewLogin.class.getResource("/imgs/RSS.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 450);
		Login = new JPanel();
		Login.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Login);
		Login.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 12));
		lblLogin.setBounds(98, 154, 46, 20);
		Login.add(lblLogin);

		JLabel label_1 = new JLabel("Senha: ");
		label_1.setFont(new Font("Arial", Font.PLAIN, 12));
		label_1.setBounds(98, 198, 46, 20);
		Login.add(label_1);

		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				txtLogin.setBackground(new Color(255, 255, 255));
				proibidoCopiaColar(evt);
			}
		});
		txtLogin.setBackground(new Color(255, 255, 255));
		txtLogin.setColumns(10);
		txtLogin.setDocument(new MascaraLogin());
		txtLogin.setBounds(154, 154, 121, 20);
		Login.add(txtLogin);

		txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		txtSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtSenha.setBackground(new Color(255, 255, 255));
				proibidoCopiaColar(evt);

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					entrar();
				}
			}
		});
		txtSenha.setBounds(154, 198, 121, 20);
		Login.add(txtSenha);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEntrar.setHorizontalTextPosition(SwingConstants.LEFT);
		btnEntrar.setHorizontalAlignment(SwingConstants.LEFT);
		btnEntrar.setIcon(new ImageIcon(ViewLogin.class.getResource("/imgs/icons8-enter-2.png")));
		btnEntrar.setBackground(SystemColor.controlHighlight);
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entrar();

			}
		});
		btnEntrar.setBounds(250, 324, 113, 35);
		Login.add(btnEntrar);

		JLabel icon = new JLabel("");
		icon.setHorizontalAlignment(SwingConstants.LEFT);
		icon.setVerticalAlignment(SwingConstants.BOTTOM);
		icon.setIcon(new ImageIcon(ViewLogin.class.getResource("/imgs/LogoDaUSBd.png")));
		icon.setBounds(10, 11, 374, 115);
		Login.add(icon);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 253, 400, 2);
		Login.add(separator);

		JLabel lblRecuperaSenha = new JLabel("Problemas com login ? ");
		lblRecuperaSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecuperaSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		lblRecuperaSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lblRecuperaSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {

				try {

					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();

					ViewProblemaComLogin problemaComLogin = new ViewProblemaComLogin();
					problemaComLogin.setVisible(true);
				} catch (SocketException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);

				} catch (IOException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		lblRecuperaSenha.setForeground(Color.BLUE);
		lblRecuperaSenha.setBounds(10, 256, 175, 14);
		Login.add(lblRecuperaSenha);

	}

	private void entrar() {

		if (!controller.campoVazio(txtLogin, txtSenha)) {

			try {
				if (controller.verificarExistenciaLoginEmail(popularLogin())) {

					if (controller.acessoAD(usuarioVO)) {
						if (controller.checkLogin(popularLogin())) {
							nomeETipo = controller.permissaoDeLogin(popularLogin());
							saudacao();

						} else {
							Log.logInfo(
									"Usuario : " + usuarioVO.getLogin() + " tentou entrar no sistema mas erro a senha",
									getClass());

							JOptionPane.showMessageDialog(null, "Senha incorreta....", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						Log.logInfo("Usuario desativado : " + usuarioVO.getLogin() + " tentou entrar no sistema",
								getClass());
						JOptionPane.showMessageDialog(null,
								"Usuario desativado, Por favor entre em contato com o Administrador", "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					Log.logInfo("Tentativa de entra no sistema com o Login: " + usuarioVO.getLogin(), getClass());
					JOptionPane.showMessageDialog(null, "Login Incorretos", "Login Não Efetuado",
							JOptionPane.ERROR_MESSAGE);

				}

			} catch (SocketException e) {
				Log.logErro(e.getMessage(), e.getCause(), getClass());
				JOptionPane.showMessageDialog(null,
						"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
						JOptionPane.ERROR_MESSAGE);

			} catch (IOException e) {
				Log.logErro(e.getMessage(), e.getCause(), getClass());
				JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

			}

		}

	}

	private UsuarioVO popularLogin() {
		usuarioVO.setLogin(txtLogin.getText());
		usuarioVO.setSenha(CriptografaSenha.criptografaSenha(new String(txtSenha.getPassword())));
		return usuarioVO;
	}

	@SuppressWarnings("deprecation")
	private void saudacao() {
		ViewPrincipal viewPrincipal = new ViewPrincipal();
		Calendar calendario = Calendar.getInstance();

		int horas = calendario.getTime().getHours();
		String dia = "";
		if (horas >= 6 && horas <= 12) {
			dia = "Bom Dia ";
		} else if (horas >= 12 && horas <= 18) {
			dia = "Boa Tarde ";
		} else {
			dia = "Boa Noite ";
		}

		if (nomeETipo.getTipo().equals("Desenvolvedor")) {
			Log.logDebug("Usuario : " + usuarioVO.getLogin() + " entrou no sistema", ViewLogin.class);

			viewPrincipal.setVisible(true);
			viewPrincipal.setLocationRelativeTo(null);
			dispose();
			JOptionPane.showMessageDialog(null,
					dia + "Sr. Desenvolvedor " + nomeETipo.getNome() + ", Ao Sistema NeedFul", "Super Login Efetuado",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (nomeETipo.getTipo().equals("Administrador")) {
			Log.logDebug("Usuario : " + usuarioVO.getLogin() + " entrou no sistema", ViewLogin.class);

			viewPrincipal.setVisible(true);
			viewPrincipal.setLocationRelativeTo(null);
			dispose();
			JOptionPane.showMessageDialog(null,
					dia + "Sr(a). Administrador(a) " + nomeETipo.getNome() + ", Ao Sistema NeedFul", "Login Efetuado",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (nomeETipo.getTipo().equals("Atendente")) {
			Log.logDebug("Usuario : " + usuarioVO.getLogin() + " entrou no sistema", ViewLogin.class);

			viewPrincipal.setVisible(true);
			viewPrincipal.setLocationRelativeTo(null);
			dispose();
			JOptionPane.showMessageDialog(null,
					dia + "Sr(a). Atendente " + nomeETipo.getNome() + ", Ao Sistema NeedFul", "Login Efetuado",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			Log.logWarn("Usuario: " + nomeETipo.getNome() + " Tentou entra no Sistema mas o acesso foi negado",
					getClass());
			JOptionPane.showMessageDialog(null,
					dia + "Sr. Tecnico " + nomeETipo.getNome() + ", você não tem acesso ao Sistema NeedFul Desktop",
					"Acesso Negado", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void proibidoCopiaColar(KeyEvent evt) {
		if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
			if (evt.getKeyCode() == KeyEvent.VK_V) {
				Log.logWarn("Tentativa de Colar", getClass());
				evt.consume();
			}
		} else if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
			if (evt.getKeyCode() == KeyEvent.VK_C) {
				Log.logWarn("Tentativa de Copiar", getClass());
				evt.consume();
			}
		}
	}

}
