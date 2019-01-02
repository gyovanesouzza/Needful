package util;

import java.awt.Window;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import view.ViewLogin;
import vo.UsuarioVO;

public class EnviarEmailSenha {
	protected static String codigo;
	protected static UsuarioVO usuarioV;

	public static boolean enviarCodigo(String cod, UsuarioVO usuarioVO) {
		codigo = cod;
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
			ImageHtmlEmail email = new ImageHtmlEmail();

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
				email.setDataSourceResolver(new DataSourceUrlResolver(url));
				email.addTo(usuarioV.getEmail(), usuarioV.getNome());
				email.setFrom(Constantes.getEmailneedful(), Constantes.getNomeneedful());
				email.setSubject(Constantes.getNomeneedful());
				email.setHtmlMsg(criaCorpoEmail(codigo, Constantes.getHtmlemailtemplate()));
				email.setTextMsg("Seu cliente de email não suporta mensagens HTML");

				email.send();
				Log.logInfo("Tentativa de Recupera Senha do Login : " + usuarioV.getLogin(), getClass());
			} catch (EmailException e) {
				Log.logFatal(e.getMessage(), e.getCause(), getClass());
				Log.logInfo("Sessão encerrada forçada pelo sistema", getClass());
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

	private static String criaCorpoEmail(String cod, String img) {
		String email = "<table style=\"border-collapse: collapse; border-spacing:"
				+ "   0; min-height: 418px;\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" bgcolor=\"#f2f2f2\">"
				+ "   <tbody>" + "      <tr>" + "         <td align=\"center\" style=\"border-collapse: collapse;"
				+ "            padding-top: 30px; padding-bottom: 30px;\">"
				+ "            <table cellpadding=\"5\" cellspacing=\"5\" width=\"600\" bgcolor=\"white\" style=\"border-collapse: collapse;"
				+ "               border-spacing: 0;\">" + "               <tbody>" + "                  <tr>"
				+ "                     <td style=\"border-collapse: collapse; padding: 0px;"
				+ "                        text-align: center; width: 600px;\">"
				+ "                        <table style=\"border-collapse: collapse; border-spacing:"
				+ "                           0; box-sizing: border-box; min-height: 40px;"
				+ "                           position: relative; width: 100%;\">"
				+ "                           <tbody>" + "                              <tr>"
				+ "                                 <td style=\"border-collapse: collapse; font-family:"
				+ "                                    Arial; padding: 10px 15px; background:"
				+ "                                    #fff;\">"
				+ "                                    <table width=\"100%\" style=\"border-collapse: collapse; border-spacing:"
				+ "                                       0; font-family: Arial;\">"
				+ "                                       <tbody>" + "                                          <tr>"
				+ "                                             <td style=\"border-collapse: collapse;\">"
				+ "                                                <h2><a style=\"display: inline-block; text-decoration:"
				+ "                                                   none; box-sizing: border-box; font-family: arial;"
				+ "                                                   width: 100%; text-align: center; color:"
				+ "                                                   rgb(102,102,102); font-size: 30px; cursor: text;\" target=\"_blank\"><span style=\"font-weight: normal;"
				+ "                                                   color: #666;\"><center>Needful Suport"
				+ "                                                  </span></a>"
				+ "                                                </h2>"
				+ "                                             </td>"
				+ "                                          </tr>" + "                                       </tbody>"
				+ "                                    </table>" + "                                 </td>"
				+ "                              </tr>" + "                           </tbody>"
				+ "                        </table>" + "                        " + ""
				+ "                        <table style=\"border-collapse: collapse;"
				+ "                           border-spacing: 0; box-sizing: border-box;"
				+ "                           min-height: 40px; position: relative; width: 100%;"
				+ "                           max-width: 600px; padding-bottom: 0px;"
				+ "                           padding-left: 0px; padding-right: 0px;"
				+ "                           padding-top: 0px; text-align: center;\">"
				+ "                           <tbody>" + "                              <tr>"
				+ "                                 <td style=\"border-collapse: collapse;"
				+ "                                    font-family: Arial; padding: 0px; line-height:"
				+ "                                    0px; mso-line-height-rule: exactly;\">"
				+ "                                    <table width=\"100%\" style=\"border-collapse: collapse;"
				+ "                                       border-spacing: 0; font-family: Arial;\">"
				+ "                                       <tbody>" + "                                          <tr>"
				+ "                                             <td align=\"center\" style=\"border-collapse:"
				+ "                                                collapse; line-height: 0px; padding: 0;"
				+ "                                                mso-line-height-rule: exactly;\"><a target=\"_blank\" style=\"display: block; text-decoration: none;"
				+ "                                                box-sizing: border-box; font-family: arial; width:"
				+ "                                                100%;\">" + img + "</a></td>"
				+ "                                          </tr>" + "                                       </tbody>"
				+ "                                    </table>" + "                                 </td>"
				+ "                              </tr>" + "                           </tbody>"
				+ "                        </table>" + "                        "
				+ "                        <table style=\"border-collapse: collapse;"
				+ "                           border-spacing: 0; box-sizing: border-box;"
				+ "                           min-height: 40px; position: relative; width: 100%;"
				+ "                           font-family: Arial; font-size: 25px;"
				+ "                           padding-bottom: 20px; padding-top: 20px;"
				+ "                           text-align: center; vertical-align:"
				+ "                           middle;\">" + "                           <tbody>"
				+ "                              <tr>"
				+ "                                 <td style=\"border-collapse: collapse; font-family:"
				+ "                                    Arial; padding: 10px 15px;\">"
				+ "                                    <table width=\"100%\" style=\"border-collapse: collapse; border-spacing:"
				+ "                                       0; font-family: Arial;\">"
				+ "                                       <tbody>" + "                                          <tr>"
				+ "                                             <td style=\"border-collapse: collapse;\">"
				+ "                                                <h2 style=\"font-weight: normal; margin: 0px; padding:"
				+ "                                                   0px; color: #666; word-wrap: break-word;\"><a style=\"display: inline-block; text-decoration:"
				+ "                                                   none; box-sizing: border-box; font-family: arial;"
				+ "                                                   width: 100%; font-size: 25px; text-align: center;"
				+ "                                                   word-wrap: break-word; color: rgb(102,102,102);"
				+ "                                                   cursor: text;\" target=\"_blank\"><span style=\"font-size: inherit; text-align: center;"
				+ "                                                   width: 100%; color: #666;\"><center>CODIGO DE SEGURAÇA</span></a>"
				+ "                                                </h2>"
				+ "                                             </td>"
				+ "                                          </tr>" + "                                       </tbody>"
				+ "                                    </table>" + "                                 </td>"
				+ "                              </tr>" + "                           </tbody>"
				+ "                        </table>"
				+ "                                               <table style=\"border-collapse: collapse;"
				+ "                           border-spacing: 0; box-sizing: border-box;"
				+ "                           min-height: 40px; position: relative; width: 100%;"
				+ "                           padding-bottom: 10px; padding-top: 10px;"
				+ "                           text-align: center;\">" + "                           <tbody>"
				+ "                              <tr>"
				+ "                                 <td style=\"border-collapse: collapse; font-family:"
				+ "                                    Arial; padding: 10px 15px;\">"
				+ "                                    <div style=\"font-family: Arial; text-align:"
				+ "                                       center;\">"
				+ "                                       <table style=\"border-collapse:"
				+ "                                          collapse; border-spacing: 0; background-color:"
				+ "                                          rgb(64,190,255); border-radius: 10px; color:"
				+ "                                          rgb(255,255,255); display: inline-block;"
				+ "                                          font-family: Arial; font-size: 15px; font-weight:"
				+ "                                          bold; text-align: center;\">"
				+ "                                          <tbody style=\"display:"
				+ "                                             inline-block;\">"
				+ "                                             <tr style=\"display:"
				+ "                                                inline-block;\">"
				+ "                                                <td align=\"center\" style=\"border-collapse: collapse; display:"
				+ "                                                   inline-block; padding: 15px 20px;\"><a target=\"_blank\" style=\"display: inline-block;"
				+ "                                                   text-decoration: none; box-sizing: border-box;"
				+ "                                                   font-family: arial; color: #fff; font-size: 15px;"
				+ "                                                   font-weight: bold; margin: 0px; padding: 0px;"
				+ "                                                   text-align: center; word-wrap: break-word; width:"
				+ "                                                   100%; cursor: text;\">" + cod + "</a>"
				+ "                                                </td>"
				+ "                                             </tr>"
				+ "                                          </tbody>"
				+ "                                       </table>" + "                                    </div>"
				+ "                                 </td>" + "                              </tr>"
				+ "                           </tbody>" + "                        </table>"
				+ "						" + "                        " + "         "
				+ "    <table style=\"border-collapse: collapse;"
				+ "                           border-spacing: 0; box-sizing: border-box;"
				+ "                           min-height: 40px; position: relative; width:"
				+ "                           100%;\">" + "                           <tbody>"
				+ "                              <tr>" + "                                 <td style=\"border-collapse:"
				+ "                                    collapse; font-family: Arial; padding: 10px"
				+ "                                    15px;\">"
				+ "                                    <table width=\"100%\" style=\"border-collapse: collapse; border-spacing:"
				+ "                                       0; text-align: left; font-family:"
				+ "                                       Arial;\">" + "                                       <tbody>"
				+ "                                          <tr>"
				+ "                                             <td style=\"border-collapse:"
				+ "                                                collapse;\">"
				+ "                                                <div style=\"font-family: Arial;"
				+ "                                                   font-size: 15px; font-weight: normal; line-height:"
				+ "                                                   170%; text-align: left; color: rgb(120,113,99);"
				+ "                                                   word-wrap: break-word;\">"
				+ "                                                   <div style=\"text-align: center; color: rgb(120,113,99);\"><span style=\"line-height: 0; display: none; color:"
				+ "                                                      rgb(120,113,99);\"></span>Se você estiver com algum problema relacionado ao Software, Entre em contato conosco!"
				+ "                                                   </div>"
				+ "                                                 	                                                     <a style=\"text-align: center;display:block; color:"
				+ "                                                      rgb(120,113,99);\" href=\"needfulsuport@gmail.com\">"
				+ "                                                      <center>needfulsuport@gmail.com"
				+ "                                                       </a>"
				+ "                                                </div>"
				+ "                                             </td>"
				+ "                                          </tr>" + "                                       </tbody>"
				+ "                                    </table>" + "                                 </td>"
				+ "                              </tr>" + "                           </tbody>"
				+ "                        </table>";
		return email;
	}

}
