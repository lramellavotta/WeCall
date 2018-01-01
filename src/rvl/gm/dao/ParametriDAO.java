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

import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import rvl.gm.classi.Parametri;


public class ParametriDAO extends NamedParameterJdbcDaoSupport
{
	
	private PlatformTransactionManager transactionManager;
	private TransactionTemplate txTemplate;
		
	public void setTransactionManager(PlatformTransactionManager txManager) 
	{
        this.transactionManager = txManager;
        this.txTemplate = new TransactionTemplate(txManager);
        this.txTemplate.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
    }
	
	public Map<String, Object> leggiParametro(String parParametro)
	{
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);
		
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "SELECT valore FROM tg_parametri WHERE campo='" + parParametro +"'";
		
		try
		{
			map=getJdbcTemplate().queryForMap(sql);
		}catch (DataAccessException ex) 
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
		return map;
	}
	
	public void update(Parametri current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;
				
		sql = "update tg_parametri set valore=:valore,"+
			  "data_variazione=:data_variazione,login_variazione=:login_variazione ";
		sql = sql + " where campo=:campo";
	
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(getDataSource());
			Map<String,Object> param = new HashMap<String, Object>();
			
			param.put("campo",current.getCampo());
			param.put("valore",current.getValore());
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

}
