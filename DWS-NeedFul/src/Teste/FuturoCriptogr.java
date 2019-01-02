package Teste;

import java.security.MessageDigest;

public class FuturoCriptogr {

	@SuppressWarnings("unused")
	private static String cripd(String senha) {

		char[] character = senha.toCharArray();
		String senhaCrip = "";
		char[] letras = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z' };
		char[] crip = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
				'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z', '!', '@', '#', '$', '%', '¨', '&', '*', '*', '-', '+', '/', '*', '-', 'º', '\\', '/' };
		for (int i = 0; i < character.length; i++) {

			if (String.valueOf(character[i]).equals("0")) {
				senhaCrip += crip[10];
			} else if (String.valueOf(character[i]).equals("1")) {
				senhaCrip += crip[0];
			} else if (String.valueOf(character[i]).equals("2")) {
				senhaCrip += crip[1];
			} else if (String.valueOf(character[i]).equals("3")) {
				senhaCrip += crip[5];
			} else if (String.valueOf(character[i]).equals("4")) {
				senhaCrip += crip[3];
			} else if (String.valueOf(character[i]).equals("5")) {
				senhaCrip += crip[4];
			} else if (String.valueOf(character[i]).equals("6")) {
				senhaCrip += crip[5];
			} else if (String.valueOf(character[i]).equals("7")) {
				senhaCrip += crip[9];
			} else if (String.valueOf(character[i]).equals("8")) {
				senhaCrip += crip[10];
			} else if (String.valueOf(character[i]).equals("9")) {
				senhaCrip += crip[10];
			} else if (String.valueOf(character[i]).equals("a")) {
				senhaCrip += crip[10];
			} else if (String.valueOf(character[i]).equals("b")) {
				senhaCrip += crip[10];
			} else if (String.valueOf(character[i]).equals("c")) {
				senhaCrip += crip[10];
			} else if (String.valueOf(character[i]).equals("d")) {
			} else if (String.valueOf(character[i]).equals("f")) {
			} else if (String.valueOf(character[i]).equals("g")) {
			} else if (String.valueOf(character[i]).equals("h")) {
			} else if (String.valueOf(character[i]).equals("i")) {
			} else if (String.valueOf(character[i]).equals("j")) {
			} else if (String.valueOf(character[i]).equals("k")) {
			} else if (String.valueOf(character[i]).equals("l")) {
			} else if (String.valueOf(character[i]).equals("m")) {
			} else if (String.valueOf(character[i]).equals("n")) {
			} else if (String.valueOf(character[i]).equals("o")) {
			} else if (String.valueOf(character[i]).equals("o")) {
			} else if (String.valueOf(character[i]).equals("p")) {
			} else if (String.valueOf(character[i]).equals("q")) {
			} else if (String.valueOf(character[i]).equals("r")) {
			} else if (String.valueOf(character[i]).equals("s")) {
			} else if (String.valueOf(character[i]).equals("t")) {
			} else if (String.valueOf(character[i]).equals("u")) {
			} else if (String.valueOf(character[i]).equals("v")) {
			} else if (String.valueOf(character[i]).equals("w")) {
			} else if (String.valueOf(character[i]).equals("x")) {
			} else if (String.valueOf(character[i]).equals("y")) {
			} else if (String.valueOf(character[i]).equals("z")) {
			}
		}
		return senhaCrip;

	}

	public static void main(String[] args) {
		System.out.println(cripd("01245"));
	}

}
