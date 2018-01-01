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

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import rvl.gm.classi.Utenti;


public class UtentiDAO extends NamedParameterJdbcDaoSupport
{

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private PlatformTransactionManager transactionManager;
	private TransactionTemplate txTemplate;

	/*
	 * Load Bean dataSource
	 */
    public UtentiDAO(DataSource dataSource) 
    {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

	public void setTransactionManager(PlatformTransactionManager txManager) 
	{
        this.transactionManager = txManager;
        this.txTemplate = new TransactionTemplate(txManager);
        this.txTemplate.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        this.txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        this.txTemplate.setTimeout(10);
    }
	
	public void checkConnection() throws org.springframework.dao.DataAccessException
	{
		getJdbcTemplate().queryForObject("select now()", java.sql.Date.class);
	}
	
    public List<Utenti> findAll()
    {	
    		List<Utenti> list=null;
    		String sql = "select * from tg_utenti";
    		
    		TransactionStatus status = transactionManager.getTransaction(txTemplate);
    		
    		try 
    		{
    			list=namedParameterJdbcTemplate.query(sql, new UtentiMapper());
    		}
    		catch (DataAccessException ex) 
    		{
    			transactionManager.rollback(status);
    			throw ex;
    		}
    		catch (TransactionTimedOutException ex)
    		{
    			transactionManager.rollback(status);
    			throw ex;
    		}
    		transactionManager.commit(status);	
    		return list;
    }
    
	public List<Utenti> findUser(Utenti parUt) throws org.springframework.dao.DataAccessException
	{
		List<Utenti> list=null;
		String sql = "select * from tg_utenti where utente = ?";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);
		
		try 
		{
			list=getJdbcTemplate().query(sql, new UtentiMapper(), new Object[]{parUt.getUtente()});
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
    
	protected class UtentiMapper implements RowMapper<Utenti>
	{
		public Utenti mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			Utenti user = new Utenti();
			
			user.setUtente(rs.getString("utente"));
			user.setPasswd(rs.getString("passwd"));
			user.setTipo(rs.getString("tipo"));
			user.setVerifica(rs.getString("verifica"));
			user.setCognome(rs.getString("cognome"));
			user.setNome(rs.getString("nome"));
			user.setAbilitato(rs.getBoolean("abilitato"));
			user.setColore_chiamata(rs.getString("colore_chiamata"));
			user.setInterno_telefonico(rs.getString("interno_telefonico"));
			user.setCanale_telefonico(rs.getString("canale_telefonico"));
			user.setCentralino_utilizzato(rs.getInt("centralino_utilizzato"));
			user.setData_cambio_passwd(rs.getTimestamp("data_cambio_passwd"));
			user.setData_abilitazione(rs.getTimestamp("data_abilitazione"));
			user.setData_disabilitazione(rs.getTimestamp("data_disabilitazione"));
			user.setData_ultimo_login(rs.getTimestamp("data_ultimo_login"));
			user.setData_inserimento(rs.getTimestamp("data_inserimento"));
			user.setLogin_inserimento(rs.getString("login_inserimento"));
			user.setData_variazione(rs.getTimestamp("data_variazione"));
			user.setLogin_variazione(rs.getString("login_variazione"));
			user.setColore_chiamata(rs.getString("colore_chiamata"));
			user.setNazione(rs.getInt("nazione"));
			user.setAvatar(rs.getString("avatar"));

			return user;
		}
		
	}

}