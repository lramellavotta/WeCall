package rvl.gm.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import rvl.gm.classi.Utenti;


public class UtentiDAO extends NamedParameterJdbcDaoSupport
{

	private PlatformTransactionManager transactionManager;
	private TransactionTemplate txTemplate;
		
	public void setTransactionManager(PlatformTransactionManager txManager) 
	{
        this.transactionManager = txManager;
        this.txTemplate = new TransactionTemplate(txManager);
        this.txTemplate.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
    }
	
	public void checkConnection() throws org.springframework.dao.DataAccessException
	{
		getJdbcTemplate().queryForObject("select now()", java.sql.Date.class);
	}
		
	@SuppressWarnings("unchecked")
	public List<Utenti> findUser(String username) throws org.springframework.dao.DataAccessException
	{
		List<Utenti> list=null;
		String sql = "select * from tg_utenti where utente = ?";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);
		
		try 
		{
			list=getJdbcTemplate().query(sql, new UtentiMapper(), new Object[]{username});
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
	
	public void updLogin(String username) throws org.springframework.dao.DataAccessException
	{		
		java.util.Date adesso = new java.util.Date();
		String sql = "update tg_utenti set data_ultimo_login=? where utente=?";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			getJdbcTemplate().update(sql, adesso, username);
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

	public void updPasswd(String username, String password, String verifica) throws org.springframework.dao.DataAccessException
	{		
		java.util.Date adesso = new java.util.Date();
		String sql = "update tg_utenti set passwd=?,verifica=?,data_cambio_passwd=? where utente=?";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			getJdbcTemplate().update(sql, password, verifica, adesso, username);
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
	
	public void update(Utenti current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;
		sql = "update tg_utenti set tipo=:tipo,cognome=:cognome,nome=:nome,data_abilitazione=:data_abilitazione," +
			  "data_disabilitazione=:data_disabilitazione,data_ultimo_login=:data_ultimo_login,abilitato=:abilitato," +
			  "data_variazione=:data_variazione,login_variazione=:login_variazione,interno_telefonico=:interno_telefonico, "+
			  "canale_telefonico=:canale_telefonico, colore_chiamata=:colore_chiamata, nazione=:nazione, centralino_utilizzato=:centralino_utilizzato, "+
			  "avatar=:avatar";
		sql = sql + " where utente=:utente";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(getDataSource());
			Map<String,Object> param = new HashMap<String, Object>();			
			param.put("utente",current.getUtente());
			param.put("tipo",current.getTipo());
			param.put("cognome",current.getCognome());
			param.put("nome",current.getNome());
			param.put("interno_telefonico", current.getInterno_telefonico());
			param.put("canale_telefonico",current.getCanale_telefonico());
			param.put("colore_chiamata", current.getColore_chiamata());
			param.put("data_abilitazione",current.getData_abilitazione());
			param.put("data_disabilitazione",current.getData_disabilitazione());
			param.put("data_ultimo_login",current.getData_ultimo_login());
			param.put("abilitato",current.getAbilitato());
			param.put("data_variazione",current.getData_variazione());
			param.put("login_variazione",current.getLogin_variazione());
			param.put("colore_chiamata",current.getColore_chiamata());
			param.put("centralino_utilizzato", current.getCentralino_utilizzato());
			param.put("nazione", current.getNazione());
			param.put("avatar", current.getAvatar());
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
	
	public void delete(String username) throws org.springframework.dao.DataAccessException
	{		
		
		String sql = "delete from tg_utenti where utente=?";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			getJdbcTemplate().update(sql, username);
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

	public void insert(Utenti current) throws org.springframework.dao.DataAccessException
	{
		String sql = null;		
		sql = "insert into tg_utenti(utente,passwd,verifica,tipo,cognome,nome,data_abilitazione,"+
				"data_disabilitazione,data_cambio_passwd,data_ultimo_login,abilitato,colore_chiamata,"+
				"data_inserimento,login_inserimento, interno_telefonico, canale_telefonico, nazione, centralino_utilizzato, avatar) ";
		sql = sql +"values (:utente,:passwd,:verifica,:tipo,:cognome,:nome,:data_abilitazione,:data_disabilitazione,"+
					":data_cambio_passwd,:data_ultimo_login,:abilitato,:colore_chiamata, :data_inserimento,:login_inserimento,:interno_telefonico,"+
					":canale_telefonico, :nazione, :centralino_utilizzato, :avatar)";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(getDataSource());
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("utente",current.getUtente());
			param.put("passwd",current.getPasswd());
			param.put("verifica",current.getVerifica());
			param.put("tipo",current.getTipo());
			param.put("cognome",current.getCognome());
			param.put("nome",current.getNome());
			param.put("colore_chiamata", current.getColore_chiamata());
			param.put("interno_telefonico", current.getInterno_telefonico());
			param.put("canale_telefonico", current.getCanale_telefonico());
			param.put("data_abilitazione",current.getData_abilitazione());
			param.put("data_disabilitazione",current.getData_disabilitazione());
			param.put("data_ultimo_login",current.getData_ultimo_login());
			param.put("abilitato",current.getAbilitato());
			param.put("nazione", current.getNazione());
			param.put("avatar", current.getAvatar());
			param.put("centralino_utilizzato", current.getCentralino_utilizzato());
			param.put("data_cambio_passwd", current.getData_inserimento());
			param.put("data_inserimento",current.getData_inserimento());
			param.put("login_inserimento",current.getLogin_inserimento());
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
	public List<Utenti> findAll(Boolean blnSoloAbilitati, int parNazione) throws org.springframework.dao.DataAccessException
	{
		
		List<Utenti> list=null;
		String sql = "select * from tg_utenti where Nazione="+parNazione;
		if(blnSoloAbilitati)
			sql = sql + " AND abilitato!=0";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new UtentiMapper());
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
	public List<Utenti> findFilter(Utenti ut) throws org.springframework.dao.DataAccessException
	{
		
		List<Utenti> list=null;
		String sql = "select * from tg_utenti where Utente like '"+ut.getUtente()+"%'"+
												" and Nome like '"+ut.getNome()+"%'"+
												" and Cognome like '"+ut.getCognome()+"%'";
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);

		try 
		{
			list=getJdbcTemplate().query(sql, new UtentiMapper());
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
	public List<Utenti> findConnessi(String parSoloConnessi, int parNazione)throws org.springframework.dao.DataAccessException
	{
		
		txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
		txTemplate.setTimeout(10);
		TransactionStatus status = transactionManager.getTransaction(txTemplate);
							
		RowMapper<Utenti> lstUtenti = new UtentiMapper();
		List<Utenti> list=null;
		@SuppressWarnings("rawtypes")
		Map mapList;

		try 
		{			
			SimpleJdbcCall jdbcCall= new SimpleJdbcCall(getDataSource())
				.withProcedureName("spElencoUtentiConnessi")
				.declareParameters(new SqlReturnResultSet("ElencoUtentiConnessi", lstUtenti));
		
			SqlParameterSource in = new MapSqlParameterSource()
				.addValue("pSoloConnessi", parSoloConnessi)
				.addValue("pNazione", parNazione);

			mapList = jdbcCall.execute(in);
			list=(List<Utenti>) mapList.get("ElencoUtentiConnessi");
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
	protected class UtentiMapper implements RowMapper 
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException 
		{
			Utenti user = new Utenti();
			
			user.setUtente(rs.getString("utente"));
			user.setPasswd(rs.getString("passwd"));
			user.setTipo(rs.getString("tipo"));
			user.setVerifica(rs.getString("verifica"));
			user.setCognome(rs.getString("cognome"));
			user.setNome(rs.getString("nome"));
			user.setAbilitato(rs.getString("abilitato"));
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
