package util;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import controller.ChamadoController;
import vo.GraficoVO;

public class GraficoPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4668081789151914449L;

	public GraficoPrincipal() throws IOException {
		setTitle("Teste");
		setSize(600, 479);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		LookAndFeel.Layout();
		JPanel jpanel = Panel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);

	}

	public static void main(String[] args) {
		LookAndFeel.Layout();

		try {
			new GraficoPrincipal().setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static PieDataset criaDadosGrafico() throws IOException {
		ChamadoController controller = new ChamadoController();
		DefaultPieDataset pizza = new DefaultPieDataset();

		for (GraficoVO graficoVO : controller.carregarDadosGrafico()) {

			pizza.setValue(graficoVO.getTipo_chamado(), graficoVO.getQtd_chamado());

		}

		return pizza;

	}

	private static JFreeChart criaGrafico(PieDataset pizza) {

		JFreeChart chart = ChartFactory.createPieChart3D("Chamados Semanais", (PieDataset) pizza, false, true, false);
		chart.setBackgroundPaint(Color.WHITE);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setSectionPaint("Finalizado", Color.GREEN);
		plot.setSectionPaint("Aberto", Color.BLUE);
		plot.setSectionPaint("Atrasado", Color.red);
		plot.setSectionPaint("Andamento", Color.YELLOW);

		// plot.setOutlineVisible(false);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));
		plot.setLabelBackgroundPaint(new Color(220, 220, 220));
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setInteriorGap(0.10);

		return chart;
	}

	public static JPanel Panel() throws IOException {

		JFreeChart jfreechart = null;

		jfreechart = criaGrafico(criaDadosGrafico());

		return new ChartPanel(jfreechart);
	}

	/*
	 * public static JFreeChart criaGrafico() {
	 * 
	 * 
	 * plot.setStartAngle(90); plot.setDirection(Rotation.CLOCKWISE);
	 * plot.setSectionPaint("Conteúdo 1", Color.black);
	 * 
	 * plot.setInteriorGap(0.20);
	 * 
	 * // ChartPanel chartPanel = new ChartPanel(chart);
	 * 
	 * return chart; }
	 */
}
