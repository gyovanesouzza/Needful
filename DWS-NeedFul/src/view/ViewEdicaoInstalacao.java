package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonSyntaxException;
import com.toedter.calendar.JDateChooser;

import controller.ChamadoController;
import controller.TecnicoController;
import util.Constantes;
import util.Log;
import util.LookAndFeel;
import validator.DocumentoHorasAgendamento;
import validator.Mascara;
import vo.ChamadosVO;
import vo.ClientVO;
import vo.StatusVO;
import vo.TecnicoVO;

public class ViewEdicaoInstalacao extends JDialog {

	private static final long serialVersionUID = 2837491791444305125L;

	ChamadoController chamadoController = new ChamadoController();
	TecnicoController tecnicoController = new TecnicoController();

	ChamadosVO chamadosVO = new ChamadosVO();
	Mascara masc = new Mascara();

	private JPanel AbrirChamadosInstalacao;
	private JFormattedTextField ftxtTelefone;
	private JFormattedTextField ftxtCliente;
	private JFormattedTextField ftxtRoteador;
	private JFormattedTextField ftxtAgendamentoHoras;
	private JTextField txtEndereco;
	private JTextPane txtpDescricao;
	private JFormattedTextField ftxtCel;
	private JFormattedTextField ftxtCep;
	private JFormattedTextField ftxtData;
	private JFormattedTextField ftxtHoras;
	private JLabel lblCliente;
	private JButton btnCadastrar;
	private JButton btnProcura;
	private JButton btnJustificativa;
	private JComboBox<String> cbTecnico;
	private JComboBox<String> cbStatus;
	private JFormattedTextField ftxtCodigo;
	private JDateChooser dcDataInstalacao;
	private HttpURLConnection httpURLConnection = null;
	private JTextField ftxtCabo;
	private int frag = 0;
	SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

	public static void main(String[] args) {
		LookAndFeel.Layout();
		try {
			ChamadosVO c = new ChamadosVO();
			ViewEdicaoInstalacao dialog = new ViewEdicaoInstalacao(c);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewEdicaoInstalacao(ChamadosVO vo)
			throws JsonSyntaxException, SocketException, IOException, ParseException {
		initComponent();
		daosNoCampo(vo);
		autalizarFechar();
	}

	private void initComponent() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewEdicaoInstalacao.class.getResource("/imgs/RSS.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Abrir Chamado");
		setBounds(100, 100, 510, 728);
		setModal(true);
		setResizable(false);

		AbrirChamadosInstalacao = new JPanel();
		AbrirChamadosInstalacao.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(AbrirChamadosInstalacao);
		AbrirChamadosInstalacao.setLayout(null);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setBounds(10, 51, 484, 637);
		AbrirChamadosInstalacao.add(panel);
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(null);

		JLabel lblAbrirChamado = new JLabel("Alterar Chamado de Instala\u00E7\u00E3o");
		lblAbrirChamado.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbrirChamado.setFont(new Font("Arial", Font.PLAIN, 18));
		lblAbrirChamado.setBounds(10, 11, 484, 29);
		AbrirChamadosInstalacao.add(lblAbrirChamado);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 15));
		lblData.setBounds(16, 48, 57, 20);
		panel.add(lblData);

		JLabel lblHora = new JLabel("\u00E0s");
		lblHora.setFont(new Font("Arial", Font.PLAIN, 15));
		lblHora.setBounds(219, 48, 22, 18);
		panel.add(lblHora);

		JLabel lblTelefone = new JLabel("Telefone: ");
		lblTelefone.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefone.setBounds(16, 122, 80, 20);
		panel.add(lblTelefone);

		ftxtTelefone = new JFormattedTextField();
		ftxtTelefone.setEditable(false);
		ftxtTelefone.setBackground(Color.LIGHT_GRAY);
		ftxtTelefone.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtTelefone.setBounds(123, 122, 126, 20);
		panel.add(ftxtTelefone);
		ftxtTelefone.setColumns(10);

		lblCliente = new JLabel("Cliente: ");
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCliente.setBounds(16, 85, 80, 20);
		panel.add(lblCliente);

		ftxtCliente = new JFormattedTextField();
		ftxtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				ftxtCliente.setBackground(new Color(255, 255, 255));
				ftxtTelefone.setText("");

				ftxtCel.setText("");
				txtEndereco.setText("");
				ftxtCep.setText("");
				ftxtRoteador.setText("");
				ftxtCabo.setText("");
				frag = 1;
			}
		});
		ftxtCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCliente.setBounds(123, 85, 254, 20);
		ftxtCliente.grabFocus();
		panel.add(ftxtCliente);
		ftxtCliente.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o: ");
		lblEndereo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEndereo.setBounds(16, 196, 80, 20);
		panel.add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setEditable(false);
		txtEndereco.setBackground(Color.LIGHT_GRAY);
		txtEndereco.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(123, 196, 254, 20);
		panel.add(txtEndereco);

		JLabel lblDescrio = new JLabel("Observa\u00E7\u00F5es: ");
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDescrio.setBounds(16, 465, 96, 20);
		panel.add(lblDescrio);

		txtpDescricao = new JTextPane();
		txtpDescricao.setFont(new Font("Arial", Font.PLAIN, 15));
		txtpDescricao.setBounds(123, 465, 250, 106);
		panel.add(txtpDescricao);

		btnCadastrar = new JButton("Alterar");
		btnCadastrar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (frag != 1) {
					Object[] options = { "Confirmar", "Cancelar" };
					int opc = -1;
					if (btnCadastrar.getText().equals("Desbloquear")) {
						opc = JOptionPane.showOptionDialog(null, "Clique Confirmar para Desbloquear o chamado",
								"Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
								options[0]);
					} else {
						opc = JOptionPane.showOptionDialog(null, "Clique Confirmar para Alterar o chamado",
								"Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
								options[0]);
					}
					if (opc == 0) {
						try {
							URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
							httpURLConnection = (HttpURLConnection) name.openConnection();
							httpURLConnection.connect();
							if (!naoPermiteEdicao()) {
								if (btnCadastrar.getText().equals("Desbloquear")) {
									if (desbloquearChamado()) {
										Log.logInfo("O Chamado : " + chamadosVO.getID() + " foi desbloqueado",
												getClass());
										JOptionPane.showMessageDialog(null, "Chamado Desbloqueado com Sucesso! ",
												"Chamado Instalação", JOptionPane.PLAIN_MESSAGE);
										dispose();
									} else {
										JOptionPane.showMessageDialog(null, "Alteração não efetuada");

									}
								} else {
									if (!justificativa()) {
										if (agendamento()) {
											if (dataHorarioAgend()) {

												if (alterarChamado()) {
													Log.logInfo("O chamado : " + chamadosVO.getID() + " foi alterado", getClass());
													JOptionPane.showMessageDialog(null,
															"Chamado Alterado com Sucesso! ", "Chamado Instalação",
															JOptionPane.PLAIN_MESSAGE);
													dispose();
												} else {
													JOptionPane.showMessageDialog(null, "Alteração não efetuada");

												}
											} else {
												Log.logInfo("Tentativa de Alterar o chamado : " + chamadosVO.getID() + " com um hora abaixo do horario de abertura do chamado", getClass());

												JOptionPane.showMessageDialog(null,
														"Por favor informe uma hora acima do horário atual", "Erro",
														JOptionPane.ERROR_MESSAGE);
											}
										} else {
											Log.logInfo("Tentativa de Alterar o chamado : " + chamadosVO.getID() + " com uma data de agendamento menor que a data de abertura do chamado", getClass());
											JOptionPane.showMessageDialog(null,
													"A data de Agendamento não pode se menor que a data atual");
										}
									} else {
										Log.logWarn("Chamado : " + chamadosVO.getID() + " não foi justificado.", getClass());
										JOptionPane.showMessageDialog(null, "Por favor Justifique o chamado");
									}
								}
							} else {
								JOptionPane.showMessageDialog(null,
										"Não foi possivel realizar a alteração. Este Chamado ja foi finalizado");
							}
						} catch (SocketTimeoutException e) {
							JOptionPane.showMessageDialog(null, "Alteração não efetuada, Conexão lenta", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							System.gc();

						} catch (SocketException e) {
							Log.logFatal(e.getMessage(), e.getCause(), getClass());
							Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
							JOptionPane.showMessageDialog(null,
									"Alteração não efetuada, Por favor verifique sua Internet", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							System.gc();
							for (Window window : Window.getWindows()) {
								window.dispose();
							}
							new ViewLogin().setVisible(true);
						} catch (Exception e) {
							Log.logFatal(e.getMessage(), e.getCause(), getClass());
							Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

							JOptionPane.showMessageDialog(null, "Error: " + e.getMessage() + " " + e.getCause(),
									"ERROR", JOptionPane.ERROR_MESSAGE);

							System.gc();
							for (Window window : Window.getWindows()) {
								window.dispose();
							}
							new ViewLogin().setVisible(true);

						} finally {
							httpURLConnection.disconnect();
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "Por Favor selecione um cliente", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					ftxtCliente.setBackground(new Color(255, 102, 102));
					ftxtCliente.requestFocus();
				}
			}

			private boolean agendamento() {
				SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");

				try {
					Date now = sdfData.parse(ftxtData.getText());
					Date aux = dcDataInstalacao.getDate();
					if (aux.before(now)) {
						return false;
					}
				} catch (ParseException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
				}
				return true;
			}
		});
		btnCadastrar.setHorizontalTextPosition(SwingConstants.LEFT);
		btnCadastrar.setBounds(347, 586, 117, 29);
		panel.add(btnCadastrar);

		ftxtData = new JFormattedTextField();
		ftxtData.setBackground(Color.LIGHT_GRAY);
		ftxtData.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtData.setEditable(false);
		ftxtData.setBounds(123, 48, 86, 20);
		panel.add(ftxtData);

		ftxtHoras = new JFormattedTextField();
		ftxtHoras.setBackground(Color.LIGHT_GRAY);
		ftxtHoras.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtHoras.setEditable(false);
		ftxtHoras.setBounds(249, 48, 46, 20);
		panel.add(ftxtHoras);

		ftxtCel = new JFormattedTextField();
		ftxtCel.setEditable(false);
		ftxtCel.setBackground(Color.LIGHT_GRAY);
		ftxtCel.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCel.setColumns(10);
		ftxtCel.setBounds(123, 159, 126, 20);
		panel.add(ftxtCel);

		JLabel lblCel = new JLabel("Cel:");
		lblCel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCel.setBounds(16, 159, 46, 20);
		panel.add(lblCel);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCep.setBounds(16, 233, 57, 20);
		panel.add(lblCep);

		ftxtCep = new JFormattedTextField();
		ftxtCep.setEditable(false);
		ftxtCep.setBackground(Color.LIGHT_GRAY);
		ftxtCep.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCep.setColumns(10);
		ftxtCep.setBounds(123, 233, 126, 20);
		panel.add(ftxtCep);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 265, 464, 2);
		panel.add(separator_1);

		JLabel lblRoteador = new JLabel("Roteador: ");
		lblRoteador.setFont(new Font("Arial", Font.PLAIN, 15));
		lblRoteador.setBounds(16, 391, 96, 20);
		panel.add(lblRoteador);

		JLabel lblCabo = new JLabel("Cabo:");
		lblCabo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCabo.setBounds(16, 354, 57, 20);
		panel.add(lblCabo);

		JLabel lblInstalacao = new JLabel("Agendamento:");
		lblInstalacao.setFont(new Font("Arial", Font.PLAIN, 15));
		lblInstalacao.setBounds(16, 280, 94, 20);
		panel.add(lblInstalacao);

		JLabel lblTecnico = new JLabel("Tecnico: ");
		lblTecnico.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTecnico.setBounds(16, 317, 80, 20);
		panel.add(lblTecnico);

		cbTecnico = new JComboBox<String>();
		cbTecnico.setEditable(true);
		cbTecnico.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTecnico.setBounds(123, 317, 161, 20);
		panel.add(cbTecnico);

		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCodigo.setBounds(16, 11, 54, 20);
		panel.add(lblCodigo);

		ftxtCodigo = new JFormattedTextField();
		ftxtCodigo.setBackground(Color.LIGHT_GRAY);
		ftxtCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCodigo.setEditable(false);
		ftxtCodigo.setBounds(123, 11, 86, 20);
		ftxtCodigo.setColumns(10);
		panel.add(ftxtCodigo);

		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setFont(new Font("Arial", Font.PLAIN, 15));
		lblStatus.setBounds(16, 428, 80, 18);
		panel.add(lblStatus);

		cbStatus = new JComboBox<String>();
		cbStatus.setEditable(true);
		cbStatus.setToolTipText("");
		cbStatus.setFont(new Font("Arial", Font.PLAIN, 15));
		cbStatus.setBounds(123, 427, 126, 20);
		panel.add(cbStatus);

		dcDataInstalacao = new JDateChooser();
		dcDataInstalacao.setDateFormatString("dd/MM/yyyy");
		dcDataInstalacao.setFont(new Font("Arial", Font.PLAIN, 15));
		dcDataInstalacao.getCalendarButton().setFont(new Font("Arial", Font.PLAIN, 15));
		dcDataInstalacao.setBounds(123, 280, 126, 20);
		panel.add(dcDataInstalacao);

		btnProcura = new JButton("Procurar");
		btnProcura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				ClientVO clientVO = new ClientVO();
				clientVO.setNome(ftxtCliente.getText());

				try {
					URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) name.openConnection();
					httpURLConnection.connect();

					ViewCliente viewCliente = new ViewCliente(clientVO);

					viewCliente.setVisible(true);
					frag = viewCliente.fechou;
					if (frag != 1) {
						clientVO = viewCliente.passarDados();

						chamadosVO.setClientVO(clientVO);

						ftxtCliente.setText(clientVO.getNome());
						ftxtCliente.setBackground(new Color(255, 255, 255));
						ftxtTelefone.setText(clientVO.getTelefone());
						ftxtCel.setText(clientVO.getCelular());
						txtEndereco.setText(
								clientVO.getEnderecoVO().getRua() + ", " + clientVO.getEnderecoVO().getNumero());
						ftxtCep.setText(clientVO.getEnderecoVO().getCep());
						ftxtCabo.setText(clientVO.getTipodeCabo());
						ftxtRoteador.setText(clientVO.getRoteador());
					}
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
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
		btnProcura.setFont(new Font("Arial", Font.PLAIN, 15));
		btnProcura.setBounds(385, 85, 89, 20);
		panel.add(btnProcura);

		ftxtRoteador = new JFormattedTextField();
		ftxtRoteador.setEditable(false);
		ftxtRoteador.setBackground(Color.LIGHT_GRAY);
		ftxtRoteador.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtRoteador.setColumns(10);
		ftxtRoteador.setBounds(123, 392, 126, 20);
		panel.add(ftxtRoteador);

		JLabel label = new JLabel("\u00E0s");
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		label.setBounds(262, 280, 22, 18);
		panel.add(label);

		ftxtAgendamentoHoras = new JFormattedTextField();
		ftxtAgendamentoHoras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if (!ftxtAgendamentoHoras.getText().isEmpty() && ftxtAgendamentoHoras.getText().length() == 5) {
					validarHoraAgendamento();
				}
				ftxtAgendamentoHoras.setBackground(new Color(255, 255, 255));
			}
		});
		ftxtAgendamentoHoras.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtAgendamentoHoras.setBounds(292, 280, 46, 20);
		ftxtAgendamentoHoras.setDocument(new DocumentoHorasAgendamento());
		panel.add(ftxtAgendamentoHoras);

		ftxtCabo = new JTextField();
		ftxtCabo.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCabo.setBackground(Color.LIGHT_GRAY);
		ftxtCabo.setEditable(false);
		ftxtCabo.setBounds(123, 355, 126, 20);
		panel.add(ftxtCabo);
		ftxtCabo.setColumns(10);

		btnJustificativa = new JButton("Justificativa");
		btnJustificativa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JOptionPane.showMessageDialog(null, chamadosVO.getJustificativa(), "Justificativa do chamado",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnJustificativa.setBounds(259, 427, 89, 20);
		btnJustificativa.setVisible(false);
		panel.add(btnJustificativa);

	}

	public boolean alterarChamado() throws IOException, ParseException {

		chamadosVO.setID(Integer.parseInt(ftxtCodigo.getText()));
		chamadosVO.setAgendamento_Data(dcDataInstalacao.getDate());
		chamadosVO.setAgendamento_horas(
				new Time(new SimpleDateFormat("HH:mm").parse(ftxtAgendamentoHoras.getText()).getTime()));
		TecnicoVO tecnicoVO = new TecnicoVO();
		tecnicoVO.setTecnico(cbTecnico.getSelectedItem().toString());
		StatusVO statusVO = new StatusVO();
		statusVO.setTipo(cbStatus.getSelectedItem().toString());
		chamadosVO.setTecnicoVO(tecnicoVO);
		chamadosVO.setStatusVO(statusVO);
		chamadosVO.setDescricao(txtpDescricao.getText());
		chamadosVO.setTipo(Constantes.getTipoChamadoInstalacao());
		return chamadoController.alterarChamadoInstalacao(chamadosVO);

	}

	public boolean desbloquearChamado() throws IOException, ParseException {
		StatusVO statusVO = new StatusVO();
		statusVO.setTipo("Aberto");
		chamadosVO.setStatusVO(statusVO);
		chamadosVO.setTipo(Constantes.getTipoChamadoInstalacao());
		return chamadoController.alterarChamadoInstalacao(chamadosVO);

	}

	public void daosNoCampo(ChamadosVO vo) throws JsonSyntaxException, SocketException, IOException, ParseException {

		chamadosVO = chamadoController.carregarTelaEdicaoInstalacao(vo);
		comboBox();
		ftxtCodigo.setValue(vo.getID());
		ftxtCliente.setValue(chamadosVO.getClientVO().getNome());
		ftxtData.setValue(sdfData.format(chamadosVO.getData()));
		ftxtHoras.setValue(sdfHora.format(chamadosVO.getHoras()));
		ftxtTelefone.setValue(chamadosVO.getClientVO().getTelefone());
		ftxtCel.setValue(chamadosVO.getClientVO().getCelular());
		txtEndereco.setText(chamadosVO.getClientVO().getEnderecoVO().getRua() + ", "
				+ chamadosVO.getClientVO().getEnderecoVO().getNumero());
		ftxtCep.setText(chamadosVO.getClientVO().getEnderecoVO().getCep());
		ftxtRoteador.setValue(chamadosVO.getClientVO().getRoteador());
		String aux = sdfData.format(chamadosVO.getAgendamento_Data());
		dcDataInstalacao.setDate(sdfData.parse(aux));
		ftxtCabo.setText(chamadosVO.getClientVO().getTipodeCabo());
		ftxtAgendamentoHoras.setText(new SimpleDateFormat("HH:mm").format(chamadosVO.getAgendamento_horas()));
		cbTecnico.setSelectedItem(String.valueOf(chamadosVO.getTecnicoVO().getTecnico()));
		cbStatus.setSelectedItem(String.valueOf(chamadosVO.getStatusVO().getTipo()));
		if (cbStatus.getSelectedItem().equals("Cancelado") || cbStatus.getSelectedItem().equals("Bloqueado")) {
			btnJustificativa.setVisible(true);
		}
		if (cbStatus.getSelectedItem().equals("Bloqueado")) {
			ftxtCliente.setEditable(false);
			btnProcura.setEnabled(false);
			dcDataInstalacao.setEnabled(false);
			ftxtAgendamentoHoras.setEditable(false);
			cbTecnico.setEnabled(false);
			btnCadastrar.setText("Desbloquear");
			cbStatus.setEnabled(false);
			ftxtCliente.setBackground(Color.LIGHT_GRAY);
			btnProcura.setBackground(Color.LIGHT_GRAY);
			dcDataInstalacao.setBackground(Color.LIGHT_GRAY);
			ftxtAgendamentoHoras.setBackground(Color.LIGHT_GRAY);
			cbTecnico.setBackground(Color.LIGHT_GRAY);
			cbStatus.setBackground(Color.LIGHT_GRAY);
		}
		if (cbStatus.getSelectedItem().equals("Cancelado") || cbStatus.getSelectedItem().equals("Finalizado")) {
			ftxtCliente.setEditable(false);
			btnProcura.setEnabled(false);
			dcDataInstalacao.setEnabled(false);
			ftxtAgendamentoHoras.setEditable(false);
			cbTecnico.setEnabled(false);
			cbStatus.setEnabled(false);
			ftxtCliente.setBackground(Color.LIGHT_GRAY);
			btnProcura.setBackground(Color.LIGHT_GRAY);
			dcDataInstalacao.setBackground(Color.LIGHT_GRAY);
			ftxtAgendamentoHoras.setBackground(Color.LIGHT_GRAY);
			cbTecnico.setBackground(Color.LIGHT_GRAY);
			cbStatus.setBackground(Color.LIGHT_GRAY);
			btnCadastrar.setEnabled(false);
		}
		txtpDescricao.setText(chamadosVO.getDescricao());
		validarData();
	}

	private void validarData() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 180);
		Date limit = cal.getTime();
		dcDataInstalacao.setMinSelectableDate(chamadosVO.getData());
		dcDataInstalacao.setMaxSelectableDate(limit);
	}

	private boolean dataHorarioAgend() {
		try {
			if (dcDataInstalacao.getDate().equals(sdfData.parse(ftxtData.getText()))) {
				Date now = sdfHora.parse(ftxtHoras.getText());
				Date aux = sdfHora.parse(ftxtAgendamentoHoras.getText());
				if (aux.equals(now) || aux.before(now)) {
					return false;
				}
			}

		} catch (ParseException e) {
			Log.logErro(e.getMessage(), e.getCause(), getClass());
			ftxtAgendamentoHoras.setBackground(new Color(255, 102, 102));
			ftxtAgendamentoHoras.requestFocus();
			JOptionPane.showMessageDialog(null, "Por favor informe uma hora acima do horário atual", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private boolean validarHoraAgendamento() {
		try {
			sdfHora.setLenient(false);
			sdfHora.parse(ftxtAgendamentoHoras.getText());
			System.out.println(sdfHora.parse(ftxtAgendamentoHoras.getText()));
		} catch (ParseException e) {
			Log.logErro(e.getMessage(), e.getCause(), getClass());
			JOptionPane.showMessageDialog(null, "Por Favor informe uma Hora valida", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void comboBox() throws JsonSyntaxException, SocketException, IOException {
		for (TecnicoVO tVO : chamadoController.carregarTecnico()) {
			cbTecnico.addItem(String.valueOf(tVO));
		}
		for (StatusVO sVO : chamadoController.carregarStatus(1)) {
			cbStatus.addItem(String.valueOf(sVO));
		}
	}

	private boolean naoPermiteEdicao() {
		if (chamadosVO.getStatusVO().getTipo().trim().equals(Constantes.getFinalizado())) {
			return true;
		}
		return false;
	}

	private boolean justificativa() {
		boolean justi = false;
		chamadosVO.setJustificativa("");
		if (cbStatus.getSelectedItem().equals("Bloqueado") || cbStatus.getSelectedItem().equals("Cancelado")) {
			justi = true;
			if (cbStatus.getSelectedItem().equals("Cancelado")) {
				chamadosVO.setJustificativa(JOptionPane.showInputDialog(null, "Justifique o cancelamento do chamado"));
				if (chamadosVO.getJustificativa() != null) {
					if (chamadosVO.getJustificativa().trim().equals("")) {
						chamadosVO.setJustificativa(null);
					}
				}
			} else if (cbStatus.getSelectedItem().equals("Bloqueado")) {
				chamadosVO.setJustificativa(JOptionPane.showInputDialog(null, "Justifique o bloqueio do chamado "));
				if (chamadosVO.getJustificativa() != null) {
					if (chamadosVO.getJustificativa().trim().equals("")) {
						chamadosVO.setJustificativa(null);
					}
				}
			}
			if (justi) {
				if (chamadosVO.getJustificativa() != null) {
					justi = false;
				}
			}
		}
		return justi;
	}

	private void autalizarFechar() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ViewListarChamados.consultar();
			}
		});
	}
}
