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
import vo.ClientVO;

public class WebServiceCliente {
	private Gson gson = new Gson();
	private Type clientType = new TypeToken<ClientVO>() {
	}.getType();
	private Type listClienteType = new TypeToken<List<ClientVO>>() {
	}.getType();

	
	private String listagemCliente = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/cliente/listagemCliente/";
	private String passarDados = "http://" + Constantes.getIpWebservice()
			+ ":8080/WSNeedful/webresources/cliente/passarDados/";

	

	private HttpClient httpClient = new HttpClient();

	public List<ClientVO> pesquisaClient(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(clientVO);
		System.out.println(json);

		return gson.fromJson(httpClient.sendPOST(listagemCliente, json, Constantes.getPost()), listClienteType);
	}

	public ClientVO passarDados(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(clientVO);
		System.out.println(json);
		
		return gson.fromJson(httpClient.sendPOST(passarDados, json, Constantes.getPost()), clientType);
	}

}
