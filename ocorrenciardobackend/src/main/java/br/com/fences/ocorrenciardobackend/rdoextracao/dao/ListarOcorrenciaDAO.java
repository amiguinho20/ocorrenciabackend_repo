package br.com.fences.ocorrenciardobackend.rdoextracao.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.fences.ocorrenciaentidade.chave.OcorrenciaChave;
import br.com.fences.ocorrenciardobackend.config.Log;
import br.com.fences.ocorrenciardobackend.util.DaoUtil;

@RequestScoped
public class ListarOcorrenciaDAO{
	
	private Logger logger =  LogManager.getLogger(ListarOcorrenciaDAO.class);
	
	private DateFormat dfDataHora = new SimpleDateFormat("yyyyMMddHHmmss");

	@Resource(mappedName = "ds/Db2Rdo")
	private DataSource dataSource;
	
	@Log
	public Set<OcorrenciaChave> pesquisarPorDataDeRegistro(String dataRegistroInicial, String dataRegistroFinal)
    {
    	//-- garante unicidade e ordenacao na ordem de insercao
    	Set<OcorrenciaChave> ocorrenciasChaves = new LinkedHashSet<>(); 
    	
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("");
			
			sql.append("select a.id_delegacia, a.ano_bo, a.num_bo, a.datahora_registro_bo ");
			sql.append("from  ");
			sql.append("    db2aplicativos.tb_bo a  ");
			sql.append("where   ");
			sql.append("    a.DATAHORA_REGISTRO_BO between  ");
			sql.append("    TIMESTAMP_FORMAT('"+ dataRegistroInicial + "', 'YYYYMMDDHH24MISS') and ");
			sql.append("    TIMESTAMP_FORMAT('" + dataRegistroFinal + "', 'YYYYMMDDHH24MISS') ");
			sql.append("order by a.datahora_registro_bo");
			
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql.toString());
			
			while (rset.next()) 
			{
				OcorrenciaChave ocorrenciaChave = new OcorrenciaChave();
				ocorrenciaChave.setIdDelegacia(rset.getString(1));
				ocorrenciaChave.setAnoBo(rset.getString(2));
				ocorrenciaChave.setNumBo(rset.getString(3));
				
				Timestamp dataHora = rset.getTimestamp(4);
				ocorrenciaChave.setDatahoraRegistroBo(dfDataHora.format(dataHora));
				
				ocorrenciasChaves.add(ocorrenciaChave);
			}
		} catch (Exception e) {
			logger.error("Erro em pesquisarPorDataDeRegistro com os parametros [" + dataRegistroInicial + ", " + dataRegistroFinal + "]", e);
			throw new RuntimeException(e);
		} finally {
			DaoUtil.fecharRecurso(rset, stmt, conn);
		}
    	return ocorrenciasChaves;
    }
		
	@Log
    public Set<OcorrenciaChave> pesquisarRouboDeCarga(String dataRegistroInicial, String dataRegistroFinal, boolean recuperarComplementares)
    {
    	//-- garante unicidade e ordenacao na ordem de insercao
    	Set<OcorrenciaChave> ocorrenciasChaves = new LinkedHashSet<>(); 
    	
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("");
			
			sql.append("select a.id_delegacia, a.ano_bo, a.num_bo, a.datahora_registro_bo  ");
			sql.append("from  ");
			sql.append("    db2aplicativos.tb_bo a  ");
			sql.append("where   ");
			sql.append("    a.DATAHORA_REGISTRO_BO between  ");
			sql.append("    TIMESTAMP_FORMAT('"+ dataRegistroInicial + "', 'YYYYMMDDHH24MISS') and ");
			sql.append("    TIMESTAMP_FORMAT('" + dataRegistroFinal + "', 'YYYYMMDDHH24MISS') ");
			//sql.append("and a.ano_referencia_bo is null "); //-- que nao seja complementar  
			sql.append("and exists (  ");
			sql.append("    select b.*  ");
			sql.append("    from   db2aplicativos.tb_bo_natureza b ");
			sql.append("    where ");
			sql.append("	a.ano_bo = b.ano_bo and a.id_delegacia = b.id_delegacia and a.num_bo = b.num_bo and  ");
			sql.append("	(   ");
			sql.append("	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22010 and b.id_natureza = '155A' and b.id_conduta = 9)  ");
			sql.append("or	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22010 and b.id_natureza = '155B' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22010 and b.id_natureza = '156' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22020 and b.id_natureza = '157' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22070 and b.id_natureza = '180A' and b.id_conduta = 1) ");
			sql.append("or	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22070 and b.id_natureza = '180B' and b.id_conduta = 1) ");
			sql.append("or	(b.id_ocorrencia = 10 and b.id_especie = 10 and b.id_subespecie = 22070 and b.id_natureza = '180C' and b.id_conduta = 1) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 10 and b.id_natureza = '155A' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 10 and b.id_natureza = '155B' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 10 and b.id_natureza = '156' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 20 and b.id_natureza = '157' and b.id_conduta = 9) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 70 and b.id_natureza = '180A' and b.id_conduta = 1) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 70 and b.id_natureza = '180B' and b.id_conduta = 1) ");
			sql.append("or	(b.id_ocorrencia = 20 and b.id_especie = 20 and b.id_subespecie = 70 and b.id_natureza = '180C' and b.id_conduta = 1) ");
			sql.append("	) ");
			sql.append(") ");
			sql.append("order by a.datahora_registro_bo");

			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql.toString());
			
			while (rset.next()) 
			{
				OcorrenciaChave ocorrenciaChave = new OcorrenciaChave();
				ocorrenciaChave.setIdDelegacia(rset.getString(1));
				ocorrenciaChave.setAnoBo(rset.getString(2));
				ocorrenciaChave.setNumBo(rset.getString(3));
				
				Timestamp dataHora = rset.getTimestamp(4);
				ocorrenciaChave.setDatahoraRegistroBo(dfDataHora.format(dataHora));
				
				ocorrenciasChaves.add(ocorrenciaChave);
				
				if (recuperarComplementares)
				{
					ocorrenciasChaves.addAll(listarComplementares(ocorrenciaChave));
				}
			}
		} catch (Exception e) {
			logger.error(
					"Erro em pesquisarRouboDeCarga com os parametros ["
							+ dataRegistroInicial + ", " + dataRegistroFinal
							+ ", " + recuperarComplementares + "]", e);
			throw new RuntimeException(e);
		} finally {
			DaoUtil.fecharRecurso(rset, stmt, conn);
		}
    	return ocorrenciasChaves;
    }
    
    /**
     * recursivo
     * @param ocorrenciaChavePai
     * @return
     */
    public Set<OcorrenciaChave> listarComplementares(OcorrenciaChave ocorrenciaChavePai){
    	
    	Set<OcorrenciaChave> ocorrenciasChaves = new LinkedHashSet<>(); 
    	
    	Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("");
			
			sql.append("select a.id_delegacia, a.ano_bo, a.num_bo ");
			sql.append("from  ");
			sql.append("    db2aplicativos.tb_bo a  ");
			sql.append("where   ");
			sql.append("    a.ano_referencia_bo = " + ocorrenciaChavePai.getAnoBo() + " ");
			sql.append("and a.NUM_REFERENCIA_BO = " + ocorrenciaChavePai.getNumBo() + " ");
			sql.append("and a.DELEG_REFERENCIA_BO = " + ocorrenciaChavePai.getIdDelegacia() + " ");
			
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql.toString());
			
			while (rset.next()) 
			{
				
				OcorrenciaChave ocorrenciaChave = new OcorrenciaChave();
				ocorrenciaChave.setIdDelegacia(rset.getString(1));
				ocorrenciaChave.setAnoBo(rset.getString(2));
				ocorrenciaChave.setNumBo(rset.getString(3));
				
				ocorrenciasChaves.add(ocorrenciaChave);
				
				//-- recursivo
				ocorrenciasChaves.addAll(listarComplementares(ocorrenciaChave));
			}

		} catch (Exception e) {
			logger.error(
					"Erro em listarComplementares com os parametros ["
							+ ocorrenciaChavePai + "]", e);
			throw new RuntimeException(e);
		} finally {
			DaoUtil.fecharRecurso(rset, stmt, conn);
		}
		return ocorrenciasChaves;
    	
    }
    
}
