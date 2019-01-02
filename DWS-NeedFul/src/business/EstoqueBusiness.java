package business;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import vo.EstoqueVO;
import webService.WebServiceEstoque;

public class EstoqueBusiness {
	WebServiceEstoque webServiceEstoque = new WebServiceEstoque();

	public List<EstoqueVO> pesquisaEstoquea(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.pesquisaEstoquea(estoqueVO);
	}

	public List<EstoqueVO> pesquisaEstoqueAD(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.pesquisaEstoqueAD(estoqueVO);
	}

	public boolean adcionarEstoque(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.adcionarEstoque(estoqueVO);
	}

	public boolean alterarEstoque(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.alterarEstoque(estoqueVO);
	}

	public EstoqueVO dadosNosCampos(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {

		return webServiceEstoque.dadosNoCampo(estoqueVO);
	}

	public boolean existerMaterialAtivadoInsert(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.existerMaterialAtivadoInsert(estoqueVO);
	}

	public boolean existerMaterialDesativadoInsert(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.existerMaterialDesativadoInsert(estoqueVO);
	}

	public boolean existerMaterialAtivadoUpdate(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.existerMaterialAtivadoUpdate(estoqueVO);
	}

	public boolean existerMaterialDesativadoUpdate(EstoqueVO estoqueVO)
			throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.existerMaterialDesativadoUpdate(estoqueVO);
	}

	public boolean statusMaterial(EstoqueVO estoqueVO) throws JsonSyntaxException, SocketException, IOException {
		return webServiceEstoque.statusMaterial(estoqueVO);

	}
}
