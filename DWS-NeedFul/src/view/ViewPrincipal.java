package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import util.Constantes;
import util.GraficoPrincipal;
import util.Log;
import util.LookAndFeel;;

public class ViewPrincipal extends JFrame {

	private static JPanel panel_2;
	private static final long serialVersionUID = 1L;
	private HttpURLConnection httpURLConnection = null;
	private JMenu mnRelatorio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPrincipal frame = new ViewPrincipal();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewPrincipal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent evt) {
				Log.logWarn("Usuario fechou o Software, sem encerrar a sessão", getClass());
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int intOpcao = JOptionPane.showOptionDialog(null, "Desejar fechar o Programa?", getTitle(),
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (intOpcao == 0) {
					Log.logWarn("Usuario fechou o Software, sem encerrar a sessão", getClass());
					System.exit(0);
				} else
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		});
		initCompotent();
		setLocationRelativeTo(null);
	}

	private void initCompotent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(SystemColor.controlHighlight);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewPrincipal.class.getResource("/imgs/RSS.png")));
		setTitle("NeedFul");
		setLocationRelativeTo(null);
		setBounds(100, 100, 1000, 712);

		JPanel panel = new JPanel();

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(2)));
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 649, Short.MAX_VALUE).addContainerGap()));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("Button.disabledForeground"));

		try {
			//panel_2 = new JPanel();
			 panel_2 = GraficoPrincipal.Panel();
		} catch (Exception e1) {
			Log.logErro("Não foi possivel gerar o Grafico de Chamados com o erro : " + e1.getMessage(), e1.getCause(),
					getClass());
			panel_2 = new JPanel();
		}

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(UIManager.getColor("Button.disabledForeground"));

		JSeparator separator = new JSeparator();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup().addGap(18)
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE))))
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel
				.createSequentialGroup()
				.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE).addGap(5)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup().addGap(77)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE).addGap(125))
						.addGroup(gl_panel.createSequentialGroup().addGap(53)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()))));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING).addGap(0, 587, Short.MAX_VALUE));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 416, Short.MAX_VALUE));

		panel_2.setLayout(gl_panel_2);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ViewPrincipal.class.getResource("/imgs/LogoDaUSBd.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblSistemaDesenvolvidoPelo = new JLabel("Sistema Desenvolvido Pelo Grupo NeedFul");
		lblSistemaDesenvolvidoPelo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSistemaDesenvolvidoPelo.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel label_1 = new JLabel("Etec Jardim \u00C2ngela");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				gl_panel_3.createSequentialGroup().addContainerGap().addGroup(gl_panel_3
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSistemaDesenvolvidoPelo, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
						.addGroup(gl_panel_3.createSequentialGroup().addGap(63)
								.addComponent(label_1, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE).addGap(71)))
						.addGap(79).addComponent(lblNewLabel).addContainerGap()));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING).addGroup(gl_panel_3
				.createSequentialGroup().addContainerGap(83, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblSistemaDesenvolvidoPelo, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
				.addGap(11).addComponent(label_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
				.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup().addComponent(lblNewLabel)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_3.setLayout(gl_panel_3);

		JButton btnEstoque = new JButton("Estoque ");
		btnEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					ViewEstoque estoque = new ViewEstoque();
					estoque.setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} finally {
					httpURLConnection.disconnect();

				}
			}
		});
		btnEstoque.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnControleDeAcesso = new JButton("Controle de Acesso");
		btnControleDeAcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					ViewAuteticado auteticado = new ViewAuteticado(this, true);
					auteticado.setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} finally {
					httpURLConnection.disconnect();

				}
			}
		});
		btnControleDeAcesso.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnAbrirChamados = new JButton("Abrir Chamados");
		btnAbrirChamados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewMenuChamado menuChamado = new ViewMenuChamado();

				menuChamado.setVisible(true);
				menuChamado.setLocationRelativeTo(null);

			}
		});
		btnAbrirChamados.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnListarChamados = new JButton("Listar Chamados");
		btnListarChamados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();

					ViewListarChamados listar = new ViewListarChamados();
					listar.setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} finally {
					httpURLConnection.disconnect();

				}

			}
		});
		btnListarChamados.setFont(new Font("Arial", Font.PLAIN, 18));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnControleDeAcesso, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(btnEstoque, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnListarChamados, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(btnAbrirChamados, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addContainerGap(38, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addGap(61)
				.addComponent(btnAbrirChamados, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(btnListarChamados, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(btnEstoque, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE).addGap(18)
				.addComponent(btnControleDeAcesso, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(61, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("Chamados");
		menu.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("Abrir Chamado");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ViewMenuChamado menuChamado = new ViewMenuChamado();

				menuChamado.setVisible(true);
				menuChamado.setLocationRelativeTo(null);

			}
		});
		menuItem.setFont(new Font("Arial", Font.PLAIN, 13));
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("Listar Chamados");
		menuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();

					ViewListarChamados listar = new ViewListarChamados();
					listar.setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} finally {
					httpURLConnection.disconnect();

				}
			}
		});
		menuItem_1.setFont(new Font("Arial", Font.PLAIN, 13));
		menu.add(menuItem_1);

		JMenu menu_1 = new JMenu("Estoque");
		menu_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					ViewEstoque estoque = new ViewEstoque();
					estoque.setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} finally {
					httpURLConnection.disconnect();

				}
			}
		});
		menu_1.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(menu_1);

		mnRelatorio = new JMenu("Relatorio");
		mnRelatorio.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				ViewMenuRelatorio menuRelatorio = new ViewMenuRelatorio();
				menuRelatorio.setVisible(true);

			}
		});
		mnRelatorio.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnRelatorio);

		JMenu menu_3 = new JMenu("Sobre");

		menu_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
				sobre.setLocationRelativeTo(null);

			}
		});
		menu_3.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(menu_3);

		JMenu menu_4 = new JMenu("Sair");
		menu_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				Object[] options = { "Confirmar", "Cancelar" };
				int Confirm = JOptionPane.showOptionDialog(null, "Clique Confirmar para Sair", "Informação",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (Confirm == 0) {
					Log.logInfo("Sessão encerrada", getClass());
					JOptionPane.showMessageDialog(null, "Finalizado sessão", "NeedFul", JOptionPane.PLAIN_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				}
			}
		});
		menu_4.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(menu_4);

	}

}
