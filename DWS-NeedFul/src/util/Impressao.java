package util;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class Impressao {
	private static PrintService impressora;

	
	public static PrintService detectaImpressoras() {
		try {
			DocFlavor df = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
			PrintService[] ps = PrintServiceLookup.lookupPrintServices(df, null);
			for (PrintService p : ps) {
				if (p.getName() != null && p.getName().contains("hp")) {
					System.out.println("Impressora Selecionada: " + p.getName());
					System.out.println("Impressora encontrada: " + p.getName());
					impressora = p;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return impressora;
	}

	

	
}