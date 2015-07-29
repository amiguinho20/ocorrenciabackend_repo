package br.com.fences.ocorrenciardobackend.util;

import java.util.List;

public class JsonUtil {

	/**
	 * A chave e o valor sao envolvidos por aspas.
	 * @param chave
	 * @param valor
	 * @return
	 */
	public static String criarAtributoJson(String chave, String valor){
		String atributo = "";
		if (valor != null && !valor.trim().isEmpty())
		{
			valor = valor.replaceAll("[^\\u0020-\\u007e\\u00a0-\\u00ff]|\\u005c|\\u0022", " ");
			valor = valor.trim();
			atributo =  "\"" + chave + "\":\"" + valor + "\"";
		}
		return atributo;
	}
	
	public static void adicionarAtributoJson(List<String> lista, String valor)
	{
		if (valor != null && !valor.trim().isEmpty())
		{
			lista.add(valor);
		}
	}
	
	public static String criarJson(List<String> atributos)
	{
		StringBuilder json = new StringBuilder();
		if (atributos != null && !atributos.isEmpty())
		{
			for (String atributo : atributos)
			{
				if (!json.toString().isEmpty())
				{
					json.append(",");
				}
				json.append(atributo);
			}
			json = new StringBuilder("{" + json.toString() + "}");
		}
		return json.toString();
	}
	
	public static String criarJsonArray(String chave, List<String> valores)
	{
		StringBuilder json = new StringBuilder();
		if (valores != null && !valores.isEmpty())
		{
			for (String valor : valores)
			{
				if (!json.toString().isEmpty())
				{
					json.append(",");
				}
				json.append(valor);
			}
			json = new StringBuilder("\"" + chave + "\":[" + json.toString() + "]");
		}
		return json.toString();
	}
	
	
}
