package vo;

import java.io.Serializable;

public class UsuarioVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6607198678171578674L;
	private int id;
	private String login;
	private String senha;
	private String nome;
	private String email;
	private String CPF;
	private String tipo;
private int statusAD;	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipo() {
		return tipo;
	}

	public int getStatusAD() {
		return statusAD;
	}

	public void setStatusAD(int statusAD) {
		this.statusAD = statusAD;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}





	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	

}
