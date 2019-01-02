package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import util.Constantes;
import util.Log;
import util.LookAndFeel;
import vo.UsuarioVO;

public class ViewMenuChamado extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1610985813407743883L;
	UsuarioVO usuarioVO = new UsuarioVO();
	Gson gSon = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

	private JPanel MenuChamdos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ViewMenuChamado frame = new ViewMenuChamado();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewMenuChamado() {
		setModal(true);
		initCompotent();
	}

	private void initCompotent() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewMenuChamado.class.getResource("/imgs/RSS.png")));
		setTitle("Menu");
		setBounds(100, 100, 268, 303);
		setLocationRelativeTo(null);

		MenuChamdos = new JPanel();
		MenuChamdos.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(MenuChamdos);

		JButton btnInstalao = new JButton("Instala\u00E7\u00E3o");
		btnInstalao.setBounds(35, 71, 149, 38);
		btnInstalao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HttpURLConnection httpURLConnection = null;
				try {
					URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) name.openConnection();
					httpURLConnection.connect();
					ViewAbreChamadoInstalacao viewInstalacao = new ViewAbreChamadoInstalacao();
					dispose();
					viewInstalacao.setVisible(true);

				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());					JOptionPane.showMessageDialog(null,
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
		btnInstalao.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnManunteo = new JButton("Manunten\u00E7\u00E3o");
		btnManunteo.setBounds(35, 150, 149, 38);
		btnManunteo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				HttpURLConnection httpURLConnection = null;

				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					ViewAbreChamadoManutencao chamadosManuntecao = new ViewAbreChamadoManutencao();
					dispose();
					chamadosManuntecao.setVisible(true);

				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());					JOptionPane.showMessageDialog(null,
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
		btnManunteo.setFont(new Font("Arial", Font.PLAIN, 18));
		MenuChamdos.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(15, 11, 237, 252);

		MenuChamdos.add(panel);
		panel.setLayout(null);
		panel.add(btnInstalao);
		panel.add(btnManunteo);
	}
}
