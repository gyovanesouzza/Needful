package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class ArquivoSistema2 {
	private final static String LOCAL = System.getenv("LOCALAPPDATA");
	private final static String PASTA = "\\needful\\Arquivos";

	private final static File RELATORIO_CHAMADO_GERAIS = new File(LOCAL + PASTA + "\\relatorioChamadoGerais.jasper");
	private final static File RELATORIO_ESTOQUE = new File(LOCAL + PASTA + "\\RelatorioEstoque.jasper");
	private final static File LOGO_USB = new File(LOCAL + PASTA + "\\logo.png");
	private final static File MANUAL = new File(LOCAL + PASTA + "\\manual.pdf");
	private final static File ARQUIVOS_NEED = new File(LOCAL + PASTA + "\\ArquivosNeed.zip");

	public static void download() {

		File pasta = new File(LOCAL + PASTA);
		try {
			if (!pasta.exists()) {
				Log.logWarn("Pasta do sistema não existe", ArquivoSistema2.class);
				pasta.mkdirs();
			}
			if (faltarArquvio()) {
				util.Log.logInfo("Baixando arquivo do Sistema", ArquivoSistema2.class);
				FileUtils.deleteDirectory(pasta);
				URL arquivos = new URL(
						"https://s3-sa-east-1.amazonaws.com/needful-bucket/ArquivosNeed.zip?versionId=null");
				FileUtils.copyURLToFile(arquivos, ARQUIVOS_NEED);
				InputStream in = new BufferedInputStream(new FileInputStream(ARQUIVOS_NEED));
				ZipInputStream zin = new ZipInputStream(in);
				ZipEntry e;
				while ((e = zin.getNextEntry()) != null) {
					Log.logDebug("descompactando arquivo do sistema : " + e.getName(), ArquivoSistema2.class);
					unzip(zin, pasta.getPath() + "\\" + e.getName());
				}
				zin.close();
				ARQUIVOS_NEED.delete();
			}
		} catch (SocketTimeoutException e1) {
			Log.logErro(e1.getMessage(), e1.getCause(), ArquivoSistema2.class);
			Log.logInfo("Sessão encerrada forçada pelo sistema", ArquivoSistema2.class);
			JOptionPane.showMessageDialog(null, "Erro : Conexão muito lenta.");
			System.gc();
			System.exit(0);
		} catch (SocketException e) {
			Log.logFatal(e.getMessage(), e.getCause(), ArquivoSistema2.class);
			Log.logInfo("Sessão encerrada forçada pelo sistema", ArquivoSistema2.class);
			JOptionPane.showMessageDialog(null,
					"Falha ao conectar com o Web Service Needful, Por favor verifique sua Internet", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.gc();
			System.exit(0);
		} catch (IOException e) {
			Log.logFatal(e.getMessage(), e.getCause(), ArquivoSistema2.class);
			Log.logInfo("Sessão encerrada forçada pelo sistema", ArquivoSistema2.class);
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
			System.gc();
			System.exit(0);
		}

	}

	private static boolean faltarArquvio() {
		if (!RELATORIO_CHAMADO_GERAIS.exists()) {
			Log.logWarn("Arquivo Relatorio de Chamados Gerais não existe", ArquivoSistema2.class);
		}
		if (!LOGO_USB.exists()) {
			Log.logWarn("Arquivo Logo não existe", ArquivoSistema2.class);
		}
		if (!RELATORIO_ESTOQUE.exists()) {
			Log.logWarn("Arquivo Relatorio de estoque não existe", ArquivoSistema2.class);
		}
		if (!MANUAL.exists()) {
			Log.logWarn("Manual não existe", ArquivoSistema2.class);
			try {
				MANUAL.createNewFile();
			} catch (IOException e) {
			}
		}
		if (!RELATORIO_CHAMADO_GERAIS.exists()) {
			return true;
		}
		if (!LOGO_USB.exists()) {
			return true;
		}
		if (!RELATORIO_ESTOQUE.exists()) {
			return true;
		}
		return false;
	}

	public static void unzip(ZipInputStream zin, String s) throws IOException {

		FileOutputStream out = new FileOutputStream(s);
		byte[] b = new byte[512];
		int len = 0;
		while ((len = zin.read(b)) != -1) {
			out.write(b, 0, len);
		}
		out.close();
	}

}
