package br.com.fences.ocorrenciardobackend.tratamentoerro.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa uma mensagem de erro devolvida pelo webservice Rest.
 * Os atributos possuem a palavra "erro" embutida para facilitar a identificacao
 * quando um erro for retornado.
 *
 */
@XmlRootElement
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Erro implements Serializable {
	private static final long serialVersionUID = -6057820737419323540L;
	private Integer codigoErro;
	private String mensagemErro;
	public Erro() {
	}
	public Erro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
	public Erro(Integer codigo, String mensagemErro) {
		this.setCodigoErro(codigo);
		this.mensagemErro = mensagemErro;
	}
	public String getMensagemErro() {
		return mensagemErro;
	}
	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
	public Integer getCodigoErro() {
		return codigoErro;
	}
	public void setCodigoErro(Integer codigoErro) {
		this.codigoErro = codigoErro;
	}
}