package controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import business.ClienteBusiness;
import vo.ClientVO;

public class ClienteController {
private ClienteBusiness clienteBusiness = new ClienteBusiness(); 

public List<ClientVO> listagemCliente(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException  {
	return clienteBusiness.listagemCliente(clientVO);
}

public ClientVO passarDados(ClientVO clientVO) throws JsonSyntaxException, SocketException, IOException{
	// TODO Auto-generated method stub
	return clienteBusiness.passarDados(clientVO);
}
}
