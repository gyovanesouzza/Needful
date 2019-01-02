package validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DocumentoHorasAgendamento extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int off, String string, AttributeSet attr) throws BadLocationException {

		if (off < 5) {

			if (!Character.isDigit(string.charAt(string.length() - 1))) {
				return;
			} else {
				if (off == 1) {
					string = string.concat(":");

				}

				super.insertString(off, string, attr);
			}
		}

	}

}
