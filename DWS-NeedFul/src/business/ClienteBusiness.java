package business;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import vo.ClientVO;
import webService.WebServiceCliente;

public class ClienteBusiness {
private WebServiceCliente webServiceCliente = new WebServiceCliente();
	public List<ClientVO> listagemCliente(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException {return webServiceCliente.pesquisaClient(clientVO);
	}
	public ClientVO passarDados(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceCliente.passarDados(clientVO);
	}

}
