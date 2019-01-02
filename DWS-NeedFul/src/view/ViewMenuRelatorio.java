package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Font;

public class ViewMenuRelatorio extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3023213434188326118L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ViewMenuRelatorio dialog = new ViewMenuRelatorio();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ViewMenuRelatorio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewMenuRelatorio.class.getResource("/imgs/RSS.png")));
		setModal(true);
		setResizable(false);
		setTitle("Op\u00E7\u00F5es de Relatorio");
		initComponent();
	}

	private void initComponent() {
		setBounds(100, 100, 249, 170);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 243, 142);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JButton btnRelatorioChamado = new JButton("Relatorio Chamado");
		btnRelatorioChamado.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRelatorioChamado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ViewRelatorioChamados viewRelatorios = new ViewRelatorioChamados();
				dispose();
				viewRelatorios.setVisible(true);
			}
		});
		btnRelatorioChamado.setBounds(42, 36, 159, 27);
		contentPanel.add(btnRelatorioChamado);

		JButton btnRelatorioEstoque = new JButton("Relatorio Estoque");
		btnRelatorioEstoque.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRelatorioEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ViewRelatorioEstoque viewRelatorioEstoque = new ViewRelatorioEstoque();
				dispose();
				viewRelatorioEstoque.setVisible(true);
				
				
			}
		});
		btnRelatorioEstoque.setBounds(42, 83, 159, 27);
		contentPanel.add(btnRelatorioEstoque);
	}
}
