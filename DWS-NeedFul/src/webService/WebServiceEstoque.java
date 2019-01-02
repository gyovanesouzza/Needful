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
import vo.EstoqueVO;

public class WebServiceEstoque {

	private Gson gson = new Gson();
	private Type estoqueType = new TypeToken<EstoqueVO>() {
	}.getType();
	private Type listestoqueType = new TypeToken<List<EstoqueVO>>() {
	}.getType();
	private HttpClient httpClient = new HttpClient();


	private String pesquisaEstoquea = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/pesquisaEstoquea/";
	private String pesquisaEstoqueAD = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/pesquisaEstoquead/";
	private String dadosNoCampo = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/dadosNosCampos/";
	private String adcionarEstoque = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/adcionarEstoque/";
	private String alterarEstoque = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/alterarEstoque/";
	private String existerMaterialAtivadoInsert = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/existerMaterialAtivadoInsert/";
	private String existerMaterialDesativadoInsert = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/existerMaterialDesativadoInsert/";
	private String existerMaterialAtivadoUpdate = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/existerMaterialAtivadoUpdate/";
	private String existerMaterialDesativadoUpdate = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/existerMaterialDesativadoUpdate/";
	private String statusMaterial = "http://" + Constantes.getIpWebservice() + ":8080/WSNeedful/webresources/estoque/statusMaterial/";
	// Metodos do Web Services
	final String POST = "POST";
	final String PUT = "PUT";
	final String GET = "GET";
	final String DELETE = "DELETE";

	public boolean adcionarEstoque(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);

		boolean retorno = Boolean.parseBoolean(httpClient.sendPOST(adcionarEstoque, json, Constantes.getPost()));

		return retorno;
	}

	public boolean alterarEstoque(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
System.out.println(json);
		boolean retorno = Boolean.parseBoolean(httpClient.sendPUT(alterarEstoque, json, Constantes.getPut()));
		System.out.println(retorno);

		return retorno;
	}

	public List<EstoqueVO> pesquisaEstoquea(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		System.out.println(json);
		List<EstoqueVO> retorno = gson.fromJson(httpClient.sendPOST(pesquisaEstoquea, json, Constantes.getPost()), listestoqueType);

		return retorno;
	}
	public List<EstoqueVO> pesquisaEstoqueAD(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		System.out.println(json);
		List<EstoqueVO> retorno = gson.fromJson(httpClient.sendPOST(pesquisaEstoqueAD, json, Constantes.getPost()), listestoqueType);
		
		return retorno;
	}


	public EstoqueVO dadosNoCampo(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		System.out.println(httpClient.sendPOST(dadosNoCampo, json, Constantes.getPost()));
		EstoqueVO retorno = gson.fromJson(httpClient.sendPOST(dadosNoCampo, json, Constantes.getPost()), estoqueType);

		return retorno;
	}

	public boolean existerMaterialAtivadoInsert (EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		return Boolean.parseBoolean(httpClient.sendPOST(existerMaterialAtivadoInsert, json, Constantes.getPost()));

	}
	public boolean existerMaterialDesativadoInsert(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		return Boolean.parseBoolean(httpClient.sendPOST(existerMaterialDesativadoInsert, json, Constantes.getPost()));
		
	}
	public boolean existerMaterialAtivadoUpdate (EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		return Boolean.parseBoolean(httpClient.sendPOST(existerMaterialAtivadoUpdate, json, Constantes.getPost()));
		
	}
	public boolean existerMaterialDesativadoUpdate(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
		return Boolean.parseBoolean(httpClient.sendPOST(existerMaterialDesativadoUpdate, json, Constantes.getPost()));
		
	}
	public boolean statusMaterial(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		String json = gson.toJson(estoqueVO);
	System.out.println(json);
		return Boolean.parseBoolean(httpClient.sendPOST(statusMaterial, json, Constantes.getPost()));
		
	}

}
