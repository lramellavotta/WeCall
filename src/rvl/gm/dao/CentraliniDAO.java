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

import rvl.gm.classi.Centralini;
import rvl.gm.classi.Nazioni;


public class CentraliniDAO extends NamedParameterJdbcDaoSupport
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
	public List<Centralini> findAll(Nazioni pNazione) throws org.springframework.dao.DataAccessException
	{
		List<Centralini> list=null;
		String sql = "select * from tg_centralini where progressivo > 0 and nazione="+Integer.toString(pNazione.getProgressivo())+" order by descrizione";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new CentraliniMapper(),new Object[]{});
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
	public List<Centralini> findCentralino(int pCentralino) throws org.springframework.dao.DataAccessException
	{
		List<Centralini> list=null;
		String sql = "select * from tg_centralini where progressivo="+Integer.toString(pCentralino);
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new CentraliniMapper(),new Object[]{});
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
	
	public void delete(int progressivo) throws org.springframework.dao.DataAccessException
	{		
		
		String sql = "delete from tg_centralini where progressivo=?";
		
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
	
	public int insert(Centralini current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;	
		KeyHolder keyH = new GeneratedKeyHolder();
		
		sql = "insert into tg_centralini(descrizione, indirizzoip, nazione, manager_login, manager_password, registra_telefonate, "+
				"data_inserimento,login_inserimento, apacheip, apacheuser, apachepass, anonimo, contesto) ";
		sql = sql +"values (:descrizione, :indirizzoip, :nazione, :manager_login, :manager_password, :registra_telefonate, "+
				":data_inserimento,:login_inserimento, :apacheip, :apacheuser, :apachepass, :anonimo, :contesto)";		

		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(getDataSource());
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("descrizione",current.getDescrizione())
				.addValue("indirizzoip", current.getIndirizzoIP())
				.addValue("nazione", current.getNazione())
				.addValue("contesto", current.getContesto())
				.addValue("manager_password", current.getManager_password())
				.addValue("manager_login", current.getManager_login())
				.addValue("registra_telefonate", current.getRegistra_telefonate())
				.addValue("apacheip", current.getApacheIP())
				.addValue("apacheuser", current.getApacheUser())
				.addValue("apachepass", current.getApachePass())
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
	
	public void update(Centralini current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;
		
		sql = "update tg_centralini set descrizione=:descrizione, indirizzoip=:indirizzoip, nazione=:nazione, "+
			  "manager_login=:manager_login, manager_password=:manager_password, registra_telefonate=:registra_telefonate, "+
			  "data_variazione=:data_variazione,login_variazione=:login_variazione, "+
			  "apacheip=:apacheip, apacheuser=:apacheuser, apachepass=:apachepass, anonimo=:anonimo, contesto=:contesto ";
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
			param.put("indirizzoip", current.getIndirizzoIP());
			param.put("nazione", current.getNazione());
			param.put("manager_password", current.getManager_password());
			param.put("manager_login", current.getManager_login());
			param.put("registra_telefonate", current.getRegistra_telefonate());
			param.put("data_variazione",current.getData_variazione());
			param.put("login_variazione",current.getLogin_variazione());
			param.put("apacheip", current.getApacheIP());
			param.put("apacheuser", current.getApacheUser());
			param.put("apachepass", current.getApachePass());
			param.put("anonimo", current.getAnonimo());
			param.put("contesto", current.getContesto());
			
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
	
	
	@SuppressWarnings("unchecked")
	public List<Centralini> findFilter(Centralini ut, Nazioni pNazione) throws org.springframework.dao.DataAccessException
	{
		
		List<Centralini> list=null;		
		String sql = "select * from tg_centralini where progressivo >0  and descrizione like '"+ut.getDescrizione()+"%' "+
					 "and nazione="+Integer.toString(pNazione.getProgressivo())+" order by descrizione";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new CentraliniMapper());
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
	
	@SuppressWarnings("rawtypes")
	protected class CentraliniMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			
			Centralini eu = new Centralini();
						
			eu.setProgressivo(rs.getInt("progressivo"));
			eu.setIndirizzoIP(rs.getString("indirizzoIP"));
			eu.setDescrizione(rs.getString("descrizione"));
			eu.setNazione(rs.getInt("nazione"));
			eu.setManager_login(rs.getString("manager_login"));
			eu.setManager_password(rs.getString("manager_password"));
			eu.setRegistra_telefonate(rs.getBoolean("registra_telefonate"));
			eu.setApacheIP(rs.getString("apacheip"));
			eu.setApachePass(rs.getString("apachePass"));
			eu.setApacheUser(rs.getString("apacheUser"));
			eu.setAnonimo(rs.getBoolean("anonimo"));
			eu.setContesto(rs.getString("contesto"));
			eu.setData_inserimento(rs.getTimestamp("data_inserimento"));
			eu.setLogin_inserimento(rs.getString("login_inserimento"));
			eu.setData_variazione(rs.getTimestamp("data_variazione"));
			eu.setLogin_variazione(rs.getString("login_variazione"));

			return eu;
		}
		
	}
}
