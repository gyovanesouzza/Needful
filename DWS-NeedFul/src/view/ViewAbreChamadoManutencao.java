package view;

import java.awt.Color;
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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.google.gson.JsonSyntaxException;
import com.toedter.calendar.JDateChooser;

import controller.ChamadoController;
import controller.TecnicoController;
import util.Constantes;
import util.Log;
import util.LookAndFeel;
import validator.DocumentoCliente;
import validator.DocumentoHorasAgendamento;
import validator.Mascara;
import vo.ChamadosVO;
import vo.ClientVO;
import vo.StatusVO;
import vo.TecnicoVO;

@SuppressWarnings("unused")
public class ViewAbreChamadoManutencao extends JDialog {

	private static final long serialVersionUID = 1L;

	ChamadoController controller = new ChamadoController();
	ChamadosVO chamadosVO = new ChamadosVO();
	Mascara masc = new Mascara();

	SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfHoras = new SimpleDateFormat("HH:mm");

	private JPanel AbrirChamadosManutencao;
	private JTextField txtEndereco;
	private JTextPane txtObservacoes;
	private JFormattedTextField ftxtCEP;
	private JFormattedTextField ftxtCelular;
	private static JFormattedTextField ftxtHoras;
	private JFormattedTextField ftxtTelefone;
	private JFormattedTextField ftxtCliente;
	private JFormattedTextField ftxtAgendamentoHoras;
	private static JFormattedTextField ftxtData;
	private JFormattedTextField txtRoteador;
	private static JComboBox<Object> cbTecnico;
	private JDateChooser dcAgendamento;
	private int frag = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAbreChamadoManutencao frame = new ViewAbreChamadoManutencao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public ViewAbreChamadoManutencao() throws IOException {
		initCompotent();
		new Thread(atualizaHora).start();

	}

	private void initCompotent() throws IOException {
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ViewAbreChamadoInstalacao.class.getResource("/imgs/RSS.png")));
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Abrir Chamado");
		setResizable(false);
		setBounds(100, 100, 529, 624);
		setLocationRelativeTo(null);

		AbrirChamadosManutencao = new JPanel();
		AbrirChamadosManutencao.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(AbrirChamadosManutencao);
		AbrirChamadosManutencao.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 53, 496, 533);
		panel.setBorder(BorderFactory.createEtchedBorder());

		AbrirChamadosManutencao.add(panel);
		panel.setLayout(null);

		ftxtData = new JFormattedTextField();
		ftxtData.setBackground(Color.LIGHT_GRAY);
		ftxtData.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtData.setEditable(false);
		ftxtData.setBounds(123, 11, 86, 20);
		panel.add(ftxtData);

		ftxtHoras = new JFormattedTextField();
		ftxtHoras.setBackground(Color.LIGHT_GRAY);
		ftxtHoras.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtHoras.setEditable(false);
		ftxtHoras.setBounds(248, 11, 46, 20);
		masc.horasAtual(ftxtHoras);
		panel.add(ftxtHoras);

		ftxtCliente = new JFormattedTextField();
		ftxtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				ftxtCliente.setBackground(new Color(255, 255, 255));
				ftxtTelefone.setText("");
				ftxtCelular.setText("");
				txtEndereco.setText("");
				ftxtCEP.setText("");
				txtRoteador.setText("");
				frag = 1;
			}
		});
		ftxtCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCliente.requestFocus();
		ftxtCliente.setDocument(new DocumentoCliente());
		ftxtCliente.setColumns(10);
		ftxtCliente.setBounds(123, 48, 254, 20);
		panel.add(ftxtCliente);

		ftxtTelefone = new JFormattedTextField();
		ftxtTelefone.setEditable(false);
		ftxtTelefone.setBackground(Color.LIGHT_GRAY);
		ftxtTelefone.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtTelefone.setColumns(10);
		ftxtTelefone.setBounds(123, 85, 126, 20);
		panel.add(ftxtTelefone);

		ftxtCelular = new JFormattedTextField();
		ftxtCelular.setEditable(false);
		ftxtCelular.setBackground(Color.LIGHT_GRAY);
		ftxtCelular.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCelular.setColumns(10);
		ftxtCelular.setBounds(123, 122, 126, 20);
		panel.add(ftxtCelular);

		txtEndereco = new JTextField();
		txtEndereco.setEditable(false);
		txtEndereco.setBackground(Color.LIGHT_GRAY);
		txtEndereco.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(123, 159, 254, 20);
		panel.add(txtEndereco);

		ftxtCEP = new JFormattedTextField();
		ftxtCEP.setEditable(false);
		ftxtCEP.setBackground(Color.LIGHT_GRAY);
		ftxtCEP.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCEP.setColumns(10);
		ftxtCEP.setBounds(123, 196, 126, 20);
		panel.add(ftxtCEP);

		cbTecnico = new JComboBox<Object>();
		cbTecnico.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				cbTecnico.setBackground(new Color(255, 255, 255));
			}
		});
		cbTecnico.setToolTipText("");
		cbTecnico.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTecnico.setSelectedIndex(-1);
		cbTecnico.setBounds(123, 280, 126, 20);
		panel.add(cbTecnico);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 227, 497, 7);
		panel.add(separator_1);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpar();
			}
		});
		btnLimpar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnLimpar.setBounds(241, 475, 89, 29);
		panel.add(btnLimpar);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (frag != 1) {
					if (validarHoraAgendamento()) {
						HttpURLConnection httpURLConnection = null;

						if (nulo()) {
							if (dataHorarioAgend()) {

								try {

									URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
									httpURLConnection = (HttpURLConnection) name.openConnection();
									httpURLConnection.connect();

									if (cadastrar()) {
										Log.logDebug("Chamado Aberto", getClass());
										JOptionPane.showMessageDialog(null, "Chamado Cadastrado com Sucesso! ",
												"Chamado Manuntenção", JOptionPane.INFORMATION_MESSAGE);
										dispose();
									} else {
										JOptionPane.showMessageDialog(null, "Erro ao Cadastrar Chamado! ",
												"Chamado Manuntenção", JOptionPane.INFORMATION_MESSAGE);
									}
								} catch (SocketException e) {
									Log.logFatal(e.getMessage(), e.getCause(), getClass());
									Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
									JOptionPane.showMessageDialog(null,
											"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
											"ERROR", JOptionPane.ERROR_MESSAGE);
									Logger.getLogger(ViewAbreChamadoInstalacao.class.getName()).log(Level.ALL, null,
											e.getCause());
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
									Logger.getLogger(ViewAbreChamadoInstalacao.class.getName()).log(Level.ALL, null,
											e.getCause());
									System.gc();
									for (Window window : Window.getWindows()) {
										window.dispose();
									}
									new ViewLogin().setVisible(true);
								} finally {
									httpURLConnection.disconnect();
								}
							} else {
								Log.logInfo("Tentativa de abrir um chamado abaixo do horario atual", getClass());
								JOptionPane.showMessageDialog(null,
										"Por favor informe um horario acima do horario atual", getTitle(),
										JOptionPane.INFORMATION_MESSAGE);
							}
						} else {
							Log.logInfo("Tentativa de abrir um chamado com algum campos obrigatórios", getClass());

							JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos !",
									"Chamado Instalação", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} else {
					Log.logInfo("Tentativa de abrir um chamado sem selecionar um cliente ", getClass());

					JOptionPane.showMessageDialog(null, "Por Favor selecione um cliente", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					ftxtCliente.setBackground(new Color(255, 102, 102));
					ftxtCliente.requestFocus();

				}

			}
		});
		btnCadastrar.setHorizontalTextPosition(SwingConstants.LEFT);
		btnCadastrar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCadastrar.setBounds(351, 475, 120, 29);
		panel.add(btnCadastrar);

		txtObservacoes = new JTextPane();
		txtObservacoes.setFont(new Font("Arial", Font.PLAIN, 15));
		txtObservacoes.setBounds(123, 354, 254, 106);
		panel.add(txtObservacoes);

		JLabel label_1 = new JLabel("Observa\u00E7\u00F5es:");
		label_1.setFont(new Font("Arial", Font.PLAIN, 15));
		label_1.setBounds(16, 354, 97, 20);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Tecnico: ");
		label_2.setFont(new Font("Arial", Font.PLAIN, 15));
		label_2.setBounds(16, 280, 80, 20);
		panel.add(label_2);

		JLabel label_3 = new JLabel("CEP:");
		label_3.setFont(new Font("Arial", Font.PLAIN, 15));
		label_3.setBounds(16, 196, 57, 20);
		panel.add(label_3);

		JLabel label_4 = new JLabel("Endere\u00E7o: ");
		label_4.setFont(new Font("Arial", Font.PLAIN, 15));
		label_4.setBounds(16, 159, 80, 20);
		panel.add(label_4);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 15));
		lblData.setBounds(16, 11, 57, 20);
		panel.add(lblData);

		JLabel lblCliente = new JLabel("Cliente: ");
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCliente.setBounds(16, 48, 80, 20);
		panel.add(lblCliente);

		JLabel label_7 = new JLabel("Telefone: ");
		label_7.setFont(new Font("Arial", Font.PLAIN, 15));
		label_7.setBounds(16, 85, 80, 20);
		panel.add(label_7);

		JLabel label_8 = new JLabel("Cel:");
		label_8.setFont(new Font("Arial", Font.PLAIN, 15));
		label_8.setBounds(16, 122, 46, 20);
		panel.add(label_8);

		JLabel lblHora = new JLabel("\u00E0s");
		lblHora.setFont(new Font("Arial", Font.PLAIN, 15));
		lblHora.setBounds(219, 12, 25, 18);
		panel.add(lblHora);

		JLabel lblAgendamento = new JLabel("Agendamento:");
		lblAgendamento.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAgendamento.setBounds(16, 243, 107, 20);
		panel.add(lblAgendamento);

		dcAgendamento = new JDateChooser(new Date());
		dcAgendamento.setFont(new Font("Arial", Font.PLAIN, 15));
		dcAgendamento.setBounds(123, 243, 126, 20);
		dcAgendamento.setDateFormatString("dd/MM/yyyy");

		validarData();
		panel.add(dcAgendamento);

		JButton button = new JButton("Procurar");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				HttpURLConnection httpURLConnection = null;
				try {
					URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) name.openConnection();
					httpURLConnection.connect();

					ClientVO clientVO = new ClientVO();
					clientVO.setNome(ftxtCliente.getText());
					ViewCliente viewCliente = new ViewCliente(clientVO);

					viewCliente.setVisible(true);
					frag = viewCliente.fechou;
					if (frag != 1) {
						ftxtCliente.setBackground(new Color(255, 255, 255));

						clientVO = viewCliente.passarDados();
						chamadosVO.setClientVO(clientVO);

						ftxtCliente.setText(clientVO.getNome());
						ftxtTelefone.setText(clientVO.getTelefone());
						ftxtCelular.setText(clientVO.getCelular());
						txtEndereco.setText(
								clientVO.getEnderecoVO().getRua() + ", " + clientVO.getEnderecoVO().getNumero());
						ftxtCEP.setText(clientVO.getEnderecoVO().getCep());
						txtRoteador.setText(clientVO.getRoteador());
					}
				} catch (SocketException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(ViewAbreChamadoManutencao.class.getName()).log(Level.ALL, null, e.getCause());
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(ViewAbreChamadoManutencao.class.getName()).log(Level.ALL, null, e.getCause());
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
		button.setFont(new Font("Arial", Font.PLAIN, 15));
		button.setBounds(397, 48, 89, 20);
		panel.add(button);

		JLabel label = new JLabel("\u00E0s");
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		label.setBounds(266, 243, 22, 18);
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
		ftxtAgendamentoHoras.addCaretListener(new CaretListener() {
			boolean update = false;

			public void caretUpdate(CaretEvent evt) {
				if (!update) {

					update = true;
					ftxtAgendamentoHoras.setCaretPosition(ftxtAgendamentoHoras.getText().length());
					update = false;
				}

			}
		});
		ftxtAgendamentoHoras.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtAgendamentoHoras.setBounds(296, 243, 46, 20);

		ftxtAgendamentoHoras.setDocument(new DocumentoHorasAgendamento());
		panel.add(ftxtAgendamentoHoras);

		JLabel lblRoteador = new JLabel("Roteador: ");
		lblRoteador.setFont(new Font("Arial", Font.PLAIN, 15));
		lblRoteador.setBounds(16, 317, 86, 20);
		panel.add(lblRoteador);

		txtRoteador = new JFormattedTextField();
		txtRoteador.setFont(new Font("Arial", Font.PLAIN, 15));
		txtRoteador.setEditable(false);
		txtRoteador.setColumns(10);
		txtRoteador.setBackground(Color.LIGHT_GRAY);
		txtRoteador.setBounds(123, 317, 126, 20);
		panel.add(txtRoteador);

		JLabel lblAbrirChamadoDe = new JLabel("Abrir Chamado de Manunten\u00E7\u00E3o");
		lblAbrirChamadoDe.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbrirChamadoDe.setFont(new Font("Arial", Font.PLAIN, 18));
		lblAbrirChamadoDe.setBounds(10, 11, 503, 29);
		AbrirChamadosManutencao.add(lblAbrirChamadoDe);
		poupolaDadosJComboBox();
	}

	private static void poupolaDadosJComboBox() throws IOException {
		TecnicoController tecnicoController = new TecnicoController();
		cbTecnico.removeAllItems();

		for (TecnicoVO tVO : tecnicoController.read()) {
			cbTecnico.addItem(tVO);
		}
		cbTecnico.setSelectedIndex(-1);
	}

	public boolean cadastrar() throws SocketException, IOException {

		return controller.abreChamadoManuntecao(popularDados());
	}

	public void limpar() {
		ftxtCliente.setText("");
		ftxtTelefone.setText("");
		ftxtCelular.setText("");
		txtEndereco.setText("");
		ftxtCEP.setText("");
		txtRoteador.setText("");
		dcAgendamento.setDate(null);
		cbTecnico.setSelectedIndex(-1);
		txtObservacoes.setText("");
		ftxtAgendamentoHoras.setText("");
	}

	private ChamadosVO popularDados() {
		try {
			TecnicoVO tecnicoVO = new TecnicoVO();
			tecnicoVO.setTecnico(cbTecnico.getSelectedItem().toString());
			StatusVO statusVO = new StatusVO();
			statusVO.setTipo(Constantes.getStatus());
			chamadosVO.setData(sdfData.parse(ftxtData.getText()));
			chamadosVO.setHoras(new java.sql.Time(sdfHoras.parse(ftxtHoras.getText()).getTime()));
			chamadosVO.setAgendamento_Data(sdfData.parse(ftxtData.getText()));

			chamadosVO.setAgendamento_horas(new Time(sdfHoras.parse(ftxtAgendamentoHoras.getText()).getTime()));

			chamadosVO.setDescricao(txtObservacoes.getText());
			chamadosVO.setTecnicoVO(tecnicoVO);
			chamadosVO.setTipo(Constantes.getTipoChamadoManuntecao());
			chamadosVO.setStatusVO(statusVO);
		} catch (ParseException e) {
			Log.logErro(e.getMessage(), e.getCause(), getClass());
			JOptionPane.showMessageDialog(null, e.getCause());
		}

		return chamadosVO;
	}

	public boolean nulo() {
		boolean retorno = true;

		if (ftxtCliente.getText().isEmpty()) {
			retorno = false;
			ftxtCliente.setBackground(new Color(255, 102, 102));
			ftxtCliente.requestFocus();
		}
		if (cbTecnico.getSelectedIndex() < 0) {
			retorno = false;
			cbTecnico.setBackground(new Color(255, 102, 102));

		}
		if (ftxtAgendamentoHoras.getText().isEmpty()) {
			retorno = false;
			ftxtAgendamentoHoras.setBackground(new Color(255, 102, 102));
			ftxtAgendamentoHoras.requestFocus();
		}

		return retorno;

	}

	private void validarData() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 180);

			Date dataAtual = sdfData.parse(ftxtData.getText());
			Date limit = cal.getTime();

			dcAgendamento.setMinSelectableDate(dataAtual);
			dcAgendamento.setMaxSelectableDate(limit);
		} catch (ParseException e) {
			Log.logErro(e.getMessage(), e.getCause(), getClass());
		}
	}

	private static Runnable atualizaHora = new Runnable() {
		public void run() {
			while (true) {
				Date data = new Date();
				ftxtData.setValue(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				ftxtHoras.setValue(new SimpleDateFormat("HH:mm").format(new Date()));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, "Erro ao atualizar a Hora", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	private boolean validarHoraAgendamento() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			sdf.setLenient(false);
			sdf.parse(ftxtAgendamentoHoras.getText());

		} catch (ParseException e) {
			Log.logErro(e.getMessage(), e.getCause(), getClass());
			ftxtAgendamentoHoras.requestFocus();
			ftxtAgendamentoHoras.setBackground(new Color(255, 102, 102));
			JOptionPane.showMessageDialog(null, "Por favor informe uma hora valida", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean dataHorarioAgend() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		if (dcAgendamento.getDate().getDate() == new Date().getDate()) {

			try {
				Date now = sdfHoras.parse(ftxtHoras.getText());
				Date aux = sdfHoras.parse(ftxtAgendamentoHoras.getText());
				if (aux.equals(now) || aux.before(now)) {
					return false;

				}

			} catch (ParseException e) {
				Log.logErro(e.getMessage(), e.getCause(), getClass());
				ftxtAgendamentoHoras.setBackground(new Color(255, 102, 102));
				ftxtAgendamentoHoras.requestFocus();
				JOptionPane.showMessageDialog(null, "Por favor informe uma Hora acima do horario atual", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return false;

			}
		}
		return true;

	}
}
