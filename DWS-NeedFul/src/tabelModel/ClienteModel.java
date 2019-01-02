package tabelModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vo.ClientVO;

public class ClienteModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<ClientVO> dados = new ArrayList<>();
	private String[] colunas = { "Cliente", "Endereço", "Numero" };

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
			return dados.get(linha).getNome();
		case 1:
			return dados.get(linha).getEnderecoVO().getRua();

		case 2:
			return dados.get(linha).getEnderecoVO().getNumero();
		}
		return null;
	}

	public void addRow(ClientVO c) {
		this.dados.add(c);
		this.fireTableDataChanged();
	}

	public void removeRow(int linha) {
		this.dados.remove(linha);
		this.fireTableRowsDeleted(linha, linha);
	}

}
