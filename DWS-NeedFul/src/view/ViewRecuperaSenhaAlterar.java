package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioController;
import util.Constantes;
import util.CriptografaSenha;
import util.Log;
import vo.UsuarioVO;

public class ViewRecuperaSenhaAlterar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1323409685387516460L;
	/**
	 * 
	 */

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JPasswordField txtSenha;
	private JPasswordField txtSenhaCf;
	private String cod = "";
	private UsuarioVO usuarioVO = new UsuarioVO();

	public static void main(String[] args) {
		try {
			String codigo = "";
			UsuarioVO vo = new UsuarioVO();
			ViewRecuperaSenhaAlterar dialog = new ViewRecuperaSenhaAlterar(codigo, vo);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewRecuperaSenhaAlterar(String codigo, UsuarioVO vo) {
		setModal(true);
		initComponent();
		cod = codigo;
		usuarioVO = vo;
	}

	private void initComponent() {
		setBounds(100, 100, 450, 223);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);

		JLabel lblCodigoDeSeguraa = new JLabel("Codigo de Segura\u00E7a: ");
		lblCodigoDeSeguraa.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCodigoDeSeguraa.setBounds(10, 26, 141, 18);
		contentPanel.add(lblCodigoDeSeguraa);

		JLabel lblNovaSenha = new JLabel("Nova Senha: ");
		lblNovaSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNovaSenha.setBounds(10, 65, 87, 18);
		contentPanel.add(lblNovaSenha);

		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha:  ");
		lblConfirmarSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblConfirmarSenha.setBounds(10, 102, 123, 18);
		contentPanel.add(lblConfirmarSenha);

		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					txtCodigo.setBackground(new Color(255, 255, 255));
				}
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					validar(cod);
				}
			}
		});
		txtCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		txtCodigo.setBounds(171, 26, 149, 20);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setBackground(Color.LIGHT_GRAY);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				alterarSenha(usuarioVO);
			}
		});
		btnAlterar.setEnabled(false);
		btnAlterar.setBounds(8, 150, 89, 23);
		contentPanel.add(btnAlterar);

		JButton btnValidar = new JButton("Validar");
		btnValidar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (validar(cod)) {
					txtSenha.setEnabled(true);
					txtSenhaCf.setEnabled(true);
					btnAlterar.setEnabled(true);

					txtSenha.setBackground(new Color(255, 255, 255));
					txtSenhaCf.setBackground(new Color(255, 255, 255));

					txtCodigo.setEditable(false);
					btnValidar.setEnabled(false);
				}

			}

		});
		btnValidar.setBounds(335, 25, 89, 23);
		contentPanel.add(btnValidar);

		txtSenha = new JPasswordField();
		txtSenha.setBackground(Color.LIGHT_GRAY);
		txtSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		txtSenha.setEnabled(false);
		txtSenha.setBounds(171, 65, 149, 20);
		contentPanel.add(txtSenha);

		txtSenhaCf = new JPasswordField();
		txtSenhaCf.setBackground(Color.LIGHT_GRAY);
		txtSenhaCf.setFont(new Font("Arial", Font.PLAIN, 15));
		txtSenhaCf.setEnabled(false);
		txtSenhaCf.setBounds(171, 102, 149, 20);
		contentPanel.add(txtSenhaCf);

	}

	public void alterarSenha(UsuarioVO usuarioVO) {

		try {
			HttpURLConnection httpURLConnection = null;

			URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
			httpURLConnection = (HttpURLConnection) name.openConnection();
			httpURLConnection.connect();

			if (new String(txtSenha.getPassword()).equals(new String(txtSenhaCf.getPassword()))) {

				usuarioVO.setSenha(CriptografaSenha.criptografaSenha(new String(txtSenha.getPassword())));

				if (new UsuarioController().alterarSenha(usuarioVO)) {
					Log.logInfo("Usuario : " + usuarioVO.getNome() + " Com Login : " + usuarioVO.getLogin() + " alterou a senha com sucesso!", getClass());
					JOptionPane.showMessageDialog(null, "Senha Alterada com Sucesso", "Alteração de senha",
							JOptionPane.PLAIN_MESSAGE);
					dispose();
				}

			} else {

				JOptionPane.showMessageDialog(null, "As senha não concede.", "Alteração de senha",
						JOptionPane.PLAIN_MESSAGE);
				txtSenhaCf.requestFocus();
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
			Log.logErro(e.getMessage(), e.getCause(), getClass());
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}

	private boolean validar(String codigo) {
		boolean retorno = false;

		if (codigo.equals(txtCodigo.getText())) {
			txtCodigo.setBackground(new Color(51, 255, 102));
			retorno = true;
		} else {
			JOptionPane.showMessageDialog(null, "Codigo invalido!");
			txtCodigo.setBackground(new Color(255, 51, 51));

		}
		return retorno;
	}

}
