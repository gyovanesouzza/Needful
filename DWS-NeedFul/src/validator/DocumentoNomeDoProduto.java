package validator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DocumentoNomeDoProduto extends PlainDocument {

	private static final long serialVersionUID = 1L;

	@Override
	public void insertString(int off, String string, AttributeSet attr) throws BadLocationException {
		
		if (!Character.isLetterOrDigit (string.charAt(string.length() - 1))&& !Character.isSpaceChar((string.charAt(string.length() - 1)))) {				return;
			} else {
				super.insertString(off, string, attr);
			}

	
	}
}