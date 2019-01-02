package view;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.JsonSyntaxException;
import com.toedter.calendar.JDateChooser;

import connection.ConnectionFactory;
import controller.TecnicoController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import util.ArquivoSistema2;
import util.Constantes;
import util.Impressao;
import util.LookAndFeel;
import vo.TecnicoVO;

public class ViewRelatorioChamados extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9089123275997969586L;
	private JDateChooser dtInicial;
	private JDateChooser dtFinal;
	private JComboBox<String> cbTecnico;
	private JTextField txtCliente;
	String src = Constantes.getLocalArquivos() + "\\relatorioChamadoGerais.jasper";

	public ViewRelatorioChamados() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewRelatorioChamados.class.getResource("/imgs/RSS.png")));
		setTitle("Emi\u00E7\u00E3o de Relat\u00F3rio de Chamados");
		initComponent();
	}

	public static void main(String[] args) {
		LookAndFeel.Layout();

		try {
			ViewRelatorioChamados dialog = new ViewRelatorioChamados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComponent() {
		setBounds(100, 100, 498, 308);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBounds(0, 0, 482, 379);
		getContentPane().add(panelPrincipal);
		panelPrincipal.setLayout(null);

		JLabel lblEmitirRelatorio = new JLabel("Emitir Relatorio");
		lblEmitirRelatorio.setBounds(154, 11, 153, 22);
		panelPrincipal.add(lblEmitirRelatorio);
		lblEmitirRelatorio.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmitirRelatorio.setFont(new Font("Arial", Font.PLAIN, 18));

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 44, 464, 175);
		panelPrincipal.add(panel);
		panel.setLayout(null);

		JLabel lblTodos = new JLabel("TODOS");
		lblTodos.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTodos.setBounds(98, 13, 135, 18);
		panel.add(lblTodos);

		JLabel lblDataInicial = new JLabel("Data Inicial: ");
		lblDataInicial.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDataInicial.setBounds(10, 85, 88, 20);
		panel.add(lblDataInicial);

		JLabel lblDataFinal = new JLabel("Data Final: ");
		lblDataFinal.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDataFinal.setBounds(10, 122, 88, 20);
		panel.add(lblDataFinal);

		dtInicial = new JDateChooser();
		dtInicial.setDateFormatString("dd/MM/yyyy");

		dtInicial.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("date".equals(evt.getPropertyName())) {
					dtFinal.setEnabled(true);
					validarData();
				}
			}
		});
		dtInicial.setBounds(92, 85, 114, 20);
		panel.add(dtInicial);

		dtFinal = new JDateChooser();
		dtFinal.setEnabled(false);
		dtFinal.setDateFormatString("dd/MM/yyyy");

		dtFinal.setBounds(92, 122, 114, 20);
		panel.add(dtFinal);

		JLabel lblCliente = new JLabel("Cliente : ");
		lblCliente.setBounds(10, 11, 57, 18);
		panel.add(lblCliente);
		lblCliente.setFont(new Font("Arial", Font.PLAIN, 15));

		JLabel lblTecnico = new JLabel("Tecnico : ");
		lblTecnico.setBounds(10, 48, 64, 18);
		panel.add(lblTecnico);
		lblTecnico.setFont(new Font("Arial", Font.PLAIN, 15));

		cbTecnico = new JComboBox<String>();
		cbTecnico.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTecnico.setBounds(92, 47, 186, 20);
		try {
			cbTecnico.removeAllItems();
			cbTecnico.addItem("TODOS");
			for (TecnicoVO t : new TecnicoController().read()) {
				cbTecnico.addItem(t.getTecnico());
			}
		} catch (JsonSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cbTecnico.setSelectedIndex(0);
		panel.add(cbTecnico);

		txtCliente = new JTextField();
		txtCliente.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent evt) {
				if (txtCliente.getText().isEmpty()) {
					lblTodos.setVisible(true);

				} else {
					lblTodos.setVisible(false);

				}
			}

			@Override
			public void focusGained(FocusEvent evt) {

				lblTodos.setVisible(false);
			}
		});
		txtCliente.setBounds(92, 11, 186, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		JButton btnDd = new JButton("Gerar Relatorio");
		btnDd.setBounds(20, 230, 145, 28);
		panelPrincipal.add(btnDd);
		btnDd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (obrigadorio()) {

					final ViewAguardaRelatorio viewAguardaRelatorio = new ViewAguardaRelatorio();
					viewAguardaRelatorio.setVisible(true);
					Thread relatorio = new Thread(new Runnable() {
						public void run() {
							try {
								Connection connection = ConnectionFactory.getConnection();
								JasperPrint jasperPrint = gerarRelatorio(src, connection);
								JasperViewer jasperViewer = new JasperViewer(jasperPrint, true);

								jasperViewer.setTitle("Relatorio de Chamados");
								jasperViewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
								viewAguardaRelatorio.dispose();
								jasperViewer.setVisible(true);
								connection.close();
							} catch (NullPointerException e) {
								viewAguardaRelatorio.dispose();

								JOptionPane.showMessageDialog(null, "Não encontramos chamados no periodo selecionado",
										getTitle(), JOptionPane.INFORMATION_MESSAGE);
							} catch (JRException e) {
								viewAguardaRelatorio.dispose();

								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								viewAguardaRelatorio.dispose();
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (MalformedURLException e) {
								viewAguardaRelatorio.dispose();
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								viewAguardaRelatorio.dispose();
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							}
						}
					});
					relatorio.start();
				}
			}

		});

		btnDd.setFont(new Font("Arial", Font.PLAIN, 15));

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(175, 230, 131, 28);
		panelPrincipal.add(btnImprimir);
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (obrigadorio()) {
					JFileChooser choice = new JFileChooser();
					final ViewAguardaRelatorio viewAguardaRelatorio = new ViewAguardaRelatorio();
					viewAguardaRelatorio.setVisible(true);
					Thread relatorio = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Connection connection = ConnectionFactory.getConnection();

								JasperPrint jasperPrint = gerarRelatorio(src, connection);
								File caminho = javax.swing.filechooser.FileSystemView.getFileSystemView()
										.getDefaultDirectory();
								choice.setCurrentDirectory(new File(caminho.getPath()));
								choice.setFileSelectionMode(1);
								choice.setDialogTitle("Relatorio Chamado");
								choice.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
								FileFilter pdf = new FileNameExtensionFilter("Arquivo Pdf (.pdf)", "pdf");
								choice.addChoosableFileFilter(pdf);
								choice.setFileFilter(pdf);
								choice.setAcceptAllFileFilterUsed(false);
								viewAguardaRelatorio.dispose();
								int option = choice.showSaveDialog(null);
								if (option == JFileChooser.APPROVE_OPTION) {
									JasperExportManager.exportReportToPdfFile(jasperPrint,
											choice.getSelectedFile().getPath() + ".pdf");
									Desktop desktop = Desktop.getDesktop();
									try {
										if (Impressao.detectaImpressoras() == null) {
											util.Log.logWarn("Tentativa de imprimir um relatório sem uma impressora.",
													getClass());
											JOptionPane.showMessageDialog(null,
													"Nennhuma impressora foi encontrada. Instale uma impressora padrão.",
													getTitle(), JOptionPane.INFORMATION_MESSAGE);
										} else {
											desktop.print(new File(choice.getSelectedFile().getPath() + ".pdf"));

										}
									} catch (NullPointerException e) {
										util.Log.logErro(e.getMessage(), e.getCause(), getClass());
										JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
												JOptionPane.ERROR_MESSAGE);

									} catch (IllegalArgumentException e) {
										util.Log.logErro(e.getMessage(), e.getCause(), getClass());
										JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
												JOptionPane.ERROR_MESSAGE);
									} catch (UnsupportedOperationException e) {
										util.Log.logErro(e.getMessage(), e.getCause(), getClass());
										JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
												JOptionPane.ERROR_MESSAGE);
									} catch (IOException e) {
										util.Log.logErro(e.getMessage(), e.getCause(), getClass());
										JOptionPane.showMessageDialog(null, e.getMessage() + " - " + e.getCause(),
												getTitle(), JOptionPane.ERROR_MESSAGE);
									}

								} else {
									JOptionPane.showMessageDialog(null, "Operação cancelada", getTitle(),
											JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (NullPointerException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, "Não encontramos chamados no periodo selecionado",
										getTitle(), JOptionPane.INFORMATION_MESSAGE);
							} catch (JRException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (MalformedURLException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (ClassNotFoundException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							} catch (SQLException e) {
								util.Log.logErro(e.getMessage(), e.getCause(), getClass());
								JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
										JOptionPane.ERROR_MESSAGE);
							}
						}
					});
					relatorio.start();
				}
			}
		});
		btnImprimir.setFont(new Font("Arial", Font.PLAIN, 15));

		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(330, 230, 131, 28);
		panelPrincipal.add(btnSair);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		btnSair.setFont(new Font("Arial", Font.PLAIN, 15));
		validarData();
	}

	private boolean obrigadorio() {

		if (dtInicial.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma data inicial do relatorio", getTitle(),
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (dtFinal.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Selecione uma data final do relatorio", getTitle(),
					JOptionPane.INFORMATION_MESSAGE);

			return false;

		}

		return true;
	}

	private void validarData() {

		Calendar maxInicial = Calendar.getInstance();
		maxInicial.add(Calendar.DAY_OF_MONTH, -2);

		System.out.println(maxInicial.getTime());
		dtInicial.setMaxSelectableDate(maxInicial.getTime());
		if (dtInicial.getDate() != null) {
			Calendar maxInicialFinal = dtInicial.getCalendar();
			maxInicialFinal.add(Calendar.DAY_OF_MONTH, 2);
			dtFinal.setMinSelectableDate(maxInicialFinal.getTime());
			dtFinal.setMaxSelectableDate(new java.util.Date());
		}
	}

	private static File logo() throws IOException {
		File file = new File(Constantes.getLocalArquivos() + "\\logo.png");
		if (!file.exists()) {
			ArquivoSistema2.download();
		}

		return file;
	}

	private JasperPrint gerarRelatorio(String src, Connection connection)
			throws ParseException, IOException, JRException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String auxInicial = dateFormat.format(dtInicial.getDate());
		String auxFinal = dateFormat.format(dtFinal.getDate());

		Date dataInicial = new Date(dateFormat.parse(auxInicial).getTime());
		Date dataFinal = new Date(dateFormat.parse(auxFinal).getTime());

		File file = logo();

		System.out.println(file.getPath());

		Map<String, Object> params = new HashMap<>();
		params.put("pDatainicia", dataInicial);
		params.put("pDatafinal", dataFinal);
		params.put("pNomeCliente", "%" + txtCliente.getText() + "%");
		if (!cbTecnico.getSelectedItem().equals("TODOS")) {
			params.put("pTecnico", "%" + String.valueOf(cbTecnico.getSelectedItem()) + "%");
		} else {
			params.put("pTecnico", "%%");

		}
		params.put("imagesDir", file);

		JasperPrint jasperPrint = JasperFillManager.fillReport(src, params, connection);
		return jasperPrint;
	}
}
