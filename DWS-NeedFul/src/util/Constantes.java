package util;

public class Constantes {

	private static final String TIPO_CHAMADO_INSTALACAO = "Instalação";
	private static final String TIPO_CHAMADO_MANUNTECAO = "Manuntenção";
	private static final String STATUS = "Aberto";
	private static final int IDSTATUS = 2;

	// WEB SERVICE
	private final static String IP_WEBSERVICE = "18.228.43.192";
	// private final static String IP_WEBSERVICE = "localhost";
	private final static String POST = "POST";
	private final static String PUT = "PUT";
	private final static String GET = "GET";
	private final static String DELETE = "DELETE";
	private final static String USER_AGENT = "Mozilla/5.0";
	// log4j.appender.file.File=C:\\Users\\GYOVANEPEREIRADESOUZ\\AppData\\Local\\needful\\log\\log4j-application.log

	// Servidor S3
	private final static String LINK_LOGO_USB = "https://s3-sa-east-1.amazonaws.com/needful-bucket/LogoDaUSB.png";
	private final static String LINK_RELATORIO_CHAMADO_GERAIS = "https://s3-sa-east-1.amazonaws.com/needful-bucket/LogoDaUSB.png";
	private final static String LINK_RELATORIO_ESTOQUE = "https://s3-sa-east-1.amazonaws.com/needful-bucket/LogoDaUSB.png";
	private final static String LOCAL_ARQUIVOS = System.getenv("LOCALAPPDATA") + "\\needful\\Arquivos";
	private final static String LOCAL_LOG = System.getenv("LOCALAPPDATA") + "\\needful\\log";

	// Email
	private static final String EMAILNEEDFUL = "needfulsuport@gmail.com";
	private static final String SENHA = "etec2018";
	private static final String NOMENEEDFUL = "Suporte Needful";
	private static final String htmlEmailTemplate = "<img src=\"https://s3-sa-east-1.amazonaws.com/needful-bucket/LogoDaUSB.png\" height=\"200\" width=\"451>";
	// private static final String htmlEmailTemplate = "<img
	// src=relatorioWS\\img\\logo.png height=\"200\" width=\"451>"

	// private static final String htmlEmailTemplate = "<img src=" +
	// LOCAL_ARQUIVOS
	// + "\\logo.png height=\"200\" width=\"451>";
	private static final String SERVIDOR_SMTP = "Smtp.gmail.com";
	private static final Integer PORTA_SMTP = 587;

	// Status
	private static final String FINALIZADO = "Finalizado";
	private static final String CANCELADO = "Cancelado";
	private static final String BLOQUEADO = "Bloqueado";

	public static String getTipoChamadoInstalacao() {
		return TIPO_CHAMADO_INSTALACAO;
	}

	public static String getTipoChamadoManuntecao() {
		return TIPO_CHAMADO_MANUNTECAO;
	}

	public static String getStatus() {
		return STATUS;
	}

	public static int getIdstatus() {
		return IDSTATUS;
	}

	public static String getUserAgent() {
		return USER_AGENT;
	}

	public static String getIpWebservice() {
		return IP_WEBSERVICE;
	}

	public static String getPost() {
		return POST;
	}

	public static String getPut() {
		return PUT;
	}

	public static String getGet() {
		return GET;
	}

	public static String getDelete() {
		return DELETE;
	}

	public static String getUSER_AGENT() {
		return USER_AGENT;
	}

	public static String getEmailneedful() {
		return EMAILNEEDFUL;
	}

	public static String getSenha() {
		return SENHA;
	}

	public static String getNomeneedful() {
		return NOMENEEDFUL;
	}

	public static String getHtmlemailtemplate() {
		return htmlEmailTemplate;
	}

	public static String getFinalizado() {
		return FINALIZADO;
	}

	public static String getLinkLogoUsb() {
		return LINK_LOGO_USB;
	}

	public static String getLinkRelatorioChamadoGerais() {
		return LINK_RELATORIO_CHAMADO_GERAIS;
	}

	public static String getLinkRelatorioEstoque() {
		return LINK_RELATORIO_ESTOQUE;
	}

	public static String getServidorSmtp() {
		return SERVIDOR_SMTP;
	}

	public static Integer getPortaSmtp() {
		return PORTA_SMTP;
	}

	public static String getLocalArquivos() {
		return LOCAL_ARQUIVOS;
	}

	public static String getLocalLog() {
		return LOCAL_LOG;
	}

	public static String getCancelado() {
		return CANCELADO;
	}

	public static String getBloqueado() {
		return BLOQUEADO;
	}

}
