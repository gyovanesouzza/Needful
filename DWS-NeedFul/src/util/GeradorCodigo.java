package util;

import java.util.Random;

public class GeradorCodigo {

	public static String geradorCodigo() {
		String codigo = "";
		Random gerador = new Random();
		char[] letras = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };

		for (int i = 0; i <= 15; i++) {
			if (codigo.length() != 15) {
				codigo += letras[gerador.nextInt(62)];
			}
		}
		return codigo;
	}
}
