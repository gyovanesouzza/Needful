package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import controller.EstoqueController;
import util.Constantes;
import util.LookAndFeel;
import validator.DocumentoNomeDoProduto;
import validator.DocumentoPrecoMaterial;
import validator.DocumentoQuantidadeMaterial;
import vo.EstoqueVO;
import vo.StatusVO;
import javax.swing.SwingConstants;

public class ViewEstoqueNovo extends JDialog {

	private static final long serialVersionUID = 7818044942393224821L;

	EstoqueController controller = new EstoqueController();

	private final JPanel contentPanel = new JPanel();
	private JTextField txtMaterial;
	private JTextField txtQuantidade;
	private JTextField txtPreco;
	private JTextField txtEstoqueIniciar;
	private JComboBox<String> cbTipoQuantidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		LookAndFeel.Layout();
		try {
			ViewEstoqueNovo dialog = new ViewEstoqueNovo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html
	 * Create the dialog.
	 */
	public ViewEstoqueNovo() {
		setModal(true);
		initComponent();
		autalizarFechar();

	}

	private void initComponent() {
		setBounds(100, 100, 450, 246);
		getContentPane().setLayout(new BorderLayout());
		setTitle("Estoque ");
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMaterial = new JLabel("Produto: ");
		lblMaterial.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMaterial.setBounds(16, 21, 75, 20);
		contentPanel.add(lblMaterial);

		JLabel lblQuatidade = new JLabel("Quantidade: ");
		lblQuatidade.setFont(new Font("Arial", Font.PLAIN, 15));
		lblQuatidade.setBounds(16, 58, 91, 20);
		contentPanel.add(lblQuatidade);

		JLabel lblPreo = new JLabel("Pre\u00E7o");
		lblPreo.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPreo.setBounds(16, 95, 39, 20);
		contentPanel.add(lblPreo);

		JLabel lblEstoqueIniciar = new JLabel("Estoque Iniciar: ");
		lblEstoqueIniciar.setFont(new Font("Arial", Font.PLAIN, 15));
		lblEstoqueIniciar.setBounds(16, 132, 104, 20);
		contentPanel.add(lblEstoqueIniciar);

		txtMaterial = new JTextField();
		txtMaterial.setHorizontalAlignment(SwingConstants.LEFT);
		txtMaterial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				txtMaterial.setBackground(new Color(255, 255, 255));

			}
		});
		txtMaterial.setFont(new Font("Arial", Font.PLAIN, 15));
		txtMaterial.setBounds(152, 21, 164, 20);
		txtMaterial.setDocument(new DocumentoNomeDoProduto());
		contentPanel.add(txtMaterial);
		txtMaterial.setColumns(10);

		txtQuantidade = new JTextField();
		txtQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
		txtQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				txtQuantidade.setBackground(new Color(255, 255, 255));

			}
		});
		txtQuantidade.setFont(new Font("Arial", Font.PLAIN, 15));
		txtQuantidade.setColumns(10);
		txtQuantidade.setDocument(new DocumentoQuantidadeMaterial());
		txtQuantidade.setBounds(152, 58, 104, 20);
		contentPanel.add(txtQuantidade);

		txtPreco = new DocumentoPrecoMaterial();
		txtPreco.setHorizontalAlignment(SwingConstants.LEFT);
		txtPreco.setFont(new Font("Arial", Font.PLAIN, 15));
		txtPreco.setColumns(10);
		txtPreco.setBounds(152, 95, 104, 20);
		contentPanel.add(txtPreco);

		txtEstoqueIniciar = new JTextField();
		txtEstoqueIniciar.setHorizontalAlignment(SwingConstants.LEFT);
		txtEstoqueIniciar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				txtEstoqueIniciar.setBackground(new Color(255, 255, 255));

			}
		});
		txtEstoqueIniciar.setFont(new Font("Arial", Font.PLAIN, 15));
		txtEstoqueIniciar.setColumns(10);
		txtEstoqueIniciar.setDocument(new DocumentoQuantidadeMaterial());

		txtEstoqueIniciar.setBounds(152, 132, 104, 20);
		contentPanel.add(txtEstoqueIniciar);

		JButton btnSalvar = new JButton("Cancelar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
		btnSalvar.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSalvar.setBounds(333, 171, 91, 27);
		contentPanel.add(btnSalvar);

		JButton button = new JButton("Salvar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				HttpURLConnection httpURLConnection = null;

				try {
					URL name = new URL("http://" + Constantes.getIpWebservice() + ":8080/");
					httpURLConnection = (HttpURLConnection) name.openConnection();
					httpURLConnection.connect();

					adcionarEstoque();
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
		button.setFont(new Font("Arial", Font.PLAIN, 15));
		button.setBounds(213, 171, 91, 27);
		contentPanel.add(button);

		cbTipoQuantidade = new JComboBox<String>();
		cbTipoQuantidade.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) {
					if (cbTipoQuantidade.getSelectedIndex() > 0) {
						cbTipoQuantidade.setBackground(new Color(255, 255, 255));
					}
				}
			}
		});
		cbTipoQuantidade.setModel(new DefaultComboBoxModel<String>(new String[] { "UN", "M" }));
		cbTipoQuantidade.setFont(new Font("Arial", Font.PLAIN, 15));
		cbTipoQuantidade.setSelectedIndex(-1);
		cbTipoQuantidade.setBounds(266, 58, 50, 20);
		contentPanel.add(cbTipoQuantidade);

	}

	public EstoqueVO poupularDados() {
		EstoqueVO estoque = new EstoqueVO();

		estoque.setMaterial(txtMaterial.getText());
		estoque.setQts(txtQuantidade.getText());
		estoque.setPreco(
				new BigDecimal(txtPreco.getText().replace("R$", "").replace(".", "").replace(",", ".").trim()));
		System.out.println(estoque.getPreco());
		estoque.setQtsInicia(txtEstoqueIniciar.getText());
		estoque.setTipo(String.valueOf(cbTipoQuantidade.getSelectedItem()));
		estoque.setStatusVO(status());
		System.out.println(new Gson().toJson(estoque));
		return estoque;
	}

	public void limpar() {
		txtEstoqueIniciar.setText("");
		txtMaterial.setText("");
		txtPreco.setText("");
		txtQuantidade.setText("");
		cbTipoQuantidade.setSelectedIndex(-1);

	}

	private StatusVO status() {
		StatusVO statusVO = new StatusVO();

		if (Integer.parseInt(txtQuantidade.getText()) > Integer.parseInt(txtEstoqueIniciar.getText())) {
			statusVO.setTipo("Em Estoque");

		} else if (Integer.parseInt(txtQuantidade.getText()) < Integer.parseInt(txtEstoqueIniciar.getText())) {
			statusVO.setTipo("Em Falta");
		} else {
			statusVO.setTipo("Estoque Baixo");

		}
		return statusVO;
	}

	private boolean existerMaterialAtivado(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {

		return controller.existerMaterialAtivadoInsert(estoqueVO);
	}

	private boolean existerMaterialDesativado(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {

		return controller.existerMaterialDesativadoInsert(estoqueVO);
	}

	private void autalizarFechar() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ViewEstoque.readSAtivado(new EstoqueVO());

			}
		});
	}

	private void adcionarEstoque() throws SocketException, IOException {
		if (!nulo()) {
			EstoqueVO estoqueVO = poupularDados();

			if (!existerMaterialAtivado(estoqueVO)) {
				if (!existerMaterialDesativado(estoqueVO)) {

					if (controller.adcionarEstoque(estoqueVO)) {
						limpar();
						JOptionPane.showMessageDialog(null, "Salvo com sucesso!", "Estoque", JOptionPane.PLAIN_MESSAGE);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao salva o produto", "Estoque",
								JOptionPane.ERROR_MESSAGE);

					}
				} else {

					Object[] options = { "Sim", "Não" };
					int confirm = JOptionPane.showOptionDialog(null, "Desejear reativar o cadastro do produto?",
							"Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
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
					} else {
						dispose();
					}

				}
			} else {
				JOptionPane.showMessageDialog(null, "Material já cadastrado", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor preenchar todos os campos", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
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
