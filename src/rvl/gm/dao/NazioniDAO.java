/*
 * This file is part of WeCall
 *
 * Copyright (C) 20017     Luca Ramella Votta
 *                         luca.ramellavotta@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package rvl.gm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import rvl.gm.classi.Nazioni;

public class NazioniDAO extends NamedParameterJdbcDaoSupport
{
	
	private PlatformTransactionManager transactionManager;
	private TransactionTemplate txTemplate;
		
	public void setTransactionManager(PlatformTransactionManager txManager) 
	{
        this.transactionManager = txManager;
        this.txTemplate = new TransactionTemplate(txManager);
        this.txTemplate.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
    }
	
	@SuppressWarnings("unchecked")
	public List<Nazioni> findAll(String parAbilitati) throws org.springframework.dao.DataAccessException
	{
		
		List<Nazioni> list=null;
		String sql="";
		if(parAbilitati.equalsIgnoreCase("T"))
			sql = "select * from tg_nazioni order by descrizione";
		else
			if(parAbilitati.equalsIgnoreCase("A"))
				sql = "select * from tg_nazioni where abilitata!=FALSE order by descrizione";

		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new NazioniMapper(),new Object[]{});
		}
		catch (DataAccessException ex) 
		{
			transactionManager.rollback(status);
			throw ex;
		}
		catch (org.springframework.transaction.TransactionTimedOutException ex)
		{
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);	
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Nazioni> findFilter(Nazioni ut) throws org.springframework.dao.DataAccessException
	{
		
		List<Nazioni> list=null;
		String sql = "select * from tg_nazioni where Descrizione like '"+ut.getDescrizione()+"%' order by descrizione";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new NazioniMapper());
		}
		catch (DataAccessException ex) 
		{
			transactionManager.rollback(status);
			throw ex;
		}
		catch (org.springframework.transaction.TransactionTimedOutException ex)
		{
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);	
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Nazioni> findNazione(int naz) throws org.springframework.dao.DataAccessException
	{
		
		List<Nazioni> list=null;
		String sql = "select * from tg_nazioni where Progressivo="+naz;
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new NazioniMapper());
		}
		catch (DataAccessException ex) 
		{
			transactionManager.rollback(status);
			throw ex;
		}
		catch (org.springframework.transaction.TransactionTimedOutException ex)
		{
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);	
		return list;
		
	}
	
	public int insert(Nazioni current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;	
		KeyHolder keyH = new GeneratedKeyHolder();
		
		sql = "insert into tg_nazioni(descrizione, sigla, abilitata, prefisso_telefonico, anonimo, "+
				"data_inserimento,login_inserimento) ";
		sql = sql +"values (:descrizione, :sigla, :abilitata, :prefisso_telefonico, :anonimo,"+
				":data_inserimento,:login_inserimento)";		

		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(getDataSource());
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("descrizione",current.getDescrizione())
				.addValue("sigla",current.getSigla())
				.addValue("abilitata", current.getAbilitata())
				.addValue("prefisso_telefonico", current.getPrefisso_telefonico())
				.addValue("anonimo", current.getAnonimo())
				.addValue("data_inserimento",current.getData_inserimento())
				.addValue("login_inserimento",current.getLogin_inserimento());
			npjt.update(sql, param, keyH, new String[]{"progressivo"});
			current.setProgressivo(keyH.getKey().intValue());
		}
		catch (DataAccessException ex) 
		{
			transactionManager.rollback(status);
			throw ex;
		}
		catch (org.springframework.transaction.TransactionTimedOutException ex)
		{
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);	
		return current.getProgressivo();
	}
	
	
	public void update(Nazioni current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;
				
		sql = "update tg_nazioni set descrizione=:descrizione, sigla=:sigla, abilitata=:abilitata, prefisso_telefonico=:prefisso_telefonico,"+
			  "anonimo=:anonimo,"+
			  "data_variazione=:data_variazione,login_variazione=:login_variazione ";
		sql = sql + " where progressivo=:progressivo";
	
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(getDataSource());
			Map<String,Object> param = new HashMap<String, Object>();
			
			param.put("progressivo",current.getProgressivo());
			param.put("descrizione",current.getDescrizione());
			param.put("sigla",current.getSigla());		
			param.put("abilitata", current.getAbilitata());
			param.put("prefisso_telefonico", current.getPrefisso_telefonico());
			param.put("anonimo", current.getAnonimo());
			param.put("data_variazione",current.getData_variazione());
			param.put("login_variazione",current.getLogin_variazione());
			
			npjt.update(sql, param);		
		}
		catch (DataAccessException ex) 
		{
			transactionManager.rollback(status);
			throw ex;
		}
		catch (org.springframework.transaction.TransactionTimedOutException ex)
		{
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);		
	}
	
	public void delete(int progressivo) throws org.springframework.dao.DataAccessException
	{		
		
		String sql = "delete from tg_nazioni where progressivo=?";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			getJdbcTemplate().update(sql, progressivo);
		}
		catch (DataAccessException ex) 
		{
			transactionManager.rollback(status);
			throw ex;
		}
		catch (org.springframework.transaction.TransactionTimedOutException ex)
		{
			transactionManager.rollback(status);
			throw ex;
		}
		transactionManager.commit(status);	
	}
	
	@SuppressWarnings("rawtypes")
	protected class NazioniMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			Nazioni eu = new Nazioni();
			
			eu.setProgressivo(rs.getInt("progressivo"));
			eu.setSigla(rs.getString("sigla"));
			eu.setDescrizione(rs.getString("descrizione"));
			eu.setAbilitata(rs.getBoolean("abilitata"));
			eu.setPrefisso_telefonico(rs.getString("prefisso_telefonico"));
			eu.setAnonimo(rs.getString("anonimo"));
			eu.setData_inserimento(rs.getTimestamp("data_inserimento"));
			eu.setLogin_inserimento(rs.getString("login_inserimento"));
			eu.setData_variazione(rs.getTimestamp("data_variazione"));
			eu.setLogin_variazione(rs.getString("login_variazione"));

			return eu;
		}
		
	}
}
