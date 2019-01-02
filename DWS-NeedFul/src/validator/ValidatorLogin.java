package validator;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ValidatorLogin {
	public boolean campoVazio(JTextField login, JPasswordField senha) {
		boolean retornoCampo = false;
		
		if (login.getText().trim().isEmpty() && (senha.getPassword().length == 0)) {
			login.setBackground(new Color(250,128,114));
			login.requestFocus();
			senha.setBackground(new Color(250,128,114));
			JOptionPane.showMessageDialog(null, "Os Campos estão Vazios", "Login e Senha Vazio",
					JOptionPane.ERROR_MESSAGE);
			retornoCampo = true;
		}

		else if (login.getText().trim().isEmpty()) {
			login.setBackground(new Color(250,128,114));
			login.requestFocus();

			JOptionPane.showMessageDialog(null, "O Campo do Login está Vazio", "Login Vazio",
					JOptionPane.ERROR_MESSAGE);
			retornoCampo = true;

		}

		else if (senha.getPassword().length == 0) {
			senha.setBackground(new Color(250,128,114));
			senha.requestFocus();

		JOptionPane.showMessageDialog(null, "O Campo do Senha está Vazio", "Senha Vazio",
					JOptionPane.ERROR_MESSAGE);
			retornoCampo = true;

		}

		return retornoCampo;

	}
}
