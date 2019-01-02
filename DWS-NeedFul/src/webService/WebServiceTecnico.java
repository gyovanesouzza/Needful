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
import vo.TecnicoVO;

public class WebServiceTecnico {
	private Gson gson = new Gson();
	@SuppressWarnings("unused")
	private Type tecnicoType = new TypeToken<TecnicoVO>() {
	}.getType();
	private Type listTecnicoType = new TypeToken<List<TecnicoVO>>() {
	}.getType();


	private String criarTecnico = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/tecnico/criarTecnico";
	private String updateTecnico = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/tecnico/updateTecnico";
	private String readTecnico = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/tecnico";

	private HttpClient httpClient = new HttpClient();

	public boolean criarTecnico(TecnicoVO tecnicoVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(tecnicoVO);

		return Boolean.parseBoolean(httpClient.sendPOST(criarTecnico, json, Constantes.getPost()));
	}

	public List<TecnicoVO> read() throws JsonSyntaxException, SocketException, IOException {
		return gson.fromJson(httpClient.sendGET(readTecnico, Constantes.getGet()), listTecnicoType);
	}

	public boolean updateTecnico(TecnicoVO tecnicoVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(tecnicoVO);

		return Boolean.parseBoolean(httpClient.sendPOST(updateTecnico, json, Constantes.getPost()));
	}

}
