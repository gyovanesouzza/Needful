package view;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonSyntaxException;

import controller.ClienteController;
import tabelModel.ClienteModel;
import util.Log;
import vo.ClientVO;
import vo.EnderecoVO;

public class ViewCliente extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 399746424744918398L;
	private JPanel contentPane;
	private JTable tblCliente;
	private ClienteController controller = new ClienteController();
	private JFormattedTextField txtCliente;
	private ClientVO clientVO = new ClientVO();
	public int fechou = 1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewCliente frame = new ViewCliente(new ClientVO());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewCliente(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException {
		setModalityType(ModalityType.APPLICATION_MODAL);

		setResizable(false);
		setModal(true);
		this.clientVO = clientVO;
		initComponent();
		readTable();
	}

	private void initComponent() {
		setBounds(100, 100, 450, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);

		JLabel lblCliente = new JLabel("Cliente: ");
		lblCliente.setBounds(10, 16, 46, 14);
		contentPane.add(lblCliente);

		txtCliente = new JFormattedTextField();
		txtCliente.setToolTipText("Nome do Cliente");
		if (!clientVO.getNome().isEmpty()) {
			txtCliente.setText(clientVO.getNome());

		}
		txtCliente.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					readTable();
				} catch (SocketException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

					JOptionPane.showMessageDialog(null,
							"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(ViewCliente.class.getName()).log(Level.ALL, null, e.getCause());
					for (Window window : Window.getWindows()) {
						window.dispose();
					}
					dispose();

					new ViewLogin().setVisible(true);
				} catch (IOException e) {
					Log.logFatal(e.getMessage(), e.getCause(), getClass());
					Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());

					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					Logger.getLogger(ViewCliente.class.getName()).log(Level.ALL, null, e.getCause());

					for (Window window : Window.getWindows()) {
						window.dispose();
					}

					new ViewLogin().setVisible(true);
					dispose();

				}

			}
		});
		txtCliente.setBounds(66, 13, 206, 20);
		contentPane.add(txtCliente);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 414, 177);
		contentPane.add(scrollPane);

		tblCliente = new JTable();
		tblCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() > 1) {
					fechou = 0;

					dispose();
				}

			}
		});
		tblCliente.setModel(new ClienteModel());
		scrollPane.setViewportView(tblCliente);

		JButton btnNewButton = new JButton("Cancelar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(326, 243, 98, 26);
		contentPane.add(btnNewButton);

		JButton btnCancelar = new JButton("OK");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (tblCliente.getSelectedRow() != -1) {
					fechou = 0;

					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Selecione um cliente.");
				}
			}
		});
		btnCancelar.setBounds(212, 243, 98, 26);
		contentPane.add(btnCancelar);
	}

	private void readTable() throws JsonSyntaxException, SocketException, IOException {
		ClienteModel modelo = (ClienteModel) tblCliente.getModel();

		while (tblCliente.getRowCount() > 0)
			modelo.removeRow(0);

		for (ClientVO c : controller.listagemCliente(poupulaChamadoVO())) {
			modelo.addRow(c);
		}

	}

	private ClientVO poupulaChamadoVO() {
		clientVO.setNome(txtCliente.getText());

		return clientVO;
	}

	public ClientVO passarDados() throws JsonSyntaxException, SocketException, IOException {
		ClientVO clientVO = new ClientVO();
		EnderecoVO enderecoVO = new EnderecoVO();

		clientVO.setNome(tblCliente.getValueAt(tblCliente.getSelectedRow(), 0).toString());
		enderecoVO.setRua(tblCliente.getValueAt(tblCliente.getSelectedRow(), 1).toString());
		clientVO.setEnderecoVO(enderecoVO);

		clientVO = controller.passarDados(clientVO);

		return clientVO;
	}
}
