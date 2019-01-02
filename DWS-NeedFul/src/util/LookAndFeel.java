package util;

import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

public class LookAndFeel {

	public static void Layout() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		} catch (ClassNotFoundException e) {
			Log.logFatal(e.getMessage(), e.getCause(), LookAndFeel.class);
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (InstantiationException e) {
			Log.logFatal(e.getMessage(), e.getCause(), LookAndFeel.class);
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IllegalAccessException e) {
			Log.logFatal(e.getMessage(), e.getCause(), LookAndFeel.class);
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (UnsupportedLookAndFeelException e) {
			Log.logFatal(e.getMessage(), e.getCause(), LookAndFeel.class);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		Properties props = new Properties();
		/* Personalização do look jTatoo */
		props.put("logoString", "USB");

		AluminiumLookAndFeel.setCurrentTheme(props);

	}

}

/*
 * COLA NA STRING lookAndFeel salva e executa a tela
 * 
 * 
 * com.jtattoo.plaf.mcwin.McWinLookAndFeel - Mistura de Windows com Mac
 * 
 * com.jtattoo.plaf.acryl.AcrylLookAndFeel - Bordas e alguns icones pretos
 * 
 * com.jtattoo.plaf.aero.AeroLookAndFeel - Borda Azul claro
 * 
 * com.jtattoo.plaf.bernstein.BernsteinLookAndFeel - Tudo Amarelo
 * 
 * com.jtattoo.plaf.aluminium.AluminiumLookAndFeel - Aluminio
 * 
 * com.jtattoo.plaf.fast.FastLookAndFeel - Normal estilo rapido
 * 
 * com.jtattoo.plaf.hifi.HiFiLookAndFeel - Tudo Preto com cinza
 * 
 * com.jtattoo.plaf.mint.MintLookAndFeel - Normal verde menta
 * 
 * com.jtattoo.plaf.luna.LunaLookAndFeel - Parecido com windows XP azul
 * 
 * com.jtattoo.plaf.texture.TextureLookAndFeel - cinza com textura
 * 
 * com.jtattoo.plaf.noire.NoireLookAndFeel - preto icones brancos
 * 
 * com.jtattoo.plaf.smart.SmartLookAndFeel - padrao com amarelo claro
 * 
 * com.jtattoo.plaf.graphite.GraphiteLookAndFeel - preto e branco grafite
 */
