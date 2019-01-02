package vo;

public class LogVO {
	private static String user;
	private static String acao;

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		LogVO.user = user;
	}

	public static String getAcao() {
		return acao;
	}

	public static void setAcao(String acao) {
		LogVO.acao = acao;
	}

}
