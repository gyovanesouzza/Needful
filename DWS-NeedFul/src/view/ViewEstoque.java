package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import controller.EstoqueController;
import tabelModel.EstoqueTableModel;
import util.Constantes;
import util.LookAndFeel;
import validator.DocumentoNomeDoProduto;
import vo.EstoqueVO;

public class ViewEstoque extends JDialog {

	private static final long serialVersionUID = 1L;

	static EstoqueController controller = new EstoqueController();
	EstoqueTableModel tableModel = new EstoqueTableModel();
	static EstoqueVO estoqueVO = new EstoqueVO();

	private JPanel Estoque;
	private static JTable tblEstoque;
	private JLabel lblIdnome;
	private static JLabel lblMostrarMaterialDesativado;
	private JTextField txtCodigo;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private HttpURLConnection http = null;
	private JLabel lblCodigo;
	private static boolean pesquisaDesativado = false;
	int frag;

	public ViewEstoque() {
		initComponent();
		new Thread(read).start();
	}

	public static void main(String[] args) {
		LookAndFeel.Layout();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewEstoque frame = new ViewEstoque();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initComponent() {
		setResizable(false);
		setTitle("Estoque");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewEstoque.class.getResource("/imgs/RSS.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 778, 530);
		setLocationRelativeTo(null);
		setModal(true);

		Estoque = new JPanel();
		Estoque.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Estoque);

		JPanel panel = new JPanel();
		panel.setBounds(14, 51, 748, 440);
		panel.setBorder(BorderFactory.createEtchedBorder());
		Estoque.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 542, 352);

		tblEstoque = new JTable();

		tblEstoque.setFont(new Font("Arial", Font.PLAIN, 15));
		tblEstoque.setModel(tableModel);

		tblEstoque.setRowSorter(new TableRowSorter<EstoqueTableModel>(tableModel));

		scrollPane.setViewportView(tblEstoque);

		JButton btnNovo = new JButton("Novo");
		btnNovo.setBounds(617, 80, 112, 32);
		btnNovo.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					http = (HttpURLConnection) url.openConnection();
					http.connect();

					ViewEstoqueNovo estoqueNovo = new ViewEstoqueNovo();
					estoqueNovo.setVisible(true);

				} catch (SocketException e) {
					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
					System.gc();
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					new ViewLogin().setVisible(true);
				} finally {
					http.disconnect();
				}

			}
		});

		btnAlterar = new JButton("Alterar");
		btnAlterar.setBounds(617, 123, 112, 32);
		btnAlterar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (tblEstoque.getSelectedRow() != -1) {
					EstoqueVO estoqueVO = new EstoqueVO();
					estoqueVO.setCodigo(
							Integer.parseInt(tblEstoque.getValueAt(tblEstoque.getSelectedRow(), 0).toString()));

					try {
						URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
						http = (HttpURLConnection) url.openConnection();
						http.connect();

						ViewEstoqueAlterar viewEstoqueAlterar = new ViewEstoqueAlterar(estoqueVO);
						viewEstoqueAlterar.setVisible(true);

					} catch (SocketException e) {
						JOptionPane.showMessageDialog(null,
								"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
								"ERROR", JOptionPane.ERROR_MESSAGE);
						Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);

					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
								JOptionPane.ERROR_MESSAGE);
						Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
						System.gc();
						for (Window window : Window.getWindows()) {
							window.dispose();
						}
						new ViewLogin().setVisible(true);
					} finally {
						http.disconnect();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Por favor selecioner uma material para alterar", "Error",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(617, 166, 112, 32);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (tblEstoque.getSelectedRow() != -1) {

					EstoqueVO estoqueVO = new EstoqueVO();

					Object[] options = { "Confirmar", "Cancelar" };
					int Confirm = JOptionPane.showOptionDialog(null, "Clique Confirmar para excluir o produto",
							"Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);

					Confirm += 2;

					if (Confirm == 2) {
						try {
							URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
							http = (HttpURLConnection) url.openConnection();
							http.connect();

							estoqueVO.setStatusAD(Confirm);
							estoqueVO
									.setMaterial(String.valueOf(tblEstoque.getValueAt(tblEstoque.getSelectedRow(), 1)));
							if (controller.statusMaterial(estoqueVO)) {
								JOptionPane.showMessageDialog(null, "Produto excluido com sucesso", "Estoque",
										JOptionPane.PLAIN_MESSAGE);
								readJtable(pouparDados());
							} else {
								JOptionPane.showMessageDialog(null, "erro ao excluido o produto ", "Erro",
										JOptionPane.ERROR_MESSAGE);

							}

						} catch (SocketException e) {
							JOptionPane.showMessageDialog(null,
									"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet",
									"ERROR", JOptionPane.ERROR_MESSAGE);
							Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
							System.gc();
							for (Window window : Window.getWindows()) {
								window.dispose();
							}
							new ViewLogin().setVisible(true);

						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR",
									JOptionPane.ERROR_MESSAGE);
							Logger.getLogger(ViewListarChamados.class.getName()).log(Level.ALL, null, e.getCause());
							System.gc();
							for (Window window : Window.getWindows()) {
								window.dispose();
							}
							new ViewLogin().setVisible(true);
						} finally {
							http.disconnect();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um produto para excluir");
				}

			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 14));

		lblCodigo = new JLabel("Pesquisa: ");
		lblCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCodigo.setBounds(16, 11, 77, 20);

		txtCodigo = new JTextField();
		txtCodigo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent evt) {
				lblIdnome.setVisible(false);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				if (txtCodigo.getText().isEmpty()) {
					lblIdnome.setVisible(true);
				}
			}
		});
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				readJtable(pouparDados());
			}
		});
		txtCodigo.setBackground(Color.WHITE);
		txtCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		txtCodigo.setBounds(103, 11, 197, 20);
		txtCodigo.setColumns(10);
		txtCodigo.setDocument(new DocumentoNomeDoProduto());
		Estoque.setLayout(null);

		JLabel lblEstoque = new JLabel("Estoque ");
		lblEstoque.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstoque.setBounds(10, 11, 752, 29);
		lblEstoque.setFont(new Font("Arial", Font.PLAIN, 18));
		Estoque.add(lblEstoque);
		panel.add(lblCodigo);

		lblIdnome = new JLabel("ID/Nome");
		lblIdnome.setForeground(Color.LIGHT_GRAY);
		lblIdnome.setBackground(Color.LIGHT_GRAY);
		lblIdnome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIdnome.setBounds(111, 11, 162, 20);

		panel.add(lblIdnome);
		panel.add(txtCodigo);
		panel.add(scrollPane);
		panel.add(btnNovo);
		panel.add(btnAlterar);
		panel.add(btnExcluir);

		lblMostrarMaterialDesativado = new JLabel("Mostrar material desativado");
		lblMostrarMaterialDesativado.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMostrarMaterialDesativado.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblMostrarMaterialDesativado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (pesquisaDesativado) {
					pesquisaDesativado = false;

				} else {
					pesquisaDesativado = true;

				}
				readJtable(estoqueVO);

			}
		});
		lblMostrarMaterialDesativado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMostrarMaterialDesativado.setBounds(373, 416, 197, 14);
		panel.add(lblMostrarMaterialDesativado);
		tamanhoCelulaJtable();
	}

	private void tamanhoCelulaJtable() {

		tblEstoque.getColumnModel().getColumn(0).setPreferredWidth(52);
		tblEstoque.getColumnModel().getColumn(0).setMinWidth(50);
		tblEstoque.getColumnModel().getColumn(0).setMaxWidth(60);

		tblEstoque.getColumnModel().getColumn(1).setPreferredWidth(180);
		tblEstoque.getColumnModel().getColumn(1).setMinWidth(170);
		tblEstoque.getColumnModel().getColumn(1).setMaxWidth(200);

		tblEstoque.getColumnModel().getColumn(2).setPreferredWidth(75);
		tblEstoque.getColumnModel().getColumn(2).setMinWidth(70);
		tblEstoque.getColumnModel().getColumn(2).setMaxWidth(85);

		tblEstoque.getColumnModel().getColumn(3).setPreferredWidth(95);
		tblEstoque.getColumnModel().getColumn(3).setMinWidth(90);
		tblEstoque.getColumnModel().getColumn(3).setMaxWidth(110);

		tblEstoque.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblEstoque.getColumnModel().getColumn(4).setMinWidth(100);
		tblEstoque.getColumnModel().getColumn(4).setMaxWidth(105);
		tblEstoque.setAutoscrolls(true);
		tblEstoque.getTableHeader().setReorderingAllowed(false);
		tblEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	}

	private static void readJtable(EstoqueVO estoqueVO) {
		System.out.println(pesquisaDesativado);

		if (pesquisaDesativado) {
			lblMostrarMaterialDesativado.setForeground(new Color(0, 0, 205));
			lblMostrarMaterialDesativado.setText("Ocultar material desativado");
			readDesativado(estoqueVO);
		} else {
			lblMostrarMaterialDesativado.setForeground(Color.BLACK);

			lblMostrarMaterialDesativado.setText("Mostrar material desativado");
			readSAtivado(estoqueVO);

		}
	}

	public static void readSAtivado(EstoqueVO estoqueVO) {
		HttpURLConnection httpURLConnection = null;

		EstoqueTableModel modelo = (EstoqueTableModel) tblEstoque.getModel();

		try {
			URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
			httpURLConnection = (HttpURLConnection) name.openConnection();
			httpURLConnection.connect();

			while (tblEstoque.getRowCount() > 0)
				modelo.removeRow(0);
			for (EstoqueVO e : controller.pesquisaEstoquea(estoqueVO)) {
				modelo.addRow(e);
				modelo.fireTableDataChanged();

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

	private static void readDesativado(EstoqueVO estoqueVO) {
		HttpURLConnection httpURLConnection = null;

		EstoqueTableModel modelo = (EstoqueTableModel) tblEstoque.getModel();

		try {
			URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
			httpURLConnection = (HttpURLConnection) name.openConnection();
			httpURLConnection.connect();

			while (tblEstoque.getRowCount() > 0)
				modelo.removeRow(0);
			for (EstoqueVO e : controller.pesquisaEstoqueAD(estoqueVO)) {
				modelo.addRow(e);
				modelo.fireTableDataChanged();

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

	private EstoqueVO pouparDados() {
		estoqueVO.setCodigo(0);
		estoqueVO.setMaterial(null);

		if (!txtCodigo.getText().isEmpty()) {

			int t = txtCodigo.getText().length();
			Pattern buscaMaterial = Pattern.compile("[a-zA-Z]{" + t + "}");
			Pattern buscarCodigo = Pattern.compile("\\d{" + t + "}");

			if (buscaMaterial.matcher(txtCodigo.getText().substring(0, t)).matches()) {
				estoqueVO.setMaterial(txtCodigo.getText());
			} else if (buscarCodigo.matcher(txtCodigo.getText().substring(0, t)).matches()) {
				estoqueVO.setCodigo(Integer.parseInt(txtCodigo.getText()));
			}
		}
		return estoqueVO;
	}

	private static Runnable read = new Runnable() {
		public void run() {
			System.out.println(pesquisaDesativado);
			readJtable(estoqueVO);
		}
	};
}
