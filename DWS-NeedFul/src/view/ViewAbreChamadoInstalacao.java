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

public class ViewAbreChamadoInstalacao extends JDialog {

	private static final long serialVersionUID = 1L;

	static TecnicoController tecnicoController = new TecnicoController();
	Mascara masc = new Mascara();
	ChamadosVO chamadosVO = new ChamadosVO();
	ChamadoController chamadoController = new ChamadoController();
	Constantes constantes = new Constantes();
	private int frag = 1;
	SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfHoras = new SimpleDateFormat("HH:mm");

	private JPanel AbrirChamadosInstalacao;
	private JFormattedTextField ftxtTelefone;
	private JFormattedTextField ftxtCliente;
	private JTextField txtEndereco;
	private JTextPane txtObservacoes;
	private JFormattedTextField ftxtCelular;
	private JFormattedTextField ftxtCEP;
	private static JFormattedTextField ftxtData;
	private JFormattedTextField txtRoteador;
	private JFormattedTextField ftxtAgendamentoHoras;
	private static JFormattedTextField ftxtHoras;
	private JLabel lblCliente;
	private static JComboBox<Object> cbTecnico;
	private JDateChooser dcCalendario;

	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ViewAbreChamadoInstalacao frame = new ViewAbreChamadoInstalacao();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewAbreChamadoInstalacao() throws IOException {
		setModal(true);
		initComponent();
		new Thread(atualizaHora).start();

	}

	private void initComponent() throws JsonSyntaxException, SocketException, IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(ViewAbreChamadoInstalacao.class.getResource("/imgs/RSS.png")));
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setTitle("Abrir Chamado");
		setResizable(false);
		setBounds(100, 100, 522, 624);
		setLocationRelativeTo(null);

		AbrirChamadosInstalacao = new JPanel();
		AbrirChamadosInstalacao.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(AbrirChamadosInstalacao);
		AbrirChamadosInstalacao.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 51, 496, 533);
		AbrirChamadosInstalacao.add(panel);
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(null);

		JLabel lblAbrirChamado = new JLabel("Abrir Chamado de Instala\u00E7\u00E3o");
		lblAbrirChamado.setHorizontalAlignment(SwingConstants.CENTER);
		lblAbrirChamado.setFont(new Font("Arial", Font.PLAIN, 18));
		lblAbrirChamado.setBounds(10, 11, 496, 29);
		AbrirChamadosInstalacao.add(lblAbrirChamado);

		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 15));
		lblData.setBounds(16, 11, 57, 20);
		panel.add(lblData);

		JLabel lblHora = new JLabel("\u00E0s");
		lblHora.setFont(new Font("Arial", Font.PLAIN, 15));
		lblHora.setBounds(219, 11, 22, 18);
		panel.add(lblHora);

		JLabel lblTelefone = new JLabel("Telefone: ");
		lblTelefone.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTelefone.setBounds(16, 85, 80, 20);
		panel.add(lblTelefone);

		ftxtTelefone = new JFormattedTextField();
		ftxtTelefone.setEditable(false);
		ftxtTelefone.setBackground(Color.LIGHT_GRAY);
		ftxtTelefone.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtTelefone.setBounds(123, 85, 126, 20);
		ftxtTelefone.setColumns(10);
		panel.add(ftxtTelefone);

		lblCliente = new JLabel("Cliente: ");
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCliente.setBounds(16, 48, 80, 20);
		panel.add(lblCliente);

		ftxtCliente = new JFormattedTextField();
		ftxtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				ftxtCliente.setBackground(new Color(255, 255, 255));
				ftxtAgendamentoHoras.setBackground(new Color(255, 255, 255));
				cbTecnico.setBackground(new Color(255, 255, 255));
				ftxtTelefone.setText("");
				ftxtCelular.setText("");
				txtEndereco.setText("");
				ftxtCEP.setText("");
				txtRoteador.setText("");
				dcCalendario.setDate(null);
				cbTecnico.setSelectedIndex(-1);
				txtObservacoes.setText("");
				ftxtAgendamentoHoras.setText("");
				frag = 1;
			}
		});
		ftxtCliente.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCliente.setBounds(123, 48, 254, 20);
		ftxtCliente.grabFocus();
		ftxtCliente.setColumns(10);
		ftxtCliente.setDocument(new DocumentoCliente());
		panel.add(ftxtCliente);

		JLabel lblEndereo = new JLabel("Endere\u00E7o: ");
		lblEndereo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEndereo.setBounds(16, 159, 80, 20);
		panel.add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setEditable(false);
		txtEndereco.setBackground(Color.LIGHT_GRAY);
		txtEndereco.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(123, 159, 254, 20);
		panel.add(txtEndereco);

		JLabel lblDescrio = new JLabel("Observa\u00E7\u00F5es:");
		lblDescrio.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDescrio.setBounds(16, 354, 92, 20);
		panel.add(lblDescrio);

		txtObservacoes = new JTextPane();
		txtObservacoes.setFont(new Font("Arial", Font.PLAIN, 15));
		txtObservacoes.setBounds(123, 354, 254, 106);
		panel.add(txtObservacoes);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();

			}
		});
		btnLimpar.setBounds(241, 475, 89, 29);
		panel.add(btnLimpar);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Arial", Font.PLAIN, 15));
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
										Log.logDebug("Chamado aberto ", getClass());
										JOptionPane.showMessageDialog(null, "Chamado Cadastrado com Sucesso! ",
												"Chamado Instalação", JOptionPane.INFORMATION_MESSAGE);
										dispose();
									} else {
										JOptionPane.showMessageDialog(null, "Erro ao Cadastrar Chamado! ",
												"Chamado Instalação", JOptionPane.INFORMATION_MESSAGE);
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
										"Por favor informe um Horario acima do horario atual", getTitle(),
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

					JOptionPane.showMessageDialog(null, "Por favor selecione um cliente", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					ftxtCliente.setBackground(new Color(255, 102, 102));
					ftxtCliente.requestFocus();

				}

			}

		});
		btnCadastrar.setHorizontalTextPosition(SwingConstants.LEFT);
		btnCadastrar.setBounds(351, 475, 120, 29);
		panel.add(btnCadastrar);

		ftxtData = new JFormattedTextField();
		ftxtData.setBackground(Color.LIGHT_GRAY);
		ftxtData.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtData.setEditable(false);
		ftxtData.setBounds(123, 11, 86, 20);
		masc.dataAtual(ftxtData);
		panel.add(ftxtData);

		ftxtHoras = new JFormattedTextField();
		ftxtHoras.setBackground(Color.LIGHT_GRAY);
		ftxtHoras.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtHoras.setEditable(false);
		ftxtHoras.setBounds(249, 11, 46, 20);
		panel.add(ftxtHoras);

		ftxtCelular = new JFormattedTextField();
		ftxtCelular.setEditable(false);
		ftxtCelular.setBackground(Color.LIGHT_GRAY);
		ftxtCelular.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCelular.setColumns(10);
		ftxtCelular.setBounds(123, 122, 126, 20);
		panel.add(ftxtCelular);

		JLabel lblCel = new JLabel("Cel:");
		lblCel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCel.setBounds(16, 122, 46, 20);
		panel.add(lblCel);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCep.setBounds(16, 196, 57, 20);
		panel.add(lblCep);

		ftxtCEP = new JFormattedTextField();
		ftxtCEP.setEditable(false);
		ftxtCEP.setBackground(Color.LIGHT_GRAY);
		ftxtCEP.setFont(new Font("Arial", Font.PLAIN, 15));
		ftxtCEP.setColumns(10);
		ftxtCEP.setBounds(123, 196, 126, 20);
		panel.add(ftxtCEP);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 227, 496, 3);
		panel.add(separator_1);

		JLabel lblRoteador = new JLabel("Roteador: ");
		lblRoteador.setFont(new Font("Arial", Font.PLAIN, 15));
		lblRoteador.setBounds(16, 317, 96, 20);
		panel.add(lblRoteador);

		JLabel lblInstalacao = new JLabel("Agendamento:");
		lblInstalacao.setFont(new Font("Arial", Font.PLAIN, 15));
		lblInstalacao.setBounds(16, 243, 96, 18);
		panel.add(lblInstalacao);

		JLabel lblTecnico = new JLabel("Tecnico: ");
		lblTecnico.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTecnico.setBounds(16, 280, 80, 20);
		panel.add(lblTecnico);

		cbTecnico = new JComboBox<Object>();
		cbTecnico.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				cbTecnico.setBackground(new Color(255, 255, 255));
			}
		});

		cbTecnico.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTecnico.setSelectedIndex(-1);
		cbTecnico.setBounds(123, 280, 126, 20);
		panel.add(cbTecnico);

		dcCalendario = new JDateChooser(new Date());
		dcCalendario.setDateFormatString("dd/MM/yyyy");
		dcCalendario.setFont(new Font("Arial", Font.PLAIN, 15));
		dcCalendario.setBounds(123, 243, 126, 20);
		validarData();
		panel.add(dcCalendario);

		JButton btnNewButton = new JButton("Procurar");
		btnNewButton.addActionListener(new ActionListener() {
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
					System.out.println("fechou " + frag);
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
					Log.logFatal(e.getMessage(), e.getCause(), getClass());

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
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton.setBounds(397, 48, 89, 20);
		panel.add(btnNewButton);

		txtRoteador = new JFormattedTextField();
		txtRoteador.setEditable(false);
		txtRoteador.setBackground(Color.LIGHT_GRAY);
		txtRoteador.setFont(new Font("Arial", Font.PLAIN, 15));
		txtRoteador.setColumns(10);
		txtRoteador.setBounds(123, 317, 126, 20);
		panel.add(txtRoteador);

		JLabel label = new JLabel("\u00E0s");
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		label.setBounds(266, 243, 22, 18);
		panel.add(label);

		ftxtAgendamentoHoras = new JFormattedTextField();
		ftxtAgendamentoHoras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
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

		ftxtAgendamentoHoras.setDocument(new DocumentoHorasAgendamento());
		ftxtAgendamentoHoras.setBounds(296, 243, 46, 20);
		panel.add(ftxtAgendamentoHoras);
		poupolaDadosJComboBox();
	}

	private void validarData() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 90);

			Date dataAtual = sdfData.parse(ftxtData.getText());
			Date limit = cal.getTime();

			dcCalendario.setMinSelectableDate(dataAtual);
			dcCalendario.setMaxSelectableDate(limit);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getCause());
		}
	}

	private static void poupolaDadosJComboBox() throws IOException {
		cbTecnico.removeAllItems();

		for (TecnicoVO tVO : tecnicoController.read()) {
			cbTecnico.addItem(tVO);
		}
		cbTecnico.setSelectedIndex(-1);

	}

	private boolean cadastrar() throws SocketException, IOException {
		return chamadoController.abreChamadoInstalacao(popularDados());
	}

	private void limpar() {
		ftxtCliente.setBackground(new Color(255, 255, 255));
		ftxtAgendamentoHoras.setBackground(new Color(255, 255, 255));
		cbTecnico.setBackground(new Color(255, 255, 255));
		ftxtCliente.setText("");
		ftxtTelefone.setText("");
		ftxtCelular.setText("");
		txtEndereco.setText("");
		ftxtCEP.setText("");
		txtRoteador.setText("");
		dcCalendario.setDate(null);
		cbTecnico.setSelectedIndex(-1);
		txtObservacoes.setText("");
		ftxtAgendamentoHoras.setText("");
		frag = 1;

	}

	private ChamadosVO popularDados() {
		try {
			TecnicoVO tecnicoVO = new TecnicoVO();
			tecnicoVO.setTecnico(cbTecnico.getSelectedItem().toString());
			StatusVO statusVO = new StatusVO();
			statusVO.setTipo(Constantes.getStatus());
			chamadosVO.setData(new Date(sdfData.parse(ftxtData.getText()).getTime()));

			chamadosVO.setAgendamento_Data(dcCalendario.getDate());

			chamadosVO.setHoras(new java.sql.Time(sdfHoras.parse(ftxtHoras.getText()).getTime()));

			chamadosVO.setAgendamento_Data(dcCalendario.getDate());

			chamadosVO.setAgendamento_horas(new Time(sdfHoras.parse(ftxtAgendamentoHoras.getText()).getTime()));

			chamadosVO.setDescricao(txtObservacoes.getText());
			chamadosVO.setTecnicoVO(tecnicoVO);
			chamadosVO.setTipo(Constantes.getTipoChamadoInstalacao());
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

		return retorno;
	}

	private static Runnable atualizaHora = new Runnable() {
		public void run() {
			while (true) {

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
			System.out.println(sdf.parse(ftxtAgendamentoHoras.getText()));

		} catch (ParseException e) {
			ftxtAgendamentoHoras.setBackground(new Color(255, 102, 102));
			ftxtAgendamentoHoras.requestFocus();
			JOptionPane.showMessageDialog(null, "Por favor informe uma Hora valida", "Erro", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean dataHorarioAgend() {

		if (dcCalendario.getDate().getDate() == new Date().getDate()) {

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
				JOptionPane.showMessageDialog(null, "Por favor informe uma hora acima do horário atual", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return false;

			}
		}
		return true;

	}
}
