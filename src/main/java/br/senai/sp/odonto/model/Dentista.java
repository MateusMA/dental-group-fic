package br.senai.sp.odonto.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dentista")
public class Dentista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)// depende do dialeto ex:Oracle = Sequence
	private long codigo;
	
	@NotNull
	@Size(min = 3, max = 98, message = "VocÊ cometeu um erro de digitação por iso sua mãe não te ama!")
	private String nome;
	
	@NotNull
	private String cro;
	
	//@Column(name = "str_telefone")
	private String telefone;
	private String email;
	
	@ManyToMany
	private List<Especialidade> especialidades;

	
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		
		return "Dentista [codigo=" + codigo + ", nome=" + nome + ", cro=" + cro + ", telefone=" + telefone + ", email="
				+ email + "]";
		
	}
	
	

}
