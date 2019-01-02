package business;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import vo.TecnicoVO;
import webService.WebServiceTecnico;

public class TecnicoBusiness {
	WebServiceTecnico webServiceTecnico = new WebServiceTecnico();

	public boolean criarTecnico(TecnicoVO tecnicoVO) throws JsonSyntaxException, SocketException, IOException {
		int indexNome = tecnicoVO.getTecnico().indexOf(" "); 
		int indexSobrenome = tecnicoVO.getTecnico().lastIndexOf(" ");

		String sobreNome = tecnicoVO.getTecnico().substring(indexSobrenome, tecnicoVO.getTecnico().length());

		tecnicoVO.setTecnico(tecnicoVO.getTecnico().substring(0, indexNome) + sobreNome);
		System.out.println(tecnicoVO.getTecnico());
		return webServiceTecnico.criarTecnico(tecnicoVO);
	}

	public boolean updateTecnico(TecnicoVO tecnicoVO) throws JsonSyntaxException, SocketException, IOException {
		int indexNome = tecnicoVO.getTecnico().indexOf(" ");
		int indexSobrenome = tecnicoVO.getTecnico().lastIndexOf(" ");

		String sobreNome = tecnicoVO.getTecnico().substring(indexSobrenome, tecnicoVO.getTecnico().length());

		tecnicoVO.setTecnico(tecnicoVO.getTecnico().substring(0, indexNome) + sobreNome);

		return webServiceTecnico.updateTecnico(tecnicoVO);
	}

	public List<TecnicoVO> read() throws JsonSyntaxException, SocketException, IOException {
		return webServiceTecnico.read();
	}

}
