package validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField;

public class Mascara {
	Calendar calendar = Calendar.getInstance();

	public void dataAtual(JFormattedTextField campo) {

		campo.setText(new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime()));

	}

	public void horasAtual(JFormattedTextField campo) {

		Date data = new Date();

		campo.setText(new SimpleDateFormat("hh:mm").format(data));

	}

}
