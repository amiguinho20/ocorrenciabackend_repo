package br.com.fences.ocorrenciardobackend.converter;

import java.util.Collection;


/**
 * @param <T> Tipo de Objeto para conversao.
 */
public abstract class Converter<T> {

	public abstract String paraJson(T obj);
	public abstract String paraJson(Collection<T> col);
	public abstract T paraObjeto(String json);

}
