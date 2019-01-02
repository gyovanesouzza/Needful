package validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MascaraCPF extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void insertString(int off, String string, AttributeSet attr) throws BadLocationException {

		if (off < 14) {

			if (!Character.isDigit(string.charAt(string.length() - 1))) {
				return;
			} else {

				if (off == 3) {
					string = ".".concat(string);
				}

				if (off == 6) {
					string = string.concat(".");
				}
				if (off == 10) {
					string = string.concat("-");
				}
				if (off == 13) {
					getText(0, off);
				}

				super.insertString(off, string, attr);
			}
		}

	}
}