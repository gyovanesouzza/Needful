package controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import business.EstoqueBusiness;
import vo.EstoqueVO;
import vo.StatusVO;
import webService.WebServiceStatus;

public class EstoqueController {
	EstoqueBusiness business = new EstoqueBusiness();

	public List<EstoqueVO> pesquisaEstoquea(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return business.pesquisaEstoquea(estoqueVO);

	}

	public List<EstoqueVO> pesquisaEstoqueAD(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return business.pesquisaEstoqueAD(estoqueVO);

	}

	public List<StatusVO> carregarStatus() throws JsonSyntaxException, SocketException, IOException {

		return new WebServiceStatus().listagemStatusEstoque();
	}

	public boolean adcionarEstoque(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return business.adcionarEstoque(estoqueVO);
	}

	public boolean alterarEstoque(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return business.alterarEstoque(estoqueVO);
	}

	public EstoqueVO dadosNosCampos(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return business.dadosNosCampos(estoqueVO);
	}

	public StatusVO statusNoCampo(StatusVO statusVO) throws JsonSyntaxException, SocketException, IOException {
		return new WebServiceStatus().statusNoCampoEstoque(statusVO);
	}

	public boolean existerMaterialAtivadoInsert(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return business.existerMaterialAtivadoInsert(estoqueVO);
	}

	public boolean existerMaterialDesativadoInsert(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return business.existerMaterialDesativadoInsert(estoqueVO);
	}

	public boolean existerMaterialAtivadoUpdate(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return business.existerMaterialAtivadoUpdate(estoqueVO);
	}

	public boolean existerMaterialDesativadoUpdate(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return business.existerMaterialDesativadoUpdate(estoqueVO);
	}

	public boolean statusMaterial(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return business.statusMaterial(estoqueVO);

	}
}
