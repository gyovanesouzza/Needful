package tabelModel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vo.*;

public class UsuarioTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<UsuarioVO> dados = new ArrayList<>();

	private String[] colunas = { "ID", "Nome", "Login", "E-Mail", "Tipo" };

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
			return dados.get(linha).getId();
		case 1:
			return dados.get(linha).getNome();
		case 2:
			return dados.get(linha).getLogin();
		case 3:
			return dados.get(linha).getEmail();
		case 4:
			return dados.get(linha).getTipo();
		}

		return null;
	}

	@Override
	public void setValueAt(Object valor, int linha, int coluna) {
		switch (coluna) {

		case 0:
			dados.get(linha).setId(Integer.parseInt((String) valor));
			break;
		case 1:
			dados.get(linha).setNome((String) valor);
			break;
		case 2:
			dados.get(linha).setLogin((String) valor);
			break;
		case 3:
			dados.get(linha).setSenha((String) valor);
			break;
		case 4:
			dados.get(linha).setEmail((String) valor);
			break;
		case 5:
			dados.get(linha).setTipo((String) valor);
			break;

		}
		this.fireTableRowsUpdated(linha, linha);
	}

	public void addRow(UsuarioVO u) {
		this.dados.add(u);
		this.fireTableDataChanged();
	}

	public void removeRow(int linha) {
		this.dados.remove(linha);
		this.fireTableRowsDeleted(linha, linha);
	}

}
