package webService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import http.HttpClient;
import util.Constantes;
import vo.ChamadosVO;
import vo.GraficoVO;

public class WebServiceChamado {

	private Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
	private Type chamadoType = new TypeToken<ChamadosVO>() {
	}.getType();
	private Type listChamadoType = new TypeToken<List<ChamadosVO>>() {
	}.getType();
	private Type listGraficaType = new TypeToken<List<GraficoVO>>() {
	}.getType();

	private HttpClient httpClient = new HttpClient();

	private String ABRECHAMADOINSTALACAO = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/abreChamadoInstalaca/";
	private String ABRECHAMADOMANUTENCAO = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/abreChamadomanutencao/";
	private String listagemFiltro = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/listagem/";
	private String pesquisaTipoDeChamado = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/pesquisaTipoDeChamado/";

	private String FECHARCHAMADO = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/fecharChamadomanutencao/";
	private String atualizarInstalacao = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/alteraChamadoinstalacao/";
	private String atualizarmanutencao = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/alteraChamadomanutencao/";
	private String CARREGARTELADEINSTALACAO = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/carregarTelaEdicaoInstalacao/";
	private String carregarteladeManuntecao = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/carregarTelaEdicaoManutencao/";
	private String carregarDadosGrafico = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/carregarDadosGrafico/";
	private String carregarTipoChamado = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/chamados/carregarTipoChamado/";

	public boolean abreChamadoInstalacao(ChamadosVO chamadosVO) throws SocketException, IOException {
		String json = gson.toJson(chamadosVO, chamadoType);
		System.out.println(json);
		return Boolean.parseBoolean(httpClient.sendPOST(ABRECHAMADOINSTALACAO, json, Constantes.getPost()));
	}

	public boolean abreChamadoManutencao(ChamadosVO chamadosVO) throws SocketException, IOException {
		String json = gson.toJson(chamadosVO, chamadoType);
		System.out.println(json);

		return Boolean.parseBoolean(httpClient.sendPOST(ABRECHAMADOMANUTENCAO, json, Constantes.getPost()));
	}

	public List<ChamadosVO> listagempost(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create();
		String json = gson.toJson(chamadosVO);
		String retorno = httpClient.sendPOST(listagemFiltro, json, Constantes.getPost());
		return gson.fromJson(retorno, listChamadoType);

	}

	public boolean fecharChamado(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(chamadosVO);
		System.out.println(json);

		return Boolean.parseBoolean(httpClient.sendPUT(FECHARCHAMADO, json, Constantes.getPut()));

	}

	public String pesquisaTipoDeChamado(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(chamadosVO);
		System.out.println(json);

		return httpClient.sendPOST(pesquisaTipoDeChamado, json, Constantes.getPost());
	}

	public ChamadosVO carregarTelaEdicaoInstalacao(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {

		String json = gson.toJson(chamadosVO);
		System.out.println(json);

		return gson.fromJson(httpClient.sendPOST(CARREGARTELADEINSTALACAO, json, Constantes.getPost()), chamadoType);
	}

	public ChamadosVO carregarteladeManuntecao(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(chamadosVO);
		System.out.println(json);

		return gson.fromJson(httpClient.sendPOST(carregarteladeManuntecao, json, Constantes.getPost()), chamadoType);
	}

	public List<GraficoVO> carregarDadosGrafico() throws JsonSyntaxException, SocketException, IOException {
		return gson.fromJson(httpClient.sendGET(carregarDadosGrafico, Constantes.getGet()), listGraficaType);
	}

	public List<ChamadosVO> carregarTipoChamado() throws JsonSyntaxException, SocketException, IOException {
		return gson.fromJson(httpClient.sendGET(carregarTipoChamado, Constantes.getGet()), listChamadoType);
	}

	public boolean atualizarInstalacao(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(chamadosVO);
		return Boolean.parseBoolean(httpClient.sendPUT(atualizarInstalacao, json, Constantes.getPut()));
	}

	public boolean atualizarmanutencao(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(chamadosVO);
		System.out.println(json);

		return Boolean.parseBoolean(httpClient.sendPUT(atualizarmanutencao, json, Constantes.getPut()));
	}
}
