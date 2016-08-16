package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.engine.internal.Cascade;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "curso")
public class Curso implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCurso")
	private Long id;
	@Expose
	private String nome;
	@Expose
	private String descricao;
	@Expose
	private String statusSincronizacao;
	@Expose
	private String registroUnico;
	
	@OneToMany(cascade= CascadeType.ALL, mappedBy="curso")
	private List<Usuario> listaUsuarios;
	
	public Curso() {
	super();
	}
	
	
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public Long getId(){
		return id;
	}
	
	public String getRegistroUnico() {
		return registroUnico;
	}


	public void setRegistroUnico(String registroUnico) {
		this.registroUnico = registroUnico;
	}

	public String getStatusSincronizacao() {
		return statusSincronizacao;
	}


	public void setStatusSincronizacao(String statusSincronizacao) {
		this.statusSincronizacao = statusSincronizacao;
	}

	private static final long serialVersionUID = 1L;
	
}
