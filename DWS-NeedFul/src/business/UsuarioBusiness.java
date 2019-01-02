package business;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.List;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import validator.ValidatorLogin;
import vo.UsuarioVO;
import webService.WebServiceUsuario;

public class UsuarioBusiness {

	WebServiceUsuario webServiceUsuario = new WebServiceUsuario();

	Type usuarioType = new TypeToken<UsuarioVO>() {
	}.getType();

	public boolean checkLogin(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {

		return webServiceUsuario.checkLogin(usuarioVO);
	}

	public UsuarioVO permissaoDeLogin(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.permissaoDeLogin(usuarioVO);

	}

	public boolean campoVazio(JTextField txtLogin, JPasswordField txtSenha) {

		return new ValidatorLogin().campoVazio(txtLogin, txtSenha);

	}

	public boolean permissaoDaAreaRestrita(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.permissaoDaAreaRestrita(usuarioVO);
	}

	public UsuarioVO pesquisaUsuario(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.pesquisaUsuario(usuarioVO);
	}

	public boolean criarUsuario(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.criarUsuario(usuarioVO);
	}

	

	public boolean verificarExistenciaLoginEmail(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.existeEmailLogin(usuarioVO);

	}

	public boolean alterarSenha(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.alterarSenha(usuarioVO);
	}

	public UsuarioVO buscarEmailLogin(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException
	{		return webServiceUsuario.buscarEmailORLogin(usuarioVO);
	}

	
	public List<UsuarioVO> readTable() throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.readJtablea();
		
	}
	public List<UsuarioVO> readTableAD() throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.readJtableAD();
		
	}
	public List<UsuarioVO> listadeAdm() throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.listadeAdm();
	}

	public boolean alterarConta(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceUsuario.alteraConta(usuarioVO);
	}

	public List<UsuarioVO> pesquisaTipoDeUsuario() throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return webServiceUsuario.pesquisaTipoDeUsuario();
	}

	public boolean existerContaa(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return webServiceUsuario.existerContaa(usuarioVO);
	}

	public boolean existerContaAD(UsuarioVO usuarioVO)throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return webServiceUsuario.existerContaAD(usuarioVO);
	}
	public boolean statusConta(UsuarioVO usuarioVO)throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return webServiceUsuario.statusConta(usuarioVO);
	}

	public boolean permissaoAlterar(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return webServiceUsuario.permissaoAlterar(usuarioVO);
	}

	public boolean acessoAD(UsuarioVO usuarioVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return webServiceUsuario.acessoAD(usuarioVO);
	}

}
