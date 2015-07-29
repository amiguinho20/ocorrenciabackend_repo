package br.com.fences.ocorrenciardobackend.converter;

import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import br.com.fences.ocorrenciaentidade.chave.OcorrenciaChave;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@ApplicationScoped
public class OcorrenciaChaveConverter extends Converter<OcorrenciaChave>{

	private Gson gson = new GsonBuilder().create();
	
	@Override
	public String paraJson(OcorrenciaChave ocorrenciaChave) 
	{
    	String json = gson.toJson(ocorrenciaChave);
		return json;
	}

	@Override
	public String paraJson(Collection<OcorrenciaChave> col) {
		String json = gson.toJson(col);
		return json;
	}	
	
	@Override
	public OcorrenciaChave paraObjeto(String json) {
    	OcorrenciaChave ocorrenciaChave = gson.fromJson(json, OcorrenciaChave.class);
    	return ocorrenciaChave;
	}


	
	
	
}
