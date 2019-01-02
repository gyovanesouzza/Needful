package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonSyntaxException;

import controller.EstoqueController;
import util.Constantes;
import util.LookAndFeel;
import validator.DocumentoNomeDoProduto;
import validator.DocumentoPrecoMaterial;
import validator.DocumentoQuantidadeMaterial;
import vo.EstoqueVO;
import vo.StatusVO;

public class ViewEstoqueAlterar extends JDialog {

	private static final long serialVersionUID = 7818044942393224821L;

	EstoqueController controller = new EstoqueController();

	private final JPanel contentPanel = new JPanel();
	private JTextField txtMaterial;
	private JTextField txtQuantidade;
	private JFormattedTextField txtPreco;
	private JTextField txtEstoqueIniciar;
	private JComboBox<String> cbTipoQuantidade;
	private JTextField txtCodigo;
	EstoqueVO estoqueVO = new EstoqueVO();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeel.Layout();
		try {
			EstoqueVO vo = new EstoqueVO();
			ViewEstoqueAlterar dialog = new ViewEstoqueAlterar(vo);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws IOException
	 * @throws SocketException
	 * @throws JsonSyntaxException
	 */
	public ViewEstoqueAlterar(EstoqueVO estoquevo) throws JsonSyntaxException, SocketException, IOException {
		setType(Type.UTILITY);
		setTitle("Estoque ");
		initComponent();
		setLocationRelativeTo(null);
		dadosNoCampo(estoquevo);
		autalizarFechar();
	}

	private void initComponent() {
		setModal(true);
		setBounds(100, 100, 450, 283);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblCodigo.setBounds(16, 21, 54, 20);
		contentPanel.add(lblCodigo);

		JLabel lblMaterial = new JLabel("Produto: ");
		lblMaterial.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMaterial.setBounds(16, 58, 75, 20);
		contentPanel.add(lblMaterial);

		JLabel lblQuatidade = new JLabel("Quantidade: ");
		lblQuatidade.setFont(new Font("Arial", Font.PLAIN, 15));
		lblQuatidade.setBounds(16, 95, 104, 20);
		contentPanel.add(lblQuatidade);

		JLabel lblPreo = new JLabel("Pre\u00E7o");
		lblPreo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreo.setBounds(16, 132, 39, 20);
		contentPanel.add(lblPreo);

		JLabel lblEstoqueIniciar = new JLabel("Estoque Iniciar: ");
		lblEstoqueIniciar.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEstoqueIniciar.setBounds(16, 169, 104, 20);
		contentPanel.add(lblEstoqueIniciar);

		txtCodigo = new JTextField();
		txtCodigo.setHorizontalAlignment(SwingConstants.LEFT);
		txtCodigo.setBackground(Color.LIGHT_GRAY);
		txtCodigo.setEditable(false);
		txtCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(152, 21, 104, 20);
		contentPanel.add(txtCodigo);

		txtMaterial = new JTextField();
		txtMaterial.setHorizontalAlignment(SwingConstants.LEFT);
		txtMaterial.requestFocus();
		txtMaterial.setFont(new Font("Arial", Font.PLAIN, 15));
		txtMaterial.setBounds(152, 58, 164, 20);
		contentPanel.add(txtMaterial);
		txtMaterial.setDocument(new DocumentoNomeDoProduto());
		txtMaterial.setColumns(10);

		txtQuantidade = new JTextField();
		txtQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
		txtQuantidade.setDocument(new DocumentoQuantidadeMaterial());
		txtQuantidade.setFont(new Font("Arial", Font.PLAIN, 15));
		txtQuantidade.setColumns(10);
		txtQuantidade.setBounds(152, 95, 104, 20);
		contentPanel.add(txtQuantidade);

		txtPreco = new DocumentoPrecoMaterial();
		txtPreco.setHorizontalAlignment(SwingConstants.LEFT);
		txtPreco.setFont(new Font("Arial", Font.PLAIN, 15));
		txtPreco.setColumns(10);
		txtPreco.setBounds(152, 132, 104, 20);
		contentPanel.add(txtPreco);

		txtEstoqueIniciar = new JTextField();
		txtEstoqueIniciar.setHorizontalAlignment(SwingConstants.LEFT);
		txtEstoqueIniciar.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEstoqueIniciar.setColumns(10);
		txtEstoqueIniciar.setDocument(new DocumentoQuantidadeMaterial());
		txtEstoqueIniciar.setBounds(152, 169, 104, 20);
		contentPanel.add(txtEstoqueIniciar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();

			}
		});
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCancelar.setBounds(333, 208, 91, 27);
		contentPanel.add(btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				HttpURLConnection httpURLConnection = null;

				try {
					URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) name.openConnection();
					httpURLConnection.connect();

					alterarProduto();
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
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSalvar.setBounds(213, 208, 91, 27);
		contentPanel.add(btnSalvar);

		cbTipoQuantidade = new JComboBox<String>();
		cbTipoQuantidade.setModel(new DefaultComboBoxModel<String>(new String[] { "UN", "M" }));
		cbTipoQuantidade.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTipoQuantidade.setSelectedIndex(-1);
		cbTipoQuantidade.setBounds(266, 95, 50, 20);
		contentPanel.add(cbTipoQuantidade);

	}

	public EstoqueVO poupularDados() {
		estoqueVO.setCodigo(estoqueVO.getCodigo());
		estoqueVO.setMaterial(txtMaterial.getText());
		estoqueVO.setQts(txtQuantidade.getText());
		estoqueVO.setPreco(
				new BigDecimal(txtPreco.getText().replace("R$", "").replace(".", "").replace(",", ".").trim()));
		System.out.println(estoqueVO.getPreco());
		estoqueVO.setQtsInicia(txtEstoqueIniciar.getText());
		estoqueVO.setTipo(String.valueOf(cbTipoQuantidade.getSelectedItem()));
		estoqueVO.setStatusVO(status());
		System.out.println(estoqueVO.getStatusVO().getTipo());
		return estoqueVO;
	}

	private void limpar() {

		txtEstoqueIniciar.setText("");
		txtMaterial.setText("");
		txtPreco.setText("");
		txtQuantidade.setText("");
		cbTipoQuantidade.setSelectedIndex(-1);
	}

	private void dadosNoCampo(EstoqueVO vo) throws JsonSyntaxException, SocketException, IOException {
		estoqueVO = controller.dadosNosCampos(vo);
		txtCodigo.setText(String.valueOf(estoqueVO.getCodigo()));
		txtMaterial.setText(String.valueOf(estoqueVO.getMaterial()));
		txtPreco.setText(String.valueOf(estoqueVO.getPreco()));
		txtQuantidade.setText(String.valueOf(estoqueVO.getQts()));
		cbTipoQuantidade.setSelectedItem(estoqueVO.getTipo());
		txtEstoqueIniciar.setText(String.valueOf(estoqueVO.getQtsInicia()));

	}

	private StatusVO status() {
		StatusVO statusVO = new StatusVO();

		if (Integer.parseInt(txtQuantidade.getText().trim()) > Integer.parseInt(txtEstoqueIniciar.getText().trim())) {
			statusVO.setTipo("Em Estoque");

		} else if (Integer.parseInt(txtQuantidade.getText().trim()) < Integer
				.parseInt(txtEstoqueIniciar.getText().trim())) {
			statusVO.setTipo("Em Falta");
		} else if (Integer.parseInt(txtQuantidade.getText().trim()) == Integer
				.parseInt(txtEstoqueIniciar.getText().trim())) {
			statusVO.setTipo("Estoque Baixo");

		}
		return statusVO;
	}

	private void autalizarFechar() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ViewEstoque.readSAtivado(new EstoqueVO());

			}
		});
	}

	private void alterarProduto() throws JsonSyntaxException, HeadlessException, SocketException, IOException {

		Object[] options = { "Confirmar", "Cancelar" };
		int Confirm = JOptionPane.showOptionDialog(null, "Clique Confirmar para Alterar o produto", "Informação",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (Confirm == 0) {
			if (!nulo()) {
				if (estoqueVO.getMaterial().equals(txtMaterial.getText())) {
					estoqueVO = poupularDados();

					if (!existerMaterialAtivadoUpdate(estoqueVO)) {
						if (!existerMaterialDesativadoUpdate(estoqueVO)) {

							alterar();
						} else {
							Object[] option = { "Sim", "Não" };
							int confirm = JOptionPane.showOptionDialog(null, "Desejear reativar o cadastro do produto?",
									"Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									option, option[0]);
							confirm += 1;
							if (confirm == 1) {
								estoqueVO.setStatusAD(confirm);
								if (controller.statusMaterial(estoqueVO)) {
									JOptionPane.showMessageDialog(null, "Produto ativado com sucesso", "Estoque",
											JOptionPane.PLAIN_MESSAGE);
									dispose();
								} else {
									JOptionPane.showMessageDialog(null, "erro ao ativar o produto ", "Erro",
											JOptionPane.ERROR_MESSAGE);

								}
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Material já cadastrado", "Information",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					estoqueVO = poupularDados();

					if (!existerMaterialAtivado(estoqueVO)) {
						if (!existerMaterialDesativado(estoqueVO)) {
							alterar();
						} else {
							Object[] option = { "Sim", "Não" };
							int confirm = JOptionPane.showOptionDialog(null, "Desejear reativar o cadastro do produto?",
									"Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									option, option[0]);
							confirm += 1;
							if (confirm == 1) {
								estoqueVO.setStatusAD(confirm);
								if (controller.statusMaterial(estoqueVO)) {
									JOptionPane.showMessageDialog(null, "Produto ativado com sucesso", "Estoque",
											JOptionPane.PLAIN_MESSAGE);
									dispose();
								} else {
									JOptionPane.showMessageDialog(null, "erro ao ativar o produto ", "Erro",
											JOptionPane.ERROR_MESSAGE);

								}
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Material já cadastrado", "Information",
								JOptionPane.INFORMATION_MESSAGE);

					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor preenchar todos os campos", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

	}

	private void alterar() throws SocketException, IOException {
		if (controller.alterarEstoque(estoqueVO)) {
			limpar();
			JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Estoque", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "Error ao Alterar Material!", "ERROR", JOptionPane.ERROR_MESSAGE);

		}
	}

	private boolean existerMaterialAtivadoUpdate(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {

		return controller.existerMaterialAtivadoUpdate(estoqueVO);
	}

	private boolean existerMaterialDesativadoUpdate(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {

		return controller.existerMaterialDesativadoUpdate(estoqueVO);
	}

	private boolean existerMaterialAtivado(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {

		return controller.existerMaterialAtivadoInsert(estoqueVO);
	}

	private boolean existerMaterialDesativado(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {

		return controller.existerMaterialDesativadoInsert(estoqueVO);
	}

	private boolean nulo() {
		boolean retorno = false;
		if (txtMaterial.getText().isEmpty()) {
			retorno = true;
			txtMaterial.requestFocus();
			txtMaterial.setBackground(new Color(240, 128, 128));

		}
		if (txtQuantidade.getText().isEmpty()) {
			retorno = true;
			txtQuantidade.requestFocus();
			txtQuantidade.setBackground(new Color(240, 128, 128));
		}
		if (txtEstoqueIniciar.getText().isEmpty()) {
			retorno = true;
			txtEstoqueIniciar.requestFocus();
			txtEstoqueIniciar.setBackground(new Color(240, 128, 128));
		}
		if (cbTipoQuantidade.getSelectedIndex() < 0) {
			retorno = true;
			cbTipoQuantidade.requestFocus();
			cbTipoQuantidade.setBackground(new Color(240, 128, 128));
		}

		return retorno;
	}
}
