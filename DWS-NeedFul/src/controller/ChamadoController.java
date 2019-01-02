package controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import business.ChamadoBusiness;
import vo.ChamadosVO;
import vo.GraficoVO;
import vo.StatusVO;
import vo.TecnicoVO;

public class ChamadoController {
	ChamadoBusiness chamadoBusiness = new ChamadoBusiness();
	TecnicoController tecnicoController = new TecnicoController();

	public boolean abreChamadoInstalacao(ChamadosVO chamadosVO) throws SocketException, IOException {
		return chamadoBusiness.abreChamadoInstalacao(chamadosVO);

	}

	public boolean abreChamadoManuntecao(ChamadosVO chamadosVO) throws SocketException, IOException {
		return chamadoBusiness.abreChamadoManuntecao(chamadosVO);
	}

	public List<ChamadosVO> pesquisaChamado(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {
		return chamadoBusiness.pesquisaChamado(chamadosVO);
	}

	public List<ChamadosVO> carregarTipoChamado() throws JsonSyntaxException, SocketException, IOException {

		return chamadoBusiness.carregarTipoChamado();
	}

	public List<StatusVO> carregarStatus(int idTipoStatus) throws JsonSyntaxException, SocketException, IOException {

		return chamadoBusiness.carregarStatus();
	}

	public List<TecnicoVO> carregarTecnico() throws JsonSyntaxException, SocketException, IOException  {

		return tecnicoController.read();
	}

	public String pesquisaTipoDeChamado(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {
		return chamadoBusiness.pesquisaTipoDeChamado(chamadosVO);
	}

	public ChamadosVO carregarTelaEdicaoInstalacao(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {

		return chamadoBusiness.carregarTelaEdicaoInstalacao(chamadosVO);

	}

	public ChamadosVO carregarTelaEdicaoManuntencao(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {

		return chamadoBusiness.carregarTelaEdicaoManuntencao(chamadosVO);

	}

	public boolean alterarChamadoManuntencao(ChamadosVO chamadoVO)
			throws JsonSyntaxException, SocketException, IOException {
		return chamadoBusiness.alterarChamadoManuntencao(chamadoVO);
	}

	public boolean fecharChamado(ChamadosVO chamadoVO) throws JsonSyntaxException, SocketException, IOException {
		// TODO Auto-generated method stub
		return chamadoBusiness.fecharChamado(chamadoVO);
	}

	public boolean alterarChamadoInstalacao(ChamadosVO chamadosVO)
			throws JsonSyntaxException, SocketException, IOException {
		return chamadoBusiness.alterarChamadoInstalacao(chamadosVO);

	}

	public List<GraficoVO> carregarDadosGrafico() throws JsonSyntaxException, SocketException, IOException {
		return chamadoBusiness.carregarDadosGrafico();
	}

}
