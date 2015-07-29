package br.com.fences.ocorrenciardobackend.tratamentoerro.exception;

import java.io.Serializable;

public class RestRuntimeException extends RuntimeException implements
		Serializable {

	private static final long serialVersionUID = -6718874105526804885L;
	
	private Integer codigoErro;

	public RestRuntimeException(Integer codigoErro, String message) {
		super(message);
		this.setCodigoErro(codigoErro);
	}
	
	public RestRuntimeException(Integer codigoErro, String message, Throwable cause) {
		super(message, cause);
		this.setCodigoErro(codigoErro);
	}

	public RestRuntimeException(Throwable cause) {
		super(cause);
	}

	public RestRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public Integer getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(Integer codigoErro) {
		this.codigoErro = codigoErro;
	}
}