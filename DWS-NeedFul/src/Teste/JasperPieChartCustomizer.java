package Teste;

import java.text.NumberFormat;
import java.util.Locale;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class JasperPieChartCustomizer implements JRChartCustomizer {
	private static final int DECIMALS = 2;
	private static final String DEFAULT_FORMAT = "{0} {1} {2}";

	public void customize(JFreeChart jFreeChart, JRChart jrChart) {
		Plot plot = jFreeChart.getPlot();
		if (!(plot instanceof PiePlot))
			return;
		PiePlot piePlot = (PiePlot) plot;
		PieSectionLabelGenerator labelGen = piePlot.getLabelGenerator();
		PieSectionLabelGenerator legendGen = piePlot.getLegendLabelGenerator();
		String labelFormat = labelGen instanceof StandardPieSectionLabelGenerator
				? ((StandardPieSectionLabelGenerator) labelGen).getLabelFormat()
				: DEFAULT_FORMAT;
		String legendFormat = legendGen instanceof StandardPieSectionLabelGenerator
				? ((StandardPieSectionLabelGenerator) legendGen).getLabelFormat()
				: DEFAULT_FORMAT;
		Locale locale = new Locale("pt");
		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
		NumberFormat percFormat = NumberFormat.getPercentInstance(locale);
		percFormat.setMinimumFractionDigits(DECIMALS);
		StandardPieSectionLabelGenerator newLabelGen = new StandardPieSectionLabelGenerator(labelFormat, numberFormat,
				percFormat);
		piePlot.setLabelGenerator(newLabelGen);
		StandardPieSectionLabelGenerator newLegendGen = new StandardPieSectionLabelGenerator(legendFormat, numberFormat,
				percFormat);
		piePlot.setLegendLabelGenerator(newLegendGen);
	}
	public static void main(String[] args) {
		
	}

}
