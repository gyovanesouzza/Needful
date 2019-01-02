package controller;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import business.TecnicoBusiness;
import vo.TecnicoVO;

public class TecnicoController {
	TecnicoBusiness TecnicoBusiness = new TecnicoBusiness();

	public boolean criarTecnico(TecnicoVO tecnicoVO) throws JsonSyntaxException, SocketException, IOException {
		return TecnicoBusiness.criarTecnico(tecnicoVO);
	}

	public void updateTecnico(TecnicoVO tecnicoVO) throws JsonSyntaxException, SocketException, IOException  {
		TecnicoBusiness.updateTecnico(tecnicoVO);

	}

	public List<TecnicoVO> read() throws JsonSyntaxException, SocketException, IOException {
		List<TecnicoVO> retorno = TecnicoBusiness.read();
		
		return retorno;
	}

}
