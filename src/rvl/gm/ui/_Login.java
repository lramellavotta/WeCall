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
package rvl.gm.ui;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import rvl.gm.classi.Centralini;
import rvl.gm.classi.Nazioni;
import rvl.gm.classi.Telefoni;
import rvl.gm.classi.Utenti;
import rvl.gm.dao.CentraliniDAO;
import rvl.gm.dao.NazioniDAO;
import rvl.gm.dao.ParametriDAO;
import rvl.gm.dao.TelefoniDAO;
import rvl.gm.dao.UtentiDAO;
import rvl.gm.utility.LoginSecure;

public class _Login extends Window
{
	private static final String Version="4.0  /  01-Ott-17";
	private static final long serialVersionUID = 1L;
	
	private Desktop gDesktop;
	private Utenti gUt;
	private String gServer;
	
	private List<Centralini> lCentralini;
	private Centralini utCentralini;
	
	private List<Telefoni> lstTelefoni;
	private Telefoni selCmbTelefoni;

	private List<Nazioni> lstNazioni;
	private Nazioni selCmbNazioni;
	
	private DriverManagerDataSource gDm;
	
	private final String  gTitoloVideata = "Videata di login";
	private Boolean gOk=true;

	List<Utenti> lstUtenti;
	Iterator<Utenti> itUtenti;
	Utenti utUtenti;
	
	final int ERR_DB_CONN = -9;
	final int ERR_SERVER = -10;
	final int ERR_USER_NOT_FOUND = -1;
	final int ERR_USER_PWD = -2;
	final int ERR_USER_NOT_ACTIVE = -3;
	final int ERR_USER_NOT_AUTH = -4;
	final int ERR_GENERIC = -99;
	final int ERR_OK = 0;
	
	@Wire("#login")
	private Window winLogin;
	
	@Wire("#btnLogin")
	private Button btnLogin;
	@Wire("#txtUserName")
	private Textbox username;
	@Wire("#txtPasswD")
	private Textbox password;
	@Wire("#rg1")
	private Radiogroup rg1;
	@Wire("#lblVersione")
	private Label lblVersione;
	
	@Wire("#cmbNazioni")
	private Combobox cmbNazioni;
	@Wire("#cmbTelefoni")
	private Combobox cmbTelefoni;
	
	@Init
	public void init()
    {
		gDesktop = Executions.getCurrent().getDesktop(); 
		gUt = new Utenti();
    }
	
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
    {
        Selectors.wireComponents(view, this, false);
        
        lblVersione.setValue(Version);
        
		WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
		if (wac == null)
			throw new IllegalStateException("Non è stato possibile recuperare le informazioni nel file WeCall-config.xml relative alla configurazione dell'applicativo");
		else
		{
			Object dataSource = wac.getBean("dataSource");
			gDm = (DriverManagerDataSource) dataSource;
			gServer = gDm.getUrl().substring(gDm.getUrl().indexOf("/")+2,gDm.getUrl().lastIndexOf(":")).toString();
			Clients.showBusy("Attendere...");
			checkMySQL();
			caricaNazioni();
			Clients.clearBusy();
		}
		username.setFocus(true);
		btnLogin.setDisabled(false); 
    }
    
    /*
     * Verifica dei dati di login
     */
	@Command
	public void onLogin()
	{
		int check_status=ERR_OK;
		
		gDesktop.removeAttribute("gUtente");
		gDesktop.removeAttribute("gServer");
		gDesktop.removeAttribute("isConnected");
		gDesktop.removeAttribute("gReportPath");
		gDesktop.removeAttribute("winLogin");
		gDesktop.removeAttribute("gDebug");
		gDesktop.removeAttribute("gNazione");
		gDesktop.removeAttribute("gDesNazione");
		gDesktop.removeAttribute("gCentralino");
		gDesktop.removeAttribute("gDataSourceURL");
		gDesktop.removeAttribute("gDataSourceUsername");
		gDesktop.removeAttribute("gDataSourcePassword");
		
		if(username.getValue().length()<1)
		{
			Messagebox.show("Utente non compilato", gTitoloVideata, Messagebox.OK , Messagebox.ERROR);
			gOk = false;
		}
		else
			gUt.setUtente(username.getValue().toString().trim());
		
		if(password.getValue().length()<1)
		{
			Messagebox.show("Password non compilata", gTitoloVideata, Messagebox.OK , Messagebox.ERROR);
			gOk = false;
		}
		else
			gUt.setPasswd(password.getValue().toString().trim());
		
		if(gOk)
		{
			UtentiDAO udao = (UtentiDAO)SpringUtil.getBean("UtentiDAO");
			lstUtenti = udao.findUser(gUt);
			if(lstUtenti == null || lstUtenti.isEmpty())
			{
				Messagebox.show("Utente non presente. \nCorreggi il campo utente e riprova",
						gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
			}
			else
			{
				itUtenti = lstUtenti.iterator();
				gUt = itUtenti.next();
				LoginSecure ls = new LoginSecure();
				try 
				{					
					if(ls.verificaPasswd(gUt, password.getValue().toString().trim()))
					{
						if(gUt.getAbilitato())
							check_status=ERR_USER_NOT_ACTIVE;
						else
						{
							if(gUt.getTipo().equalsIgnoreCase("A"))
								;
							else
							{
								if(gUt.getNazione()!=selCmbNazioni.getProgressivo())
									check_status=ERR_USER_NOT_AUTH;
							}
						}

					} else
						check_status=ERR_USER_PWD;

				} catch (NoSuchAlgorithmException e) 
				{
					Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					check_status=ERR_GENERIC;
				} catch (WrongValueException e) 
				{
					Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					check_status=ERR_GENERIC;
				} catch (SQLException e) 
				{
					Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					check_status=ERR_GENERIC;
				}
					

				switch(check_status)
				{
				case ERR_USER_NOT_ACTIVE: 
					Messagebox.show("Utente disabilitato \nContattare l'assistenza",
							gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					break;
				case ERR_USER_NOT_AUTH: 
					Messagebox.show("Utente non abilitato per la nazione selezionata \nContattare l'assistenza",
							gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					break;
				case ERR_USER_PWD:
					Messagebox.show("Password errata. \nCorreggi il campo password e riprova",
							gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					break;
				case ERR_GENERIC:
					break;
				}
				
				if(check_status==ERR_OK)
				{
					CentraliniDAO ddao = (CentraliniDAO)SpringUtil.getBean("CentraliniDAO");
					lCentralini = ddao.findCentralino(gUt.getCentralino_utilizzato());
					try
					{
						utCentralini=lCentralini.get(0);
					} catch(Exception e)
					{
						Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
						check_status=ERR_GENERIC;
					}
				}


				if(check_status==ERR_OK)
				{

					URL r = this.getClass().getResource("/");
					String decoded=null;
					try {
						decoded = URLDecoder.decode(r.getFile(), "UTF-8");
					} catch (UnsupportedEncodingException e) 
					{
						Messagebox.show(e.getMessage(),gTitoloVideata,Messagebox.OK,Messagebox.ERROR);
					}
					
					gUt.setCanale_telefonico(selCmbTelefoni.getDescrizione());
					gUt.setInterno_telefonico(selCmbTelefoni.getInterno());
					
					Map<String, Object> map = new HashMap<String, Object>();
					ParametriDAO pdao = (ParametriDAO)SpringUtil.getBean("ParametriDAO");
					map=pdao.leggiParametro("debug");
					

					gDesktop.setAttribute("gUtente", gUt);
					gDesktop.setAttribute("gServer",gServer);
					gDesktop.setAttribute("isConnected", new Boolean(true));
					gDesktop.setAttribute("gReportPath", decoded+"rvl/gm/ui/reports/");
					gDesktop.setAttribute("winLogin", winLogin);
					gDesktop.setAttribute("gDebug",map.get("valore").toString());
					gDesktop.setAttribute("gNazione", selCmbNazioni);
					gDesktop.setAttribute("gDesNazione",selCmbNazioni.getDescrizione());			
					gDesktop.setAttribute("gCentralino", utCentralini);

					//
					// Per la gestione stampe ho necessità di questi dati 
					//
					gDesktop.setAttribute("gDataSourceURL", 	gDm.getUrl());
					gDesktop.setAttribute("gDataSourceUsername",gDm.getUsername());
					gDesktop.setAttribute("gDataSourcePassword",gDm.getPassword());
					
					Window menuWin = (Window)Executions.createComponents("menu.zul",null,null);
					menuWin.doOverlapped();

				}
			}
		}
		
	}
    
    

    /*
     * Inizio routines interne
     * 
     */
	private void checkMySQL()
	{		
		UtentiDAO pdao = (UtentiDAO)SpringUtil.getBean("UtentiDAO");
		pdao.checkConnection();
	}
	
	private void caricaNazioni()
	{
		cmbNazioni.setValue("<non trovato>");
		int naz = 106;	// Italia
		NazioniDAO ndao = (NazioniDAO)SpringUtil.getBean("NazioniDAO");
		List<Nazioni> lNazioni;
		lNazioni = ndao.findNazione(naz);
		
		lstNazioni = ndao.findAll("A");
		
		Nazioni utNazioni;
		Iterator<Nazioni> itNazioni;
		for (itNazioni = lNazioni.iterator(); itNazioni.hasNext(); ) 
		{
		    utNazioni = (Nazioni)itNazioni.next(); 
		    selCmbNazioni = utNazioni;
		    cmbNazioni.setValue(utNazioni.getDescrizione());
		}
	}
	
	@Command
	public void caricaTelefoni()
	{
		cmbTelefoni.setValue("<non trovato>");
		TelefoniDAO ndao = (TelefoniDAO)SpringUtil.getBean("TelefoniDAO");
		List<Telefoni> lTelefoni;
		lTelefoni = ndao.findTelefonoUtente(username.getValue().toString().trim());
		
		lstTelefoni = ndao.findAll();
		
		Telefoni utTelefoni;
		Iterator<Telefoni> itTelefoni;
		for (itTelefoni = lTelefoni.iterator(); itTelefoni.hasNext(); ) 
		{
			utTelefoni = (Telefoni)itTelefoni.next(); 
		    selCmbTelefoni = utTelefoni;
		    cmbTelefoni.setValue(utTelefoni.getDescrizione());
		}
		BindUtils.postNotifyChange(null,null,_Login.this,"lstTelefoni");
		
	}


	 /*
     * Gestione ZUL
     * 
     */
	
	public List<Telefoni> getLstTelefoni() {
		return lstTelefoni;
	}

	public void setLstTelefoni(List<Telefoni> lstTelefoni) {
		this.lstTelefoni = lstTelefoni;
	}

	public List<Nazioni> getLstNazioni() {
		return lstNazioni;
	}

	public void setLstNazioni(List<Nazioni> lstNazioni) {
		this.lstNazioni = lstNazioni;
	}

	public Telefoni getSelCmbTelefoni() {
		return selCmbTelefoni;
	}

	public void setSelCmbTelefoni(Telefoni selCmbTelefoni) {
		this.selCmbTelefoni = selCmbTelefoni;
	}

	public Nazioni getSelCmbNazioni() {
		return selCmbNazioni;
	}

	public void setSelCmbNazioni(Nazioni selCmbNazioni) {
		this.selCmbNazioni = selCmbNazioni;
	}	
	
	
	
}
