package controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.JsonSyntaxException;

import business.UsuarioBusiness;
import vo.UsuarioVO;

public class UsuarioController {
	UsuarioBusiness business = new UsuarioBusiness();

	public boolean checkLogin(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.checkLogin(usuarioVO);
	}

	public UsuarioVO permissaoDeLogin(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.permissaoDeLogin(usuarioVO);

	}

	public boolean campoVazio(JTextField txtLogin, JPasswordField txtSenha) {

		return business.campoVazio(txtLogin, txtSenha);

	}

	public boolean permissaoDaAreaRestrita(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.permissaoDaAreaRestrita(usuarioVO);
	}

	public List<UsuarioVO> readTablea() throws JsonSyntaxException, SocketException, IOException  {
		return business.readTable();

	}
	public List<UsuarioVO> readTableAD() throws JsonSyntaxException, SocketException, IOException  {
		return business.readTableAD();
	}
	public List<UsuarioVO> listadeAdm() throws JsonSyntaxException, SocketException, IOException  {
		return business.listadeAdm();
	}
	
	public UsuarioVO pesquisaUsuario(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.pesquisaUsuario(usuarioVO);

	}

	public boolean criarUsuario(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException  {
		return business.criarUsuario(usuarioVO);
	}

	

	public boolean verificarExistenciaLoginEmail(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException  {

		return business.verificarExistenciaLoginEmail(usuarioVO);
	}

	public UsuarioVO buscarEmailLogin(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException{
		return business.buscarEmailLogin(usuarioVO);
	}

	public boolean alterarSenha(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.alterarSenha(usuarioVO);
	}

	public boolean alteraConta(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.alterarConta(usuarioVO);

	}

	public List<UsuarioVO> pesquisaTipoDeUsuario() throws JsonSyntaxException, SocketException, IOException  {

		return business.pesquisaTipoDeUsuario();
	}

	public boolean existerContaa(UsuarioVO usuarioVO)throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return business.existerContaa(usuarioVO);
	}

	public boolean existerContaAD(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return business.existerContaAD(usuarioVO);
	}
	public boolean statusConta(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return business.statusConta(usuarioVO);
	}

	public boolean permissaoAlterar(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return business.permissaoAlterar(usuarioVO);
	}

	public boolean acessoAD(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return business.acessoAD(usuarioVO);
	}
	
}
