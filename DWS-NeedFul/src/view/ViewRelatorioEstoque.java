package view;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

import connection.ConnectionFactory;
import controller.UsuarioController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import util.ArquivoSistema2;
import util.Constantes;
import util.EnviarEmailRelatorio;
import util.Impressao;
import util.Log;
import vo.UsuarioVO;

public class ViewRelatorioEstoque extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2143248774055596293L;
	private final JPanel contentPanel = new JPanel();
	String src = Constantes.getLocalArquivos() + "\\relatorioEstoque.jasper";

	String iconPath = Constantes.getLocalArquivos() + "\\logo.png";
	File relatorio = new File(Constantes.getLocalArquivos() + "\\relatorio.html");

	public static void main(String[] args) {
		try {
			ViewRelatorioEstoque dialog = new ViewRelatorioEstoque();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewRelatorioEstoque() {
		initComponet();
		setLocationRelativeTo(null);
	}

	private void initComponet() {
		setTitle("Relatorio Estoque");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewRelatorioEstoque.class.getResource("/imgs/RSS.png")));
		setBounds(100, 100, 525, 108);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 509, 69);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JButton btnGerarRelatorio = new JButton("Gerar Relatorio ");
		btnGerarRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				final ViewAguardaRelatorio viewAguardaRelatorio = new ViewAguardaRelatorio();
				viewAguardaRelatorio.setVisible(true);
				Thread relatorio = new Thread(new Runnable() {
					public void run() {
						try {
							Connection connection = ConnectionFactory.getConnection();
							JasperPrint jasperPrint = gerarRelatorio(src, connection);
							JasperViewer jasperViewer = new JasperViewer(jasperPrint, true);

							jasperViewer.setTitle("Relatorio de Estoque");
							jasperViewer.setExtendedState(JFrame.MAXIMIZED_BOTH);
							viewAguardaRelatorio.dispose();
							jasperViewer.setVisible(true);

						} catch (NullPointerException e) {
							viewAguardaRelatorio.dispose();
							JOptionPane.showMessageDialog(null, "Não encontramos produtos no Estoque", getTitle(),
									JOptionPane.INFORMATION_MESSAGE);
						} catch (JRException e) {
							viewAguardaRelatorio.dispose();
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							viewAguardaRelatorio.dispose();
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (MalformedURLException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							viewAguardaRelatorio.dispose();
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							viewAguardaRelatorio.dispose();
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (Exception e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							viewAguardaRelatorio.dispose();
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				relatorio.start();
			}

		});
		btnGerarRelatorio.setBounds(29, 22, 121, 23);
		contentPanel.add(btnGerarRelatorio);

		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

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
							choice.setDialogTitle("Relatorio Estoque");
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

								final ViewAguardaImprimido viewAguardaImprimido = new ViewAguardaImprimido();
								viewAguardaImprimido.setVisible(true);

								Thread imprimirTh = new Thread(new Runnable() {

									@Override
									public void run() {
										Desktop desktop = Desktop.getDesktop();
										try {

											if (Impressao.detectaImpressoras() == null) {
												viewAguardaImprimido.dispose();
												JOptionPane.showMessageDialog(null,
														"Nennhuma impressora foi encontrada. Instale uma impressora padrão.",
														getTitle(), JOptionPane.INFORMATION_MESSAGE);
											} else {
												viewAguardaImprimido.dispose();
												desktop.print(new File(choice.getSelectedFile().getPath() + ".pdf"));
											}
										} catch (NullPointerException e) {
											Log.logErro(e.getMessage(), e.getCause(), getClass());
											viewAguardaImprimido.dispose();
											JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
													JOptionPane.ERROR_MESSAGE);
										} catch (IllegalArgumentException e) {
											Log.logErro(e.getMessage(), e.getCause(), getClass());
											viewAguardaImprimido.dispose();
											JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
													JOptionPane.ERROR_MESSAGE);
										} catch (UnsupportedOperationException e) {
											Log.logErro(e.getMessage(), e.getCause(), getClass());
											viewAguardaImprimido.dispose();
											JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(),
													JOptionPane.ERROR_MESSAGE);
										} catch (IOException e) {
											Log.logErro(e.getMessage(), e.getCause(), getClass());
											viewAguardaImprimido.dispose();
											JOptionPane.showMessageDialog(null, e.getMessage() + " - " + e.getCause(),
													getTitle(), JOptionPane.ERROR_MESSAGE);
										}

									}
								});
								imprimirTh.start();
							} else {
								JOptionPane.showMessageDialog(null, "Operação cancelada", getTitle(),
										JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (NullPointerException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, "Não encontramos produtos no esotque", getTitle(),
									JOptionPane.INFORMATION_MESSAGE);
						} catch (JRException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (MalformedURLException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (ClassNotFoundException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						} catch (SQLException e) {
							Log.logErro(e.getMessage(), e.getCause(), getClass());
							JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
						}
					}

				});
				relatorio.start();

			}
		});
		btnImprimir.setBounds(194, 22, 99, 23);
		contentPanel.add(btnImprimir);

		JButton btnEnviarPorEmail = new JButton("Enviar por E-Mail");
		btnEnviarPorEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Connection connection = ConnectionFactory.getConnection();

					JasperPrint jasperPrint = gerarRelatorio(src, connection);
					Map<String, String> images = new HashMap<>();

					JasperExportManager.exportReportToHtmlFile(jasperPrint,
							Constantes.getLocalArquivos() + "\\relatorio_e_mail.html");

					HtmlExporter exporterHTML = new HtmlExporter();
					SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
					exporterHTML.setExporterInput(exporterInput);
					SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();

					exporterHTML.setConfiguration(reportExportConfiguration);
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

					SimpleHtmlExporterOutput simpleHtmlExporterOutput = new SimpleHtmlExporterOutput(outputStream);
					simpleHtmlExporterOutput.setImageHandler(new HtmlResourceHandler() {

						@Override
						public void handleResource(String id, byte[] data) {
							images.put(id,
									"data:image/png;base64," + com.itextpdf.text.pdf.codec.Base64.encodeBytes(data));
						}

						@Override
						public String getResourcePath(String id) {
							return images.get(id);
						}
					});
					exporterHTML.setExporterOutput(simpleHtmlExporterOutput);

					exporterHTML.exportReport();
					FileUtils.writeByteArrayToFile(relatorio, outputStream.toByteArray());
					String conteudo = FileUtils.readFileToString(relatorio, "UTF-8");

					List<UsuarioVO> usuarioV = new UsuarioController().listadeAdm();

					EnviarEmailRelatorio.enviarCodigo(conteudo, usuarioV);
				} catch (ParseException | IOException | JRException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					Log.logErro(e.getMessage(), e.getCause(), getClass());
					JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEnviarPorEmail.setBounds(347, 22, 127, 23);
		contentPanel.add(btnEnviarPorEmail);
	}

	private JasperPrint gerarRelatorio(String src, Connection connection)
			throws ParseException, IOException, JRException {
		
		File file = logo();
		Map<String, Object> params = new HashMap<>();
		params.put("imagesDir", file);

		JasperPrint jasperPrint = JasperFillManager.fillReport(src, params, connection);
		return jasperPrint;
	}

	private static File logo() throws IOException {
		File file = new File(Constantes.getLocalArquivos() + "\\logo.png");
		if (!file.exists()) {
			ArquivoSistema2.download();
		}

		return file;
	}
}
