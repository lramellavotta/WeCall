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
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import rvl.gm.classi.Telefoni;

public class TelefoniDAO extends NamedParameterJdbcDaoSupport
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
	public List<Telefoni> findAll() throws org.springframework.dao.DataAccessException
	{
		
		List<Telefoni> list=null;
		String sql = "select * from tg_telefoni where progressivo >0 order by descrizione";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new TelefoniMapper(),new Object[]{});
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
	public List<Telefoni> findTelefonoUtente(String ut) throws org.springframework.dao.DataAccessException
	{
		
		List<Telefoni> list=null;
		String sql = "select 0 AS progressivo, canale_telefonico AS descrizione, interno_telefonico AS interno, NULL AS indirizzo_IP, NULL AS data_inserimento, "+
					 "NULL AS login_inserimento, NULL AS data_variazione, NULL AS login_variazione "+
					 "from tg_utenti where utente='"+ut+"'";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new TelefoniMapper(),new Object[]{});
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
	protected class TelefoniMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			Telefoni eu = new Telefoni();
			
			eu.setProgressivo(rs.getInt("progressivo"));
			eu.setDescrizione(rs.getString("descrizione"));
			eu.setIndirizzo_IP(rs.getString("indirizzo_IP"));
			eu.setInterno(rs.getString("interno"));
			eu.setData_inserimento(rs.getTimestamp("data_inserimento"));
			eu.setLogin_inserimento(rs.getString("login_inserimento"));
			eu.setData_variazione(rs.getTimestamp("data_variazione"));
			eu.setLogin_variazione(rs.getString("login_variazione"));

			return eu;
		}
		
	}
}
