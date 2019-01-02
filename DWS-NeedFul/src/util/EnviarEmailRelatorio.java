package util;

import java.awt.Window;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import view.ViewLogin;
import vo.UsuarioVO;

public class EnviarEmailRelatorio {
	static String mensagem = null;
	protected static List<UsuarioVO> usuarioV;
	static File relatorio = new File(Constantes.getLocalArquivos() + "\\relatorio.html");

	public static boolean enviarCodigo(String mensaegem, List<UsuarioVO> usuarioVO) {
		mensagem = mensaegem;
		usuarioV = usuarioVO;
		boolean retorno = false;
		Thread thread = new Thread(enviar);
		thread.start();

		return retorno;
	}

	private static Runnable enviar = new Runnable() {

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			// ImageHtmlEmail email = new ImageHtmlEmail();
			HtmlEmail email = new HtmlEmail();

			try {
				URL url = new URL("http://www.apache.org");

				email.setHostName(Constantes.getServidorSmtp());
				email.setSmtpPort(Constantes.getPortaSmtp());
				email.setAuthenticator(new DefaultAuthenticator(Constantes.getEmailneedful(), Constantes.getSenha()));
				email.setSSLOnConnect(true);
				email.setSSL(true);
				email.setTLS(true);
				email.setStartTLSEnabled(true);
				email.setDebug(true);
				for (UsuarioVO usuarioVO : usuarioV) {
					email.addTo(usuarioVO.getEmail(), usuarioVO.getNome());
				}
				EmailAttachment attachment = new EmailAttachment();
				attachment.setPath(relatorio.getPath());
				attachment.setDisposition("attacment");
				attachment.setName("Relatorio.html");
				email.attach(attachment);
				email.setFrom(Constantes.getEmailneedful(), Constantes.getNomeneedful());
				email.setSubject(Constantes.getNomeneedful());
				email.setHtmlMsg("Relatorio de Estoque gerando no dia " + new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(new Date()));
				email.setTextMsg("Seu cliente de email não suporta mensagens HTML");

				email.send();
			} catch (EmailException e) {
				Log.logFatal(e.getMessage(), e.getCause(), getClass());
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Não foi possivel envial o E-mail, pois Anti-virus ou Firewall está bloqueando a Porta: "
								+ email.getSmtpPort() + "\n"
								+ "Deative-o para que o sistema possa enviar o codigo de segura para o seu e-mail",
						"Error", JOptionPane.ERROR_MESSAGE);
				System.gc();
				for (Window window : Window.getWindows()) {
					window.dispose();
				}
				new ViewLogin().setVisible(true);

			} catch (MalformedURLException e) {
				Log.logErro(e.getMessage(), e.getCause(), getClass());

				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}

	};


}
