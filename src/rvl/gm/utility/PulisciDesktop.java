package rvl.gm.utility;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zkplus.spring.SpringUtil;

import rvl.gm.classi.EventiUtente;
import rvl.gm.classi.Utenti;
import rvl.gm.dao.EventiUtenteDAO;
import rvl.gm.interfacce.EventiDefiniti;

public class PulisciDesktop implements DesktopCleanup
{
	private Utenti gUt;
	
	@Override
	public void cleanup(Desktop desktop) 
	{
		gUt=(Utenti) desktop.getAttribute("gUtente");
		
		try
		{
			InserisciEventoLogin(gUt.getUtente(),EventiDefiniti.LOGOUT);
			
		} catch (Exception e)
		{
			;
		}
	}
	
	public boolean InserisciEventoLogin(String loc_utente, int loc_evento)
	{
		EventiUtenteDAO ev =  (EventiUtenteDAO)SpringUtil.getBean("EventiUtenteDAO");
		Boolean ok=true;

		EventiUtente eu = new EventiUtente();
		eu.setUtente(loc_utente);
		eu.setEvento(loc_evento);
		java.util.Date adesso = new java.util.Date();
		eu.setData_evento(new java.sql.Timestamp(adesso.getTime()));
		
		try
		{
			ev.insEvento(eu);
		} catch (org.springframework.dao.DataAccessException e)
		{
			;
			ok=false;
		} catch (Exception e)
		{
			;
			ok=false;
		}
		return ok;
	}
}
