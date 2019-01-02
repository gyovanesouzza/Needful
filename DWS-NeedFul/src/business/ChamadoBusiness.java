package business;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import vo.ChamadosVO;
import vo.GraficoVO;
import vo.StatusVO;
import webService.WebServiceChamado;
import webService.WebServiceStatus;

public class ChamadoBusiness {

	Gson gson = new Gson();
	Type chamadoType = new TypeToken<ChamadosVO>() {
	}.getType();
	WebServiceChamado serviceChamado = new WebServiceChamado();
	WebServiceStatus serviceStatus = new WebServiceStatus();

	public boolean abreChamadoInstalacao(ChamadosVO chamadosVO) throws SocketException, IOException{
		return serviceChamado.abreChamadoInstalacao(chamadosVO);

	}

	public boolean abreChamadoManuntecao(ChamadosVO chamadosVO) throws SocketException, IOException{

		return serviceChamado.abreChamadoManutencao(chamadosVO);
	}

	public List<ChamadosVO> pesquisaChamado(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException {

		return serviceChamado.listagempost(chamadosVO);
	}

	public String pesquisaTipoDeChamado(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException{


		return serviceChamado.pesquisaTipoDeChamado(chamadosVO);
	}

	public ChamadosVO carregarTelaEdicaoInstalacao(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException 
			 {

		return serviceChamado.carregarTelaEdicaoInstalacao(chamadosVO);
	}

	public ChamadosVO carregarTelaEdicaoManuntencao(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException{
		return serviceChamado.carregarteladeManuntecao(chamadosVO);
	}

	public boolean alterarChamadoManuntencao(ChamadosVO chamadoVO) throws JsonSyntaxException, SocketException, IOException {
		return serviceChamado.atualizarmanutencao(chamadoVO);
	}

	public boolean fecharChamado(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException {

		return serviceChamado.fecharChamado(chamadosVO);
	}

	public boolean alterarChamadoInstalacao(ChamadosVO chamadosVO) throws JsonSyntaxException, SocketException, IOException{
		return serviceChamado.atualizarInstalacao(chamadosVO);
	}

	public List<GraficoVO> carregarDadosGrafico() throws JsonSyntaxException, SocketException, IOException {

		return serviceChamado.carregarDadosGrafico();
	}

	public List<ChamadosVO> carregarTipoChamado() throws JsonSyntaxException, SocketException, IOException{
		// TODO Auto-generated method stub
		return serviceChamado.carregarTipoChamado();
	}

	public List<StatusVO> carregarStatus() throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return serviceStatus.listagemStatusChamado();
	}

}
