package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;

public class ViewSplash extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -744221723492315654L;
	private JPanel contentPane;
	public JProgressBar progressBar;
	public JLabel lblLoading;
	public Timer t;
	public ActionListener al;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewSplash frame = new ViewSplash();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewSplash() {
		
		setUndecorated(true);
		setResizable(false);
		initCompotent();

	}

	private void initCompotent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 606, 320);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 102));
		panel.setBounds(0, 0, 606, 320);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblVerso = new JLabel();
		lblVerso.setText("Vers\u00E3o 2.0.1");
		lblVerso.setForeground(new Color(0, 0, 0));
		lblVerso.setFont(new Font("Century Gothic", Font.ITALIC, 24));
		lblVerso.setBounds(431, 149, 165, 31);
		panel.add(lblVerso);

		JLabel lblNeedfulSoftware = new JLabel();
		lblNeedfulSoftware.setHorizontalAlignment(SwingConstants.LEFT);
		lblNeedfulSoftware.setText("Needful Software");
		lblNeedfulSoftware.setForeground(new Color(0, 0, 0));
		lblNeedfulSoftware.setFont(new Font("Century Gothic", Font.BOLD, 36));
		lblNeedfulSoftware.setBounds(288, 78, 308, 45);
		panel.add(lblNeedfulSoftware);

		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon(ViewSplash.class.getResource("/imgs/LogoDaUSBd.png")));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(0, 0, 278, 151);
		panel.add(label_2);

		lblLoading = new JLabel();
		lblLoading.setText("Carregando ...");
		lblLoading.setForeground(new Color(0, 0, 0));
		lblLoading.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblLoading.setBounds(10, 268, 586, 19);
		panel.add(lblLoading);

		progressBar = new JProgressBar();
		progressBar.setForeground(Color.YELLOW);
		progressBar.setBackground(new Color(102, 102, 102));
		progressBar.setBounds(0, 306, 606, 14);
		panel.add(progressBar);

		JLabel label_4 = new JLabel();
		label_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		label_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		label_4.setText("X");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Century Gothic", Font.BOLD, 36));
		label_4.setBounds(572, 0, 24, 45);
		panel.add(label_4);

	}

	
}
