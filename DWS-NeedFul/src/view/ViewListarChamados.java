package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import controller.ChamadoController;
import tabelModel.ChamadosModel;
import util.Constantes;
import util.Log;
import util.LookAndFeel;
import validator.DocumentoConsultaChamado;
import validator.Mascara;
import vo.ChamadosVO;
import vo.ClientVO;

public class ViewListarChamados extends JDialog {

	static ChamadoController controller = new ChamadoController();
	ChamadosVO chamadosVO = new ChamadosVO();
	ChamadosModel clienteModel = new ChamadosModel();
	Mascara masc = new Mascara();

	private static final long serialVersionUID = 1L;
	private JPanel TodosChamados;
	public static JTable tblChamados;
	private static JFormattedTextField ftxtConsulta;
	private JButton btnConsulta;
	private static JComboBox<Object> cbChamado;
	private static JDateChooser dcCaledario;
	private Thread tabela = new Thread(atualizarJtable);
	private Thread combox = new Thread(popularDadosJCombox);

	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewListarChamados frame = new ViewListarChamados();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// http://mauda.com.br/?p=1420
	public ViewListarChamados() {
		initComponent();
		tabela.start();
		combox.start();

	}

	private void initComponent() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewListarChamados.class.getResource("/imgs/RSS.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 650);

		TodosChamados = new JPanel();
		TodosChamados.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(TodosChamados);
		setResizable(false);
		setTitle("Todos os Chamados");
		setLocationRelativeTo(null);
		TodosChamados.setLayout(null);

		JPanel Chamados = new JPanel();
		Chamados.setBounds(10, 51, 974, 560);
		TodosChamados.add(Chamados);
		Chamados.setBorder(BorderFactory.createEtchedBorder());
		Chamados.setLayout(null);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (tblChamados.getSelectedRow() != -1) {

					ChamadosVO vo = new ChamadosVO();
					vo.setID(Integer.parseInt(tblChamados.getValueAt(tblChamados.getSelectedRow(), 0).toString()));
					String retorno = null;
					HttpURLConnection httpURLConnection = null;
					try {
						URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
						httpURLConnection = (HttpURLConnection) url.openConnection();
						httpURLConnection.connect();

						retorno = controller.pesquisaTipoDeChamado(vo);

						if (retorno.equals(Constantes.getTipoChamadoInstalacao())) {
							ViewEdicaoInstalacao viewEdicaoInstalacao = new ViewEdicaoInstalacao(vo);
							viewEdicaoInstalacao.setVisible(true);

						} else {
							if (retorno.equals(Constantes.getTipoChamadoManuntecao())) {
								ViewEdicaoManuntecao viewEdicaoManuntecao = new ViewEdicaoManuntecao(vo);
								viewEdicaoManuntecao.setVisible(true);
							}
						}
					} catch (SocketException e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} catch (Exception e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} finally {
						httpURLConnection.disconnect();
					}
				} else {
					JOptionPane.showMessageDialog(null, "selecioner um Chamado para Abrir", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEditar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEditar.setBounds(222, 515, 153, 23);
		Chamados.add(btnEditar);

		JButton btnFinalizarChamado = new JButton("Finalizar Chamado");
		btnFinalizarChamado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (tblChamados.getSelectedRow() != -1) {
					ChamadosVO vo = new ChamadosVO();
					vo.setID(Integer.parseInt(tblChamados.getValueAt(tblChamados.getSelectedRow(), 0).toString()));
					HttpURLConnection httpURLConnection = null;

					try {
						URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
						httpURLConnection = (HttpURLConnection) url.openConnection();
						httpURLConnection.connect();
						vo = controller.carregarTelaEdicaoInstalacao(vo);
						if (vo.getStatusVO().getTipo().equals(Constantes.getFinalizado())) {
							Log.logDebug("Tentativa de finalizar um chamado, que ja foi finaliza", getClass());
							JOptionPane.showMessageDialog(null, "O Chamado ja foi finalizado", getTitle(),
									JOptionPane.INFORMATION_MESSAGE);
						} else if (vo.getStatusVO().getTipo().equals(Constantes.getCancelado())) {
							Log.logDebug("Tentativa de finalizar um chamado, que foi cancelado", getClass());

							JOptionPane.showMessageDialog(null,
									"Não foi possivel finaliza o chamado, pois foi cancelado,", getTitle(),
									JOptionPane.INFORMATION_MESSAGE);
						} else if (vo.getStatusVO().getTipo().equals(Constantes.getBloqueado())) {
							Log.logDebug("Tentativa de finalizar um chamado, que está bloqueado", getClass());
							JOptionPane.showMessageDialog(null,
									"Não foi possivel finaliza o chamado, pois está bloqueado.\nPor Favor desbloquei-o para pode finalizá-lo",
									getTitle(), JOptionPane.INFORMATION_MESSAGE);
						} else {
							if (controller.fecharChamado(vo)) {
								consultar();
								JOptionPane.showMessageDialog(null, "Chamado Finalizado com Sucesso",
										"Todos os Chamados", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "Não foi Possivel finalizar o Chamado",
										"Todos os Chamados", JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (SocketException e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} catch (IOException e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} finally {
						httpURLConnection.disconnect();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Selecione um Chamado para Finalizá-lo", "Error",
							JOptionPane.QUESTION_MESSAGE);
				}
			}
		});
		btnFinalizarChamado.setFont(new Font("Arial", Font.PLAIN, 15));
		btnFinalizarChamado.setBounds(597, 515, 183, 23);
		Chamados.add(btnFinalizarChamado);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 95, 953, 409);
		Chamados.add(scrollPane);

		tblChamados = new JTable();
		tblChamados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() > 1) {

					ChamadosVO vo = new ChamadosVO();
					vo.setID(Integer.parseInt(tblChamados.getValueAt(tblChamados.getSelectedRow(), 0).toString()));

					String retorno = null;

					HttpURLConnection httpURLConnection = null;
					try {
						URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
						httpURLConnection = (HttpURLConnection) url.openConnection();
						httpURLConnection.connect();

						retorno = controller.pesquisaTipoDeChamado(vo);

						if (retorno.equals(Constantes.getTipoChamadoInstalacao())) {
							ViewEdicaoInstalacao viewEdicaoInstalacao = new ViewEdicaoInstalacao(vo);
							viewEdicaoInstalacao.setVisible(true);

						} else {
							if (retorno.equals(Constantes.getTipoChamadoManuntecao())) {
								ViewEdicaoManuntecao viewEdicaoManuntecao = new ViewEdicaoManuntecao(vo);
								viewEdicaoManuntecao.setVisible(true);
							}
						}
					} catch (SocketException e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} catch (Exception e) {
						Log.logFatal(e.getMessage(), e.getCause(), getClass());
						Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} finally {
						httpURLConnection.disconnect();
					}

				}

			}

		});
		tblChamados.setFont(new Font("Arial", Font.PLAIN, 15));
		tblChamados.setModel(clienteModel);
		tblChamados.getTableHeader().setReorderingAllowed(false);
		taamanho_celula_Jtable();
		tblChamados.setRowSorter(new TableRowSorter<>(clienteModel));
		scrollPane.setViewportView(tblChamados);

		JLabel lblConsulta = new JLabel("Consulta: ");
		lblConsulta.setFont(new Font("Arial", Font.PLAIN, 15));
		lblConsulta.setBounds(11, 11, 72, 20);
		Chamados.add(lblConsulta);

		ftxtConsulta = new JFormattedTextField();
		ftxtConsulta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				consultar();

			}
		});
		ftxtConsulta.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtConsulta.setBounds(90, 11, 285, 20);
		ftxtConsulta.setDocument(new DocumentoConsultaChamado());
		Chamados.add(ftxtConsulta);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 82, 974, 2);
		Chamados.add(separator_1);

		btnConsulta = new JButton("Consulta");
		btnConsulta.setFont(new Font("Arial", Font.PLAIN, 15));
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				consultar();
			}
		});
		btnConsulta.setBounds(402, 11, 91, 23);
		Chamados.add(btnConsulta);

		JLabel lblNewLabel = new JLabel("Tipo de Chamada: ");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(222, 50, 132, 20);
		Chamados.add(lblNewLabel);

		dcCaledario = new JDateChooser();
		dcCaledario.setDateFormatString("dd/MM/yyyy");
		dcCaledario.setFont(new Font("Arial", Font.PLAIN, 15));
		dcCaledario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					System.out.println("Data");
					consultar();

				}
			}
		});

		dcCaledario.setBounds(90, 51, 119, 20);
		Chamados.add(dcCaledario);

		cbChamado = new JComboBox<Object>();
		cbChamado.setFont(new Font("Arial", Font.PLAIN, 15));
		cbChamado.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					System.out.println(cbChamado.getSelectedIndex());
					if (cbChamado.getSelectedIndex() > -1) {
						consultar();
					}
				}

			}
		});
		cbChamado.setBounds(364, 51, 129, 20);
		cbChamado.addItem("");
		cbChamado.setSelectedIndex(0);
		Chamados.add(cbChamado);

		JLabel lblData = new JLabel("Data: ");
		lblData.setFont(new Font("Arial", Font.PLAIN, 15));
		lblData.setBounds(11, 51, 46, 20);
		Chamados.add(lblData);

		JLabel lblTitulo = new JLabel("Todos os Chamados");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 974, 30);
		TodosChamados.add(lblTitulo);
		lblTitulo.setFont(new Font("Arial", Font.PLAIN, 18));
	}

	private static void popularJCombox() throws IOException {

		for (ChamadosVO chamado : controller.carregarTipoChamado()) {
			cbChamado.addItem(chamado.getTipo());
		}

	}

	private void taamanho_celula_Jtable() {
		tblChamados.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblChamados.getColumnModel().getColumn(0).setMinWidth(35);
		tblChamados.getColumnModel().getColumn(0).setMaxWidth(50);

		tblChamados.getColumnModel().getColumn(1).setMinWidth(180);
		tblChamados.getColumnModel().getColumn(1).setPreferredWidth(180);

		tblChamados.getColumnModel().getColumn(2).setPreferredWidth(85);
		tblChamados.getColumnModel().getColumn(2).setMaxWidth(90);
		tblChamados.getColumnModel().getColumn(2).setMinWidth(90);

		tblChamados.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblChamados.getColumnModel().getColumn(3).setMinWidth(120);
		tblChamados.getColumnModel().getColumn(3).setMaxWidth(123);

		tblChamados.getColumnModel().getColumn(5).setPreferredWidth(24);

		tblChamados.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblChamados.getColumnModel().getColumn(6).setMinWidth(100);
		tblChamados.getColumnModel().getColumn(6).setMaxWidth(123);
	}

	public static void consultar() {
		ChamadosModel modelo = (ChamadosModel) tblChamados.getModel();
		ChamadosVO vo = poupulaChamadoVO();
		HttpURLConnection httpURLConnection = null;
		try {

			URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
			httpURLConnection = (HttpURLConnection) name.openConnection();
			httpURLConnection.connect();
			Log.logDebug("Ocorreu uma pesquisa de chamados no sistema", ViewListarChamados.class);

			while (tblChamados.getRowCount() > 0)
				modelo.removeRow(0);

			for (ChamadosVO c : controller.pesquisaChamado(vo)) {
				modelo.addRow(c);
			}
		} catch (SocketException e) {
			Log.logFatal(e.getMessage(), e.getCause(), ViewListarChamados.class);
			Log.logInfo("Sessão encerrada forçada pelo sistema", ViewListarChamados.class);
			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.gc();
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new ViewLogin().setVisible(true);
		} catch (IOException e) {
			Log.logFatal(e.getMessage(), e.getCause(), ViewListarChamados.class);
			Log.logInfo("Sessão encerrada forçada pelo sistema", ViewListarChamados.class);
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			System.gc();
			for (Window window : Window.getWindows()) {
				window.dispose();
			}
			new ViewLogin().setVisible(true);
		} finally {
			httpURLConnection.disconnect();

		}

	}

	private static ChamadosVO poupulaChamadoVO() {

		ChamadosVO vo = new ChamadosVO();
		ClientVO clientVO = new ClientVO();

		clientVO.setNome(null);
		vo.setID(0);
		vo.setData(null);
		vo.setTipo(null);

		if (!ftxtConsulta.getText().isEmpty()) {
			int t = ftxtConsulta.getText().length();
			Pattern buscarCliente = Pattern.compile("[a-zA-Z]{" + t + "}");
			Pattern buscarID = Pattern.compile("\\d{" + t + "}");

			if (buscarCliente.matcher(ftxtConsulta.getText().substring(0, t)).matches()) {
				System.out.println(t);
				System.out.println("Client");
				clientVO.setNome(ftxtConsulta.getText());

			} else if (buscarID.matcher(ftxtConsulta.getText().substring(0, t)).matches()) {
				vo.setID(Integer.parseInt(ftxtConsulta.getText()));
			} else {
				clientVO.setId(0);
			}

		}

		vo.setClientVO(clientVO);

		if (cbChamado.getSelectedIndex() > 0) {
			vo.setTipo(String.valueOf(cbChamado.getSelectedItem()));
		}

		if (dcCaledario.getDate() != null) {
			vo.setData(dcCaledario.getDate());
		}
		return vo;
	}

	private static Runnable atualizarJtable = new Runnable() {
		public void run() {
			while (true) {

				consultar();
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
				}
			}
		}
	};
	private static Runnable popularDadosJCombox = new Runnable() {
		public void run() {
			while (true) {

				try {
					popularJCombox();
					Thread.sleep(90000000);
				} catch (Exception e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
				}
			}
		}
	};

}
