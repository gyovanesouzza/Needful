package webService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import http.HttpClient;
import util.Constantes;
import vo.StatusVO;

public class WebServiceStatus {
	private Gson gson = new Gson();
	private HttpClient httpClient = new HttpClient();
	private Type statusType = new TypeToken<StatusVO>() {
	}.getType();
	private Type liststatusType = new TypeToken<List<StatusVO>>() {
	}.getType();

	private String listagemStatusEstoque = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/status/listagemStatusEstoque/";
	private String listagemStatusChamado = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/status/listagemStatusChamado/";
	private String statusNoCampoEstoque = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/status/statusNoCampoEstoque/";

	// Metodos do Web Services
	final String POST = "POST";
	final String PUT = "PUT";
	final String GET = "GET";
	final String DELETE = "DELETE";

	public List<StatusVO> listagemStatusEstoque() throws JsonSyntaxException, SocketException, IOException {
		List<StatusVO> retorno = gson.fromJson(httpClient.sendGET(listagemStatusEstoque, Constantes.getGet()), liststatusType);

		return retorno;
	}

	public List<StatusVO> listagemStatusChamado() throws JsonSyntaxException, SocketException, IOException {
		List<StatusVO> retorno = gson.fromJson(httpClient.sendGET(listagemStatusChamado, Constantes.getGet()), liststatusType);

		return retorno;
	}

	public StatusVO statusNoCampoEstoque(StatusVO statusVO) throws JsonSyntaxException, SocketException, IOException  {
		String json = gson.toJson(statusVO);

		StatusVO retorno = gson.fromJson(httpClient.sendPOST(statusNoCampoEstoque, json, Constantes.getPost()), statusType);

		return retorno;
	}

}
