package main.java.br.ufc.qx.wed.dontpad.model;

import java.util.Date;

public class Caderno {
	
	private long id;
	private String nome;
	private String conteudo;
	private Usuario dono;
	private boolean publico = true;
	private Date criacao;
	private Date ultimaAtualizacao;
	
	public Caderno() {
		super();
	}

	public Caderno(String nome, Usuario dono, boolean publico) {
		super();
		this.nome = nome;
		this.dono = dono;
		this.publico = publico;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Usuario getDono() {
		return dono;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

	public Date getCriacao() {
		return criacao;
	}

	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}

	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(Date ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}
	
	

	
}
