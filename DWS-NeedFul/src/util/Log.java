package util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class Log {

	public static void logErro(String erro, Throwable t, Class<?> class1) {
		config();
		Logger log = Logger.getLogger(class1);

		log.error(erro, t);
	}

	public static void logDebug(String erro, Class<?> class1) {
		config();
		Logger log = Logger.getLogger(class1);

		log.debug(erro);
		// http://www.feltex.com.br/felix/configurar-log4j-projeto-web/
	}

	public static void logInfo(String erro, Class<?> class1) {
		config();
		Logger log = Logger.getLogger(class1);

		log.info(erro);
	}

	public static void logWarn(String erro, Class<?> class1) {
		config();
		Logger log = Logger.getLogger(class1);
		log.warn(erro);
	}

	public static void logFatal(String erro, Throwable t, Class<?> class1) {
		config();
		Logger log = Logger.getLogger(class1);
		log.fatal(erro, t);
	}

	private static void config() {
		try {

			InputStream is = Log.class.getResourceAsStream("log4j.properties");
			Properties properties = new Properties();
			properties.load(is);
			properties.setProperty("local", Constantes.getLocalLog());
			properties.setProperty("current", new SimpleDateFormat("dd-MM-yy").format(new Date()));
			org.apache.log4j.PropertyConfigurator.configure(properties);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
}
