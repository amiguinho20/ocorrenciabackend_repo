package br.com.fences.ocorrenciardobackend.rdoextracao.dao;

import static br.com.fences.ocorrenciardobackend.util.DaoUtil.converterValorParaString;
import static br.com.fences.ocorrenciardobackend.util.JsonUtil.adicionarAtributoJson;
import static br.com.fences.ocorrenciardobackend.util.JsonUtil.criarAtributoJson;
import static br.com.fences.ocorrenciardobackend.util.JsonUtil.criarJson;
import static br.com.fences.ocorrenciardobackend.util.JsonUtil.criarJsonArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import br.com.fences.fencesutils.verificador.Verificador;
import br.com.fences.ocorrenciardobackend.util.DaoUtil;

@RequestScoped
public class UsuarioDAO{
	
	@Inject
	private transient Logger logger;
	
	@Resource(mappedName = "ds/Db2Rdo")
	private DataSource dataSource;
	
	public String consultar(String rgUsuario)
	{
		StringBuilder json = new StringBuilder();
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rset = null;
		if (Verificador.isValorado(rgUsuario))
		{
			StringBuilder sql = new StringBuilder();
			sql.append("select * from db2aplicativos.tb_usuario where rg_usuario = '" + rgUsuario + "' ");
			
			try
			{
				conn = dataSource.getConnection();
				stmt = conn.createStatement();
				rset = stmt.executeQuery(sql.toString());
				
				if (rset.next()) {

					List<String> atributos = new ArrayList<>();
					
					//-- recuperacao das colunas de cabecalho
					ResultSetMetaData rsmd = rset.getMetaData();
					int qtdColuna = rsmd.getColumnCount();
					for (int i = 1; i <= qtdColuna; i++) {
					
						String coluna = rsmd.getColumnName(i);

						//-- recuperacao do valor no resultSet
						String valor = converterValorParaString(rset, coluna);
						String atributo = criarAtributoJson(coluna, valor);
						adicionarAtributoJson(atributos, atributo);
					}
					json.append(criarJson(atributos));
				}
			}
			catch(Exception e)
			{
				String msg = "Erro na recuperacao do usuario de rg[" + rgUsuario + "]. Erro: " + e.getMessage();
				logger.error(msg, e);
				throw new RuntimeException(msg, e);
			}
			finally
			{
				DaoUtil.fecharRecurso(rset, stmt, conn);
			}
		}
		else
		{
			throw new RuntimeException("O parametro idDelegacia esta vazio.");
		}
		return json.toString();
	}
	
	
	public String listar()
	{
		StringBuilder json = new StringBuilder();
		Connection conn = null;
		Statement  stmt = null;
		ResultSet  rset = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select RG_USUARIO from db2aplicativos.tb_usuario order by 1");
		
		try
		{
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql.toString());
			
			List<String> rgUsuarios = new ArrayList<>();
			
			while (rset.next()) {
				String valor = rset.getString(1);
				rgUsuarios.add(valor);
			}
			json.append("{" + criarJsonArray("RG_USUARIO", rgUsuarios) + "}");
		}
		catch(Exception e)
		{
			String msg = "Erro na lista de rgUsuarios. Erro: " + e.getMessage();
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		}
		finally
		{
			DaoUtil.fecharRecurso(rset, stmt, conn);
		}
		

		return json.toString();
	}
	
	
	

    
}
