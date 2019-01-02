package tabelModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vo.ChamadosVO;

public class ChamadosModel extends AbstractTableModel {

	
	private static final long serialVersionUID = 1L;
	private List<ChamadosVO> dados = new ArrayList<>();
	private String[] colunas = { "ID", "Cliente", "Data", "Telefone", "Endereço", "Tecnico", "Status" };

	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return dados.size();

	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		switch (coluna) {
		case 0:
			return dados.get(linha).getID();

		case 1:
			return dados.get(linha).getClientVO().getNome();
		case 2:
			return new SimpleDateFormat("dd/MM/yyyy").format(dados.get(linha).getData());
		case 3:
			return dados.get(linha).getClientVO().getTelefone();
		case 4:
			return dados.get(linha).getClientVO().getEnderecoVO().getRua();
		case 5:
			return dados.get(linha).getTecnicoVO().getTecnico();
		case 6:
			return dados.get(linha).getStatusVO().getTipo();
		}
		return null;
	}

	
	public void addRow(ChamadosVO c) {
		this.dados.add(c);
		this.fireTableDataChanged();
	}

	public void removeRow(int linha) {
		this.dados.remove(linha);
		this.fireTableRowsDeleted(linha, linha);
	}

}
