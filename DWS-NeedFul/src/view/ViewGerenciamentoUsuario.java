package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.google.gson.JsonSyntaxException;

import controller.UsuarioController;
import tabelModel.UsuarioTableModel;
import util.Constantes;
import util.CriptografaSenha;
import util.LookAndFeel;
import validator.Mascara;
import validator.MascaraCPF;
import validator.MascaraEmail;
import validator.MascaraLogin;
import validator.ValidarCPF;
import vo.TecnicoVO;
import vo.UsuarioVO;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ViewGerenciamentoUsuario extends JDialog {

	private static final long serialVersionUID = 1L;

	static UsuarioController controller = new UsuarioController();
	TecnicoVO tecnicoVO = new TecnicoVO();
	UsuarioTableModel tableModel = new UsuarioTableModel();
	Mascara mascara = new Mascara();
	private UsuarioVO usuarioVO = new UsuarioVO();

	private JPanel Estoque;
	private JFormattedTextField txtLogin;
	private JPasswordField txtSenha;
	private JTextField txtNome;
	private JTextField txtEMail;
	private static JTable tblUsuario;
	private JPasswordField txtConfirmarSenha;
	private JButton btnNovo;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnAlterar;
	private JButton btnCancelar;
	private static JLabel lblMostrarUsuariosDesativado;
	private static JComboBox<Object> cbTipoConta;
	int frag = 0;
	int alterar = 0;
	static boolean pesquisaDesativado = false;
	private JTextField txtCPF;
	private Thread combox = new Thread(popularDadosJCombox);
	private Thread tabela = new Thread(atualizarJtable);

	public ViewGerenciamentoUsuario() {

		initCompontent();
		combox.start();
		tabela.start();
	}

	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewGerenciamentoUsuario frame = new ViewGerenciamentoUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initCompontent() {
		setResizable(false);
		setModal(true);
		setTitle("Controle de Acesso");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ViewGerenciamentoUsuario.class.getResource("/imgs/icons8-lock-30.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 648, 685);
		Estoque = new JPanel();
		Estoque.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Estoque);
		setLocationRelativeTo(null);
		Estoque.setLayout(null);

		JLabel lblNewLabel = new JLabel("Controle de Acesso");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 18, 609, 22);
		Estoque.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setBounds(10, 51, 622, 594);
		Estoque.add(panel);
		panel.setLayout(null);

		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 11, 47, 20);
		panel.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBackground(Color.LIGHT_GRAY);
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				txtNome.setBackground(new Color(255, 255, 255));
			}
		});
		txtNome.setFont(new Font("Arial", Font.PLAIN, 15));
		txtNome.setEnabled(false);
		txtNome.setColumns(10);
		txtNome.setBounds(145, 11, 137, 20);
		panel.add(txtNome);

		txtCPF = new JTextField();
		txtCPF.addCaretListener(new CaretListener() {
			boolean update = false;

			public void caretUpdate(CaretEvent evt) {
				if (!update) {

					update = true;
					txtCPF.setCaretPosition(txtCPF.getText().length());
					update = false;
				}

			}
		});
		txtCPF.setBackground(Color.LIGHT_GRAY);
		txtCPF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				validarCPF();

			}

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
					if (evt.getKeyCode() == KeyEvent.VK_V) {
						evt.consume();
					}
				}

			}
		});

		JLabel lblCpf = new JLabel("CPF: ");
		lblCpf.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCpf.setBounds(10, 51, 38, 20);
		panel.add(lblCpf);
		txtCPF.setFont(new Font("Arial", Font.PLAIN, 15));
		txtCPF.setEnabled(false);
		txtCPF.setBounds(145, 51, 137, 20);
		txtCPF.setDocument(new MascaraCPF());
		panel.add(txtCPF);
		txtCPF.setColumns(10);

		JLabel label_4 = new JLabel("E-Mail: ");
		label_4.setFont(new Font("Arial", Font.PLAIN, 15));
		label_4.setBounds(10, 90, 48, 20);
		panel.add(label_4);

		txtEMail = new JFormattedTextField();
		txtEMail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
			}
		});
		txtEMail.setBackground(Color.LIGHT_GRAY);
		txtEMail.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEMail.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getModifiers() == KeyEvent.CTRL_MASK) {
					if (evt.getKeyCode() == KeyEvent.VK_V) {
						evt.consume();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				validarEmail();

			}
		});
		txtEMail.setEnabled(false);
		txtEMail.setColumns(10);
		txtEMail.setBounds(145, 90, 137, 20);
		panel.add(txtEMail);

		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLogin.setBounds(10, 130, 43, 20);
		panel.add(lblLogin);

		txtLogin = new JFormattedTextField();
		txtLogin.setBackground(Color.LIGHT_GRAY);
		txtLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				txtLogin.setBackground(new Color(255, 255, 255));

			}
		});
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLogin.setEnabled(false);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new MascaraLogin());
		txtLogin.setBounds(145, 130, 137, 20);
		panel.add(txtLogin);

		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSenha.setBounds(10, 170, 50, 20);
		panel.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setBackground(Color.LIGHT_GRAY);
		txtSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtSenha.setBackground(new Color(255, 255, 255));

			}
		});
		txtSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		txtSenha.setEnabled(false);
		txtSenha.setBounds(145, 170, 137, 20);
		panel.add(txtSenha);

		JLabel lblConfirmarSenha = new JLabel("Confirmar Senha: ");
		lblConfirmarSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblConfirmarSenha.setBounds(10, 210, 119, 20);
		panel.add(lblConfirmarSenha);

		txtConfirmarSenha = new JPasswordField();
		txtConfirmarSenha.setBackground(Color.LIGHT_GRAY);
		txtConfirmarSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				txtConfirmarSenha.setBackground(new Color(255, 255, 255));

			}
		});
		txtConfirmarSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		txtConfirmarSenha.setEnabled(false);
		txtConfirmarSenha.setBounds(145, 210, 137, 20);
		panel.add(txtConfirmarSenha);

		JLabel lblTipo = new JLabel("Tipo da Conta: ");
		lblTipo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTipo.setBounds(10, 250, 99, 20);
		panel.add(lblTipo);

		cbTipoConta = new JComboBox<Object>();
		cbTipoConta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {

				cbTipoConta.setBackground(new Color(255, 255, 255));

			}
		});
		cbTipoConta.setEnabled(false);
		cbTipoConta.setBackground(Color.LIGHT_GRAY);

		cbTipoConta.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTipoConta.setSelectedIndex(-1);

		cbTipoConta.setBounds(145, 250, 118, 20);
		panel.add(cbTipoConta);

		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frag = 1;
				txtNome.setBackground(new Color(255, 255, 255));
				txtLogin.setBackground(new Color(255, 255, 255));
				txtSenha.setBackground(new Color(255, 255, 255));
				txtConfirmarSenha.setBackground(new Color(255, 255, 255));
				txtEMail.setBackground(new Color(255, 255, 255));
				cbTipoConta.setBackground(new Color(255, 255, 255));
				txtCPF.setBackground(new Color(255, 255, 255));

				txtNome.setEnabled(true);
				txtNome.requestFocus();
				txtCPF.setEnabled(true);
				txtLogin.setEnabled(true);
				txtSenha.setEnabled(true);
				txtConfirmarSenha.setEnabled(true);
				txtEMail.setEnabled(true);
				cbTipoConta.setEnabled(true);
				btnCancelar.setEnabled(true);
				btnSalvar.setEnabled(true);
				txtNome.requestFocus();
				btnNovo.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnAlterar.setEnabled(false);

			}
		});
		btnNovo.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNovo.setEnabled(true);
		btnNovo.setBounds(498, 11, 112, 32);
		panel.add(btnNovo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean finalizou = false;
				HttpURLConnection httpURLConnection = null;

				if (!campoEmBranco()) {
					if (criteiro()) {
						try {

							URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
							httpURLConnection = (HttpURLConnection) url.openConnection();
							httpURLConnection.connect();

							if (frag == 1) {
								if (new String(txtSenha.getPassword())
										.equals(new String(txtConfirmarSenha.getPassword()))) {
									if (ValidarCPF.isCPF(txtCPF.getText().replaceAll("[.-]", ""))) {

										UsuarioVO usuarioVO = poupularUsuario();

										if (!controller.existerContaa(usuarioVO)) {
											usuarioVO.setEmail(null);
											if (!controller.verificarExistenciaLoginEmail(usuarioVO)) {
												usuarioVO = poupularUsuario();
												usuarioVO.setLogin(null);
												if (!controller.verificarExistenciaLoginEmail(usuarioVO)) {
													usuarioVO = poupularUsuario();

													if (!controller.existerContaAD(usuarioVO)) {
														if (controller.criarUsuario(usuarioVO)) {
															finalizou = true;
															JOptionPane.showMessageDialog(null,
																	"Usuario Cadastrado com Sucesso!",
																	"Controle de Acesso", JOptionPane.PLAIN_MESSAGE);

														} else {
															JOptionPane.showMessageDialog(null,
																	"Error ao Salvar Usuario", "ERROR",
																	JOptionPane.ERROR_MESSAGE);
														}

													} else {

														Object[] options = { "Sim", "Não" };
														int confirm = JOptionPane.showOptionDialog(null,
																"Desejear reativar a conta ?", "Informação",
																JOptionPane.DEFAULT_OPTION,
																JOptionPane.QUESTION_MESSAGE, null, options,
																options[0]);
														confirm += 1;

														if (confirm == 1) {
															usuarioVO.setStatusAD(confirm);
															if (controller.statusConta(usuarioVO)) {
																limparEVoltarAoNormalText();
																JOptionPane.showMessageDialog(null,
																		"Conta ativa com sucesso!!", getTitle(),
																		JOptionPane.PLAIN_MESSAGE);

															} else {
																JOptionPane.showMessageDialog(null,
																		"Erro ao ativar a conta", getTitle(),
																		JOptionPane.PLAIN_MESSAGE);
															}
														}

													}
												} else {
													JOptionPane.showMessageDialog(null, "E-Mail já cadastrado",
															"Information", JOptionPane.INFORMATION_MESSAGE);

												}
											} else {
												JOptionPane.showMessageDialog(null, "Login já cadastrado",
														"Information", JOptionPane.INFORMATION_MESSAGE);
											}
										} else {
											JOptionPane.showMessageDialog(null, "CPF já cadastrado", "Information",
													JOptionPane.INFORMATION_MESSAGE);
										}
									} else {
										JOptionPane.showMessageDialog(null, "Por favor informe um CPF válido");
									}

								} else {
									JOptionPane.showMessageDialog(null, "As senha não são iguais", "Erro",
											JOptionPane.INFORMATION_MESSAGE);

								}
							}
							if (frag == 2) {

								if (tblUsuario.getSelectedRow() != -1) {

									if (new String(txtSenha.getPassword())
											.equals(new String(txtConfirmarSenha.getPassword()))) {

										finalizou = true;
										if (controller.alteraConta(poupularUsuarioaltear())) {
											JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
										}

									} else {
										JOptionPane.showMessageDialog(null, "As senha não sao iguais", "Erro",
												JOptionPane.ERROR_MESSAGE);
									}

								}
							}
							if (finalizou) {
								limparEVoltarAoNormalText();
								readJTable();
							}

						} catch (SocketException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null,
									"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
									"ERROR", JOptionPane.ERROR_MESSAGE);
							System.gc();
							for (Window window : Window.getWindows()) {
								window.dispose();
							}
							new ViewLogin().setVisible(true);
						} catch (IOException e) {
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

					}
				}
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSalvar.setBounds(498, 60, 112, 32);
		panel.add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparEVoltarAoNormalText();
			}
		});
		btnCancelar.setEnabled(false);
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCancelar.setBounds(498, 109, 112, 32);
		panel.add(btnCancelar);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (tblUsuario.getSelectedRow() != -1) {

					UsuarioVO usuarioVO = new UsuarioVO();
					usuarioVO.setId(Integer.parseInt(tblUsuario.getValueAt(tblUsuario.getSelectedRow(), 0).toString()));
					HttpURLConnection httpURLConnection = null;

					try {

						URL name = new URL("http://"+Constantes.getIpWebservice()+":8080/");
						httpURLConnection = (HttpURLConnection) name.openConnection();
						httpURLConnection.connect();
						if (controller.permissaoAlterar(usuarioVO)) {

							frag = 2;
							alterar = 1;

							txtNome.setBackground(new Color(255, 255, 255));
							txtLogin.setBackground(new Color(255, 255, 255));
							txtSenha.setBackground(new Color(255, 255, 255));
							txtConfirmarSenha.setBackground(new Color(255, 255, 255));
							txtEMail.setBackground(new Color(255, 255, 255));
							cbTipoConta.setBackground(new Color(255, 255, 255));

							txtNome.setEnabled(true);
							txtLogin.setEnabled(true);
							txtSenha.setEnabled(true);
							txtConfirmarSenha.setEnabled(true);
							txtEMail.setEnabled(true);
							cbTipoConta.setEnabled(true);
							txtCPF.setEditable(false);
							txtCPF.setEnabled(true);
							txtCPF.setBackground(Color.LIGHT_GRAY);
							btnAlterar.setEnabled(false);
							btnExcluir.setEnabled(false);
							btnNovo.setEnabled(false);
							btnSalvar.setEnabled(true);
							btnCancelar.setEnabled(true);

							txtNome.requestFocus();

							dadosnoCampo(usuarioVO);
						} else {
							JOptionPane.showMessageDialog(null, "A conta seleciona está desativada.", getTitle(),
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (SocketException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						Logger.getLogger(ViewAbreChamadoInstalacao.class.getName()).log(Level.ALL, null, e.getCause());
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} finally {
						httpURLConnection.disconnect();

					}
				} else {
					JOptionPane.showMessageDialog(null, "Por Favor Selecioner um Usuario para Alterar", "",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAlterar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnAlterar.setBounds(498, 158, 112, 32);
		panel.add(btnAlterar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (tblUsuario.getSelectedRow() != -1) {

					Object[] options = { "Confirmar", "Cancelar" };
					int Confirm = JOptionPane.showOptionDialog(null, "Clique Confirmar para Excluir", "Informação",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					HttpURLConnection httpURLConnection = null;

					try {

						URL name = new URL("http://"+Constantes.getIpWebservice()+":8080/");
						httpURLConnection = (HttpURLConnection) name.openConnection();
						httpURLConnection.connect();
						if (btnExcluir.getText().equals("Ativar")) {
							Confirm += 1;
							usuarioVO.setStatusAD(Confirm);
							if (Confirm == 1) {

								if (controller.statusConta(usuarioVO)) {
									JOptionPane.showMessageDialog(null, "Conta ativada com sucesso", getTitle(),
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null, "Erro ao ativa a conta ", getTitle(),
											JOptionPane.INFORMATION_MESSAGE);

								}

							}

						} else {
							Confirm += 2;
							usuarioVO = controller.pesquisaUsuario(usuarioVO);
							usuarioVO.setStatusAD(Confirm);

							if (Confirm == 2) {

								if (controller.statusConta(usuarioVO)) {
									JOptionPane.showMessageDialog(null, "Conta excluida com sucesso", getTitle(),
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null, "Erro ao exclui a conta ", getTitle(),
											JOptionPane.INFORMATION_MESSAGE);

								}

							}
						}
					} catch (SocketException e) {
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						Logger.getLogger(ViewAbreChamadoInstalacao.class.getName()).log(Level.ALL, null, e.getCause());
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} finally {
						httpURLConnection.disconnect();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um usuario para excluir", "",
							JOptionPane.WARNING_MESSAGE);
				}
				btnExcluir.setText("Excluir");
				readJTable();
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 14));
		btnExcluir.setBounds(498, 207, 112, 32);
		panel.add(btnExcluir);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 301, 600, 263);
		panel.add(scrollPane);

		tblUsuario = new JTable();
		tblUsuario.getTableHeader().setReorderingAllowed(true);

		tblUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				HttpURLConnection httpURLConnection = null;

				try {

					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					if (pesquisaDesativado) {
						if (tblUsuario.getSelectedRow() != -1) {
							usuarioVO = controller.pesquisaUsuario(poupularUsuario());
							if (controller.existerContaAD(usuarioVO)) {
								btnExcluir.setText("Ativar");
							} else {
								btnExcluir.setText("Excluir");

							}
						}
					}

				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
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
		});
		tblUsuario.setModel(tableModel);
		tblUsuario.setRowSorter(new TableRowSorter<UsuarioTableModel>(tableModel));
		tblUsuario.getColumnModel().getColumn(0).setPreferredWidth(15);
		tblUsuario.setRowSelectionAllowed(true);

		scrollPane.setViewportView(tblUsuario);

		lblMostrarUsuariosDesativado = new JLabel("Mostrar usuarios desativado");
		lblMostrarUsuariosDesativado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (pesquisaDesativado) {
					btnExcluir.setText("Excluir");

					pesquisaDesativado = false;
				} else {
					pesquisaDesativado = true;

				}
				readJTable();
			}
		});
		lblMostrarUsuariosDesativado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMostrarUsuariosDesativado.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMostrarUsuariosDesativado.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblMostrarUsuariosDesativado.setBounds(413, 575, 197, 14);
		panel.add(lblMostrarUsuariosDesativado);

	}

	public void dadosnoCampo(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {

		UsuarioVO usuarioVo = controller.pesquisaUsuario(usuarioVO);
		txtNome.setText(usuarioVo.getNome());
		txtLogin.setValue(usuarioVo.getLogin().trim());
		txtEMail.setText(usuarioVo.getEmail());
		txtCPF.setText(usuarioVo.getCPF());
		txtSenha.setText("*********");
		txtConfirmarSenha.setText("*********");
		cbTipoConta.setSelectedItem(usuarioVo.getTipo());

	}

	public static void readJTable() {
		HttpURLConnection httpURLConnection = null;

		try {

			URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.connect();

			if (pesquisaDesativado) {
				lblMostrarUsuariosDesativado.setForeground(new Color(0, 0, 205));
				lblMostrarUsuariosDesativado.setText("Ocultar usuario desativado");

				readDesativado();

			} else {
				lblMostrarUsuariosDesativado.setForeground(Color.BLACK);

				lblMostrarUsuariosDesativado.setText("Mostrar usuario desativado");
				readSAtivado();

			}
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
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

	private static void readSAtivado() throws SocketException, IOException {
		UsuarioTableModel modelo = (UsuarioTableModel) tblUsuario.getModel();

		while (tblUsuario.getRowCount() > 0)
			modelo.removeRow(0);

		for (UsuarioVO u : controller.readTablea()) {
			modelo.addRow(u);
			modelo.fireTableDataChanged();

		}
	}

	private static void readDesativado() throws SocketException, IOException {
		UsuarioTableModel modelo = (UsuarioTableModel) tblUsuario.getModel();

		while (tblUsuario.getRowCount() > 0)
			modelo.removeRow(0);

		for (UsuarioVO u : controller.readTableAD()) {
			modelo.addRow(u);
			modelo.fireTableDataChanged();

		}
	}

	@SuppressWarnings("deprecation")
	public boolean campoEmBranco() {
		boolean branco = false;

		if (txtNome.getText().trim().isEmpty()) {
			branco = true;
			txtNome.setBackground(new Color(255, 102, 102));
		}

		if (txtLogin.getText().trim().isEmpty()) {
			branco = true;
			txtLogin.setBackground(new Color(255, 102, 102));
		}

		if (txtSenha.getText().trim().isEmpty()) {
			branco = true;
			txtSenha.setBackground(new Color(255, 102, 102));
		}

		if (txtConfirmarSenha.getText().trim().isEmpty()) {
			branco = true;
			txtConfirmarSenha.setBackground(new Color(255, 102, 102));
		}

		if (txtEMail.getText().trim().isEmpty()) {
			branco = true;
			txtEMail.setBackground(new Color(255, 102, 102));
		}

		if (cbTipoConta.getSelectedIndex() < 0) {
			branco = true;
			cbTipoConta.setBackground(new Color(255, 102, 102));
		}

		if (txtCPF.getText().trim().isEmpty()) {
			branco = true;
			txtCPF.setBackground(new Color(255, 102, 102));
		}

		// ff
		if (txtNome.getText().equals("") || txtLogin.getText().equals("")
				|| (new String(txtSenha.getPassword()).equals(""))
				|| (new String(txtConfirmarSenha.getPassword()).equals("")) || txtEMail.getText().equals("")
				|| cbTipoConta.getSelectedIndex() == -1) {
			branco = true;
			JOptionPane.showMessageDialog(null, "Todos Os Campo Deve se Preenchido", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (txtNome.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, informer o nome do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (txtLogin.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, informer o login do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (txtSenha.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, informer a senha do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (txtConfirmarSenha.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, confirmer a senha do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (txtEMail.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, informer o e-mail do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (cbTipoConta.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(null, "Por favor, informer o tipo de conta que será o usuario",
					"Área Restrita", JOptionPane.INFORMATION_MESSAGE);
		} else if (txtCPF.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, informer o cpf do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (txtLogin.getText().length() < 4) {
			branco = true;

			JOptionPane.showMessageDialog(null, "Por favor, informer um login com no minimo 4 caracteres",
					"Área Restrita", JOptionPane.INFORMATION_MESSAGE);
		} else if (txtLogin.getText().length() > 15) {
			branco = true;

			JOptionPane.showMessageDialog(null, "Por favor, informer um login com no maximo 15 caracteres",
					"Área Restrita", JOptionPane.INFORMATION_MESSAGE);
		}
		return branco;
	}

	private boolean criteiro() {
		boolean crit = true;

		if (!txtNome.getText().trim().contains(" ")) {
			crit = false;
			txtNome.setBackground(new Color(255, 102, 102));
			JOptionPane.showMessageDialog(null, "Por favor, informer o nome completo do usuario", "Área Restrita",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (txtLogin.getText().trim().length() < 5) {
			crit = false;
			txtLogin.setBackground(new Color(255, 102, 102));
			JOptionPane.showMessageDialog(null, "Por favor, informer um login com mais de 5 characters",
					"Área Restrita", JOptionPane.INFORMATION_MESSAGE);
		} else if (new String(txtSenha.getPassword()).trim().length() < 5) {
			crit = false;
			txtSenha.setBackground(new Color(255, 102, 102));
			JOptionPane.showMessageDialog(null, "Por favor, informer uma senha com mais de 5 characters",
					"Área Restrita", JOptionPane.INFORMATION_MESSAGE);
		}
		return crit;

	}

	private UsuarioVO poupularUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		if (tblUsuario.getSelectedRow() != -1) {
			usuarioVO.setId(Integer.parseInt(tblUsuario.getValueAt(tblUsuario.getSelectedRow(), 0).toString()));
		}
		usuarioVO.setLogin(txtLogin.getText());
		usuarioVO.setSenha(CriptografaSenha.criptografaSenha(new String(txtSenha.getPassword())));

		usuarioVO.setCPF(txtCPF.getText());
		usuarioVO.setNome(txtNome.getText());
		usuarioVO.setEmail(txtEMail.getText());
		usuarioVO.setTipo((String) cbTipoConta.getSelectedItem());
		return usuarioVO;
	}

	private UsuarioVO poupularUsuarioaltear() throws JsonSyntaxException, SocketException, IOException {
		UsuarioVO usuarioVO = new UsuarioVO();

		usuarioVO.setId(Integer.parseInt(tblUsuario.getValueAt(tblUsuario.getSelectedRow(), 0).toString()));

		UsuarioVO pesquisa = controller.pesquisaUsuario(poupularUsuario());
		if (!txtNome.getText().equals(pesquisa.getNome())) {
			usuarioVO.setNome(txtNome.getText());
			System.out.println("f");
		}
		if (!txtEMail.getText().equals(pesquisa.getEmail())) {
			usuarioVO.setEmail(txtEMail.getText());

		}
		if (!txtLogin.getText().equals(pesquisa.getLogin())) {
			usuarioVO.setLogin(txtLogin.getText());

		}
		if (!new String(txtSenha.getPassword()).equals("*********")) {
			usuarioVO.setSenha(CriptografaSenha.criptografaSenha(new String(txtSenha.getPassword())));

		}
		usuarioVO.setTipo((String) cbTipoConta.getSelectedItem());

		return usuarioVO;
	}

	private void limparEVoltarAoNormalText() {
		txtNome.setText("");
		txtNome.setBackground(Color.LIGHT_GRAY);
		txtEMail.setText("");
		txtEMail.setBackground(Color.LIGHT_GRAY);
		txtLogin.setText("");
		txtLogin.setBackground(Color.LIGHT_GRAY);
		txtSenha.setText("");
		txtSenha.setBackground(Color.LIGHT_GRAY);
		txtCPF.setText("");
		txtCPF.setBackground(Color.LIGHT_GRAY);
		txtConfirmarSenha.setText("");
		txtConfirmarSenha.setBackground(Color.LIGHT_GRAY);
		cbTipoConta.setSelectedIndex(-1);
		cbTipoConta.setBackground(Color.LIGHT_GRAY);

		txtNome.setEnabled(false);
		txtEMail.setEnabled(false);
		txtLogin.setEnabled(false);
		txtSenha.setEnabled(false);
		txtCPF.setEnabled(false);
		txtConfirmarSenha.setEnabled(false);
		cbTipoConta.setEnabled(false);
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(true);
		btnAlterar.setEnabled(true);
	}

	private void validarCPF() {
		if (!txtCPF.getText().isEmpty() && txtCPF.getText().length() == 14) {
			String CPF = txtCPF.getText().replaceAll("[.-]", "");
			;
			if (ValidarCPF.isCPF(CPF)) {
				txtCPF.setBackground(new Color(255, 255, 255));
			} else {
				txtCPF.setBackground(new Color(255, 102, 102));
			}
		} else {
			txtCPF.setBackground(new Color(255, 102, 102));
		}

	}

	private void validarEmail() {
		if (!txtEMail.getText().isEmpty()) {

			if (MascaraEmail.validarEmail(txtEMail.getText())) {
				txtEMail.setBackground(new Color(255, 255, 255));

			} else {
				txtEMail.setBackground(new Color(255, 102, 102));

			}
		}
	}

	public static void popularJcombo() throws JsonSyntaxException, SocketException, IOException {
		for (UsuarioVO usuarioVO : controller.pesquisaTipoDeUsuario()) {
			cbTipoConta.addItem(usuarioVO.getTipo());
		}
	}

	private static Runnable popularDadosJCombox = new Runnable() {
		public void run() {
			while (true) {

				try {
					popularJcombo();
					cbTipoConta.setSelectedIndex(-1);

					Thread.sleep(90000000);
				} catch (Exception e) {

				}
			}
		}
	};

	private static Runnable atualizarJtable = new Runnable() {
		public void run() {
			while (true) {

				HttpURLConnection httpURLConnection = null;

				try {

					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) url.openConnection();
					httpURLConnection.connect();
					readJTable();
					Thread.sleep(90000000);
				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} catch (Exception e) {
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
		}
	};
}
