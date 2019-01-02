package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import util.ArquivoSistema2;
import util.Constantes;
import util.Log;
import util.LookAndFeel;
import view.ViewLogin;
import view.ViewSplash;

public class Main {

	public Main() {
		ViewSplash splash = new ViewSplash();
		splash.setUndecorated(true);
	}

	public static void main(String[] args) {
		ViewSplash splash = new ViewSplash();
		LookAndFeel.Layout();

		splash.setUndecorated(true);
		splash.setVisible(true);
		splash.setLocationRelativeTo(null);
		splash.al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (consegueConectar()) {
					if (splash.progressBar.getValue() < 100) {
						splash.progressBar.setValue(splash.progressBar.getValue() + 1);
					} else {
						splash.t.stop();
					}

					if (splash.progressBar.getValue() == 5) {
						Log.logInfo("Iniciou o Software", getClass());
					}
					if (splash.progressBar.getValue() == 15) {
						Log.logDebug("Carregando banco de dados", getClass());

						splash.lblLoading.setText("Carregando banco de dados....");
					}
					if (splash.progressBar.getValue() == 30) {
						consegueConectar();
						Log.logDebug("Conectando com o Web Services", getClass());

						splash.lblLoading.setText("Conectando com o Web Services....");
					}
					if (splash.progressBar.getValue() == 50) {
						Log.logDebug("Conectando com o servidor ICX", getClass());

						splash.lblLoading.setText("Conectando com o servidor ICX ....");
					}
					if (splash.progressBar.getValue() == 60) {
						Log.logDebug("Verificando arquivos do Sistema", getClass());

						splash.lblLoading.setText("Baixando arquivo do Sistema....");

					}
					if (splash.progressBar.getValue() == 98) {
						ArquivoSistema2.download();
						splash.lblLoading.setText("Finalizando...");

					}

					if (splash.progressBar.getValue() == 98) {
						splash.dispose();
						ViewLogin login = new ViewLogin();
						login.setVisible(true);
					}

				}
			}

		};

		splash.t = new Timer(100, splash.al);
		splash.t.start();
	}

	private static boolean consegueConectar() {
		HttpURLConnection con = null;
		try {

			URL url = new URL("http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/");
			con = (HttpURLConnection) url.openConnection();
			con.connect();
			return true;

		} catch (IOException e) {
			Log.logFatal(e.getMessage(), e.getCause(), Main.class);
			Log.logWarn("Sistema foi encerrado por uma falha", Main.class);
			
			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return false;

		} finally {
			con.disconnect();
		}
	}
}
